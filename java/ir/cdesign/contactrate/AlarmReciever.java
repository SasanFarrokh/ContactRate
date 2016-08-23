package ir.cdesign.contactrate;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Sasan on 2016-08-23.
 */
public class AlarmReciever extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder bBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.mlm_list)
                        .setContentTitle("title")
                        .setContentText("sub title");
        Notification barNotif = bBuilder.build();
        notificationManager.notify(intent.getIntExtra("rc",0),barNotif);

        wl.release();
    }

    public void setAlarm(Context context,long time, int requestCode)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReciever.class).putExtra("rc",requestCode);
        PendingIntent pi = PendingIntent.getBroadcast(context, requestCode, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, time, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context , int requestCode)
    {
        Intent intent = new Intent(context, AlarmReciever.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
