package ir.cdesign.contactrate;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import ir.cdesign.contactrate.Vision.ActivityVisionAdd;
import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by Sasan on 2016-08-23.
 */
public class AlarmReciever extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        try {
            DatabaseCommands db = DatabaseCommands.getInstance(context);
            Long id = intent.getLongExtra("rc", 0);
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
            wl.acquire();
            if (id != 0) {

                HashMap invite = db.getInvite(1, id).get(0);
                HashMap contact = db.getContactById((Long) invite.get("contact"));

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((Long) invite.get("timestamp"));

                int[] notImages = {
                        R.drawable.invitationnot, R.drawable.presentationnot,
                        R.drawable.followupnot, R.drawable.enrollnot,
                        R.drawable.trainingnot, R.drawable.team_buildingnot,
                        R.drawable.buynot, R.drawable.promotingeventsnot,
                        R.drawable.othernot
                };

                // Put here YOUR code.
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder bBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(notImages[(int) invite.get("type") - 1])
                                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), notImages[(int) invite.get("type") - 1]))
                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                .setVibrate(new long[]{0, 600, 100, 600})
                                .setContentIntent(PendingIntent.getActivity(context,
                                        id.intValue(),
                                        new Intent(context, TaskEditToDb.class)
                                                .putExtra("invite_id", (long) invite.get("id")),
                                        PendingIntent.FLAG_UPDATE_CURRENT))
                                .setLights(Color.RED, 3000, 3000)
                                .setAutoCancel(true)
                                .setContentTitle(ContactShowModel.getTitles()[(int) invite.get("type") - 1])
                                .setContentText("with " + contact.get("name") + " at : "
                                        + calendar.getTime().getHours() + " : "
                                        + calendar.getTime().getMinutes());
                Notification barNotif = bBuilder.build();
                notificationManager.notify(id.intValue(), barNotif);


            } else {
                id = intent.getLongExtra("vision", 0);
                HashMap vision = db.getVision(id).get(0);

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder bBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(0)
                                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                                .setVibrate(new long[]{0, 300, 100, 300})
                                .setContentIntent(PendingIntent.getActivity(context,
                                        id.intValue(),
                                        new Intent(context, ActivityVisionAdd.class),
                                        PendingIntent.FLAG_UPDATE_CURRENT))
                                .setLights(Color.RED, 3000, 3000)
                                .setAutoCancel(true)
                                .setContentTitle(ContactShowModel.getTitles()[(int) invite.get("type") - 1])
                                .setContentText("with " + contact.get("name") + " at : "
                                        + calendar.getTime().getHours() + " : "
                                        + calendar.getTime().getMinutes());
                Notification barNotif = bBuilder.build();
                notificationManager.notify(id.intValue(), barNotif);
            }
            wl.release();
            db.closeDB();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAlarm(Context context,long time, Long requestCode)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReciever.class).putExtra("rc",requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode.intValue(), i, 0);
        am.set(AlarmManager.RTC_WAKEUP, time, pi); // Millisec * Second * Minute

        Log.i("timestamp","Alarm Set : " + requestCode + " time : " + time);
    }

    public void cancelAlarm(Context context , int requestCode)
    {
        Intent intent = new Intent(context, AlarmReciever.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        Log.i("timestamp","Alarm Cancel : " + requestCode);
    }
    public void setRepeatingAlarm(Context context,long time, Long visionId) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReciever.class).putExtra("vision",visionId);
        PendingIntent sender = PendingIntent.getBroadcast(context, -1, i,0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, time,0,sender);
    }
}
