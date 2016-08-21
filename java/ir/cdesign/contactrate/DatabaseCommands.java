package ir.cdesign.contactrate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sasan on 2016-08-21.
 */
public class DatabaseCommands {

    public static final String DB_NAME = "ContactRate";
    public static final String TABLE_CONTACTS = "ContactRank";


    private SQLiteDatabase database;
    private static DatabaseCommands instance;

    public static DatabaseCommands getInstance(SQLiteDatabase db) {
        if (instance == null)
            instance = new DatabaseCommands(db);
        return instance;
    }

    private DatabaseCommands(SQLiteDatabase db) {
        database = db;
    }

    public boolean insertContact(long id, int lesson, int time, int motive, String invites) {

        ArrayList<String> contact = ContactShow.getContactById(id);

        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", contact.get(0));
        values.put("phone", contact.get(1));
        values.put("lesson", lesson);
        values.put("time", time);
        values.put("motive", motive);
        values.put("invites", invites);
        if (database.insert(TABLE_CONTACTS, null, values) != -1) return true;
        return false;
    }

    public void removeContact(long id) {
        database.delete(TABLE_CONTACTS, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Object[]> getContactsForRank() {

        List<Object[]> contacts = new ArrayList<>();
        String query = "SELECT * FROM " +
                TABLE_CONTACTS +
                " ORDER BY lesson+motive+time DESC";
        Cursor result = database.rawQuery(query, null);
        if (result != null) {
            while (result.moveToNext()) {
                Object[] contact = new Object[2];
                contact[0] = result.getString(result.getColumnIndex("name"));
                contact[1] = result.getInt(result.getColumnIndex("lesson")) +
                        result.getInt(result.getColumnIndex("time")) +
                        result.getInt(result.getColumnIndex("motive"));

                contacts.add(contact);
            }
            result.close();
        }
        return contacts;
    }
}
