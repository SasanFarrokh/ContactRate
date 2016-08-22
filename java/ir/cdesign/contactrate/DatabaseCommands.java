package ir.cdesign.contactrate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Sasan on 2016-08-21.
 */
public class DatabaseCommands {

    public static final String DB_NAME = "ContactRate";
    public static final String TABLE_CONTACTS = "ContactRank";
    public static final String TABLE_INVITES = "ContactInvites";


    private SQLiteDatabase database;
    private static DatabaseCommands instance;

    public static DatabaseCommands getInstance() {
        if (instance == null)
            instance = new DatabaseCommands(MainActivity.database);
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

                Object[] contact = new Object[4];
                contact[0] = result.getString(result.getColumnIndex("name"));
                contact[1] = result.getInt(result.getColumnIndex("lesson")) +
                        result.getInt(result.getColumnIndex("time")) +
                        result.getInt(result.getColumnIndex("motive"));
                contact[2] = result.getString(result.getColumnIndex("invites"));
                contact[3] = result.getInt(result.getColumnIndex("id"));

                contacts.add(contact);
            }
            result.close();
        }
        return contacts;
    }

    public List<Object[]> getContactsForInvitation() {

        List<Object[]> contacts = new ArrayList<>();
        String query = "SELECT * FROM " +
                TABLE_CONTACTS + " WHERE LENGTH(invites) != 0 " +
                " ORDER BY lesson+motive+time DESC";
        Cursor result = database.rawQuery(query, null);
        if (result != null) {
            while (result.moveToNext()) {
                Object[] contact = new Object[4];
                contact[0] = result.getString(result.getColumnIndex("name"));
                contact[1] = result.getInt(result.getColumnIndex("lesson")) +
                        result.getInt(result.getColumnIndex("time")) +
                        result.getInt(result.getColumnIndex("motive"));
                contact[2] = result.getString(result.getColumnIndex("invites"));
                contact[3] = result.getInt(result.getColumnIndex("id"));
                contacts.add(contact);
            }
            result.close();
        }
        return contacts;
    }

    public HashMap getContactById(long id) {
        HashMap contact = new HashMap();
        String query = "SELECT * FROM " +
                TABLE_CONTACTS + " WHERE id = " + String.valueOf(id) +
                " LIMIT 1";
        Cursor result = database.rawQuery(query, null);
        if (result != null) {
            result.moveToFirst();
            contact.put("name", result.getString(result.getColumnIndex("name")));
            contact.put("phone", result.getString(result.getColumnIndex("phone")));
            contact.put("lesson", result.getInt(result.getColumnIndex("lesson")));
            contact.put("time", result.getInt(result.getColumnIndex("time")));
            contact.put("motive", result.getInt(result.getColumnIndex("motive")));
            contact.put("invites", result.getString(result.getColumnIndex("invites")));

            result.close();
        }
        return contact;
    }

    public boolean addToInvitation(long id) {
        ContentValues values = new ContentValues();
        values.put("invites", "0");
        database.update(TABLE_CONTACTS, values, " id = ? AND LENGTH(invites) = 0 ", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean addInvite(long contactId, int type, String note, long time) {
        return addInvite(contactId, type, note, time, 0);
    }

    public boolean addInvite(long contactId, int type, String note, long timestamp, int active) {

        HashMap contact = getContactById(contactId);
        if (contact.get("name") != null) {

            ContentValues values = new ContentValues();
            values.put("type", type);
            values.put("note", note);
            values.put("timestamp", timestamp);
            values.put("active", active);

            long inviteId = database.insert(TABLE_INVITES, null, values);
            if (inviteId == -1) return false;

            if (putToInvites(contactId, inviteId)) return true;
        }
        return false;
    }

    public void removeInvite(long contactId,long inviteId) {
        database.delete(TABLE_INVITES, "id = ?", new String[]{String.valueOf(contactId)});
        if (contactId != 0) {
            HashMap contact = getContactById(contactId);
            List<String> inviteArray = Arrays.asList(((String) contact.get("invites")).split(","));
            inviteArray.remove(inviteArray.indexOf(String.valueOf(inviteId)));
            String inviteString = "";
            for (int i = 0 ; i < inviteArray.size() ; i++) {
                inviteString += inviteArray.get(i);
                if (i != inviteArray.size() - 1 ) inviteString += ",";
            }

            ContentValues contactValues = new ContentValues();
            contactValues.put("invites", inviteString);

            database.update(TABLE_CONTACTS, contactValues, " id = ? ", new String[]{String.valueOf(contactId)});
        }
    }

    public boolean activateInvite(long id, boolean active) {
        int act = (active) ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put("active", act);
        database.update(TABLE_INVITES, values, " id = ? ", new String[]{String.valueOf(id)});
        return true;
    }

    private boolean putToInvites(long contactId, long inviteId) {

        HashMap contact = getContactById(contactId);

        String inviteStr = (String) contact.get("invites");
        inviteStr += "," + String.valueOf(inviteId);


        ContentValues contactValues = new ContentValues();
        contactValues.put("invites", inviteStr);

        database.update(TABLE_CONTACTS, contactValues, " id = ? ", new String[]{String.valueOf(contactId)});
        return true;
    }
}
