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

import ir.cdesign.contactrate.adapters.TasksAdapter;
import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.models.MedalModel;

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
        if (getSharedPreferences(MainActivity.PREF,MODE_PRIVATE).getString("userName",null) == null) {
            databaseInit();
        }
        instance = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new DatabaseCommands.DBhelper(this).execute();
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
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseCommands.TABLE_VISIONS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "subject VARCHAR," +
                "note TEXT," +
                "image VARCHAR," +
                "reminder INTEGER," +
                "timestamp INTEGER," +
                "regdate INTEGER," +
                "active INTEGER);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseCommands.TABLE_MEDALS +
                "(id INTEGER PRIMARY KEY NOT NULL," +
                "title VARCHAR NOT NULL UNIQUE," +
                "subtitle VARCHAR NOT NULL," +
                "image INTEGER NOT NULL," +
                "progress INTEGER NOT NULL DEFAULT 0," +
                "complete INTEGER NOT NULL," +
                "point INTEGER NOT NULL DEFAULT 10," +
                "achieved INTEGER NOT NULL DEFAULT 0);");
        List<MedalModel> medals = MedalModel.getData();
        for (MedalModel medal : medals) {
            ContentValues values = new ContentValues();
            values.put("id", medal.id);
            values.put("title", medal.title);
            values.put("subtitle", medal.subtitle);
            values.put("image", medal.imageId);
            values.put("complete", medal.completeMax);
            values.put("point", medal.point);
            database.insert(DatabaseCommands.TABLE_MEDALS,null,values);
        }
    }
    public SQLiteDatabase databaseConnect() {
        return openOrCreateDatabase(DatabaseCommands.DB_NAME, MODE_PRIVATE, null);
    }


}
