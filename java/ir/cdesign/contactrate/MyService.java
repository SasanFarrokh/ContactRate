package ir.cdesign.contactrate;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

    public static SQLiteDatabase database;

    public void onCreate() {
        super.onCreate();
        databaseInit();
        alarm = new AlarmReciever();
        calendar = Calendar.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        long currentTime = calendar.getTimeInMillis();
        List<HashMap> invites = DatabaseCommands.getInstance().getInvite(0,0);
        for (HashMap invite : invites) {

            if ( (int) invite.get("active") == 0 ) continue;

            long timestamp = (long) invite.get("timestamp");

            if (timestamp > currentTime) {
                alarm.setAlarm(this, timestamp, (Integer) invite.get("id"));
            } else {
                alarm.cancelAlarm(this, (Integer) invite.get("id"));
                //DatabaseCommands.getInstance().removeInvite(((Integer) invite.get("contact")).longValue(),((Integer) invite.get("id")).longValue());
            }
            Log.i("timestamp : ", "" + timestamp);
        }

        //alarm.setAlarm(this, System.currentTimeMillis() + 10000, 0);

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void databaseInit() {
        database = openOrCreateDatabase(DatabaseCommands.DB_NAME, MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseCommands.TABLE_CONTACTS + "(id INTEGER PRIMARY KEY," +
                "name VARCHAR," +
                "phone VARCHAR," +
                "lesson INTEGER," +
                "time INTEGER," +
                "motive INTEGER," +
                "note TEXT," +
                "invites VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseCommands.TABLE_INVITES +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "type VARCHAR," +
                "contact INTEGER," +
                "note TEXT," +
                "timestamp INTEGER," +
                "active INTEGER);");
    }
}
