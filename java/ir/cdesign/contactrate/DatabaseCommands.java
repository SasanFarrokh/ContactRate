package ir.cdesign.contactrate;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Sasan on 2016-08-21.
 */
public class DatabaseCommands {

    private SQLiteDatabase database;

    public DatabaseCommands(SQLiteDatabase db) {
        database = db;
    }

    public void insertContact() {
        database.insert("")
    }
}
