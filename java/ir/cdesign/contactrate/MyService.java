package ir.cdesign.contactrate;

import android.app.Activity;
import android.app.Service;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.models.ContactShowModel;

/**
 * Created by Sasan on 2016-08-23.
 */
public class MyService extends Service {

    AlarmReciever alarm;
    Calendar calendar;
    public static MyService instance;

    public static SQLiteDatabase database;

    public void onCreate() {
        super.onCreate();
        databaseInit();
        alarm = new AlarmReciever();
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        calendar = Calendar.getInstance();

        List<HashMap> invites = DatabaseCommands.getInstance().getInvite(0,0);
        for (HashMap invite : invites) {

            /*if ( (int) invite.get("active") == 1 ) {
                alarm.cancelAlarm(this, (Integer) invite.get("id"));
                continue;
            }


            long timestamp = (long) invite.get("timestamp");
            Log.i("timestamp : ", "" + timestamp + " | " + calendar.getTimeInMillis());
            if (timestamp > calendar.getTimeInMillis()) {
                alarm.setAlarm(this, timestamp, (Integer) invite.get("id"));
            } else {
                alarm.cancelAlarm(this, (Integer) invite.get("id"));
            }*/


        }
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
                "point INTEGER NOT NULL DEFAULT 0," +
                "invites VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseCommands.TABLE_INVITES +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "type VARCHAR," +
                "contact INTEGER," +
                "note TEXT," +
                "timestamp INTEGER," +
                "eventid INTEGER," +
                "active INTEGER);");
    }
    public SQLiteDatabase databaseConnect() {
        return openOrCreateDatabase(DatabaseCommands.DB_NAME, MODE_PRIVATE, null);
    }


}
