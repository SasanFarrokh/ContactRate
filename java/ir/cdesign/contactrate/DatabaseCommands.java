package ir.cdesign.contactrate;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Sasan on 2016-08-21.
 */
public class DatabaseCommands {

    public static final String DB_NAME = "ContactRate";
    public static final String TABLE_CONTACTS = "ContactRank";


    private SQLiteDatabase database;

    public DatabaseCommands(SQLiteDatabase db) {
        database = db;
    }

    public boolean insertContact(long id,int lesson,int time, int motive,String invites) {

        ArrayList<String> contact = ContactShow.getContactById(id);

        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("name",contact.get(0));
        values.put("phone",contact.get(1));
        values.put("lesson",lesson);
        values.put("time",time);
        values.put("motive",motive);
        values.put("invites",invites);
        if ( database.insert(TABLE_CONTACTS,null,values) != -1 ) return true;
        return false;
    }
}
