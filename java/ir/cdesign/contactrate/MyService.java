package ir.cdesign.contactrate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sasan on 2016-08-23.
 */
public class MyService extends Service {

    AlarmReciever alarm;
    Calendar calendar;
    public void onCreate() {
        super.onCreate();
        alarm = new AlarmReciever();
        calendar = Calendar.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        long currentTime = calendar.getTimeInMillis();
        List<HashMap> invites = DatabaseCommands.getInstance().getInvite(0,0);
        for (HashMap invite : invites) {

            long timestamp = (long) invite.get("timestamp");


            if (timestamp > currentTime) {
                alarm.setAlarm(this, timestamp, (Integer) invite.get("id"));
            } else {
                alarm.cancelAlarm(this, (Integer) invite.get("id"));
            }
            Log.i("timestamp : ", "" + timestamp);
        }

        alarm.setAlarm(this, System.currentTimeMillis() + 10000, 0);

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
