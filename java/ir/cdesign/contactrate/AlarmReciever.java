package ir.cdesign.contactrate;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

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
            HashMap invite = null;//DatabaseCommands.getInstance(
                    //CreateDatabase("ml", Context.MODE_PRIVATE, null))
                    //.getInvite(1, intent.getIntExtra("rc", 0)).get(0);
            HashMap contact = null;//DatabaseCommands.getInstance(
                    //CreateDatabase("ml", Context.MODE_PRIVATE, null))
                    //.getContactById(((Integer) invite.get("contact")).longValue());


            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
            wl.acquire();

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis((Long) invite.get("timestamp"));

            int[] notImages = {
                    R.drawable.invitationnot , R.drawable.presentationnot ,
                    R.drawable.followupnot , R.drawable.enrollnot ,
                    R.drawable.trainingnot , R.drawable.team_buildingnot ,
                    R.drawable.buynot , R.drawable.promotingeventsnot ,
                    R.drawable.othernot
            };

            // Put here YOUR code.
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder bBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(notImages[(int) invite.get("type") - 1])
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setVibrate(new long[]{0, 600, 100, 600})
                            .setContentIntent(PendingIntent.getActivity(context,
                                    intent.getIntExtra("rc", 0),
                                    new Intent(context, TaskEditToDb.class).putExtra("invite_id", intent.getIntExtra("rc", 0)),
                                    PendingIntent.FLAG_UPDATE_CURRENT))
                            .setLights(Color.RED, 3000, 3000)
                            .setAutoCancel(true)
                            .setContentTitle(ContactShowModel.getTitles()[(int) invite.get("type") - 1])
                            .setContentText("with " + (String) contact.get("name") + " at : "
                                    + calendar.getTime().getHours() + " : "
                                    + calendar.getTime().getMinutes());
            Notification barNotif = bBuilder.build();
            notificationManager.notify(intent.getIntExtra("rc", 0), barNotif);

            wl.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAlarm(Context context,long time, int requestCode)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.setTimeZone(TimeZone.getDefault().toString());
        Intent i = new Intent(context, AlarmReciever.class).putExtra("rc",requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, time, pi); // Millisec * Second * Minute

        Log.i("timestamp","Alarm Set : " + requestCode);
    }

    public void cancelAlarm(Context context , int requestCode)
    {
        Intent intent = new Intent(context, AlarmReciever.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        Log.i("timestamp","Alarm Cancel : " + requestCode);



    }
}
