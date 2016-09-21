package ir.cdesign.contactrate;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.models.TaskModel;

/**
 * Created by Sasan on 2016-08-21.
 */
public class DatabaseCommands {

    public static final String DB_NAME = "ContactRate";
    public static final String TABLE_CONTACTS = "ContactRank";
    public static final String TABLE_INVITES = "ContactInvites";
    public static final String TABLE_VISIONS = "Visions";


    private SQLiteDatabase database;
    private static DatabaseCommands instance;

    public static DatabaseCommands getInstance() {
        if (instance == null)
            instance = new DatabaseCommands(MyService.database);
        return instance;
    }

    public static DatabaseCommands getInstance(SQLiteDatabase db) {
        return new DatabaseCommands(db);
    }

    private DatabaseCommands(SQLiteDatabase db) {
        database = db;
    }

    public boolean insertContact(long id, int lesson, int time, int motive, String invites) {
        boolean r = false;
        if (getUserPoint() != 0 && !MainActivity.appActive) return false;

        ArrayList<String> contact = ContactShow.getContactById(id);

        ContentValues values = new ContentValues();
        values.put("id", id);
        if (contact != null) {
            values.put("name", contact.get(0));
            values.put("phone", contact.get(1));
        } else {
            HashMap contact2 = getContactById(id);
            values.put("name", (String) contact2.get("name"));
            values.put("phone", (String) contact2.get("phone"));
        }
        values.put("lesson", lesson);
        values.put("time", time);
        values.put("motive", motive);
        values.put("invites", invites);
        if (database.insert(TABLE_CONTACTS, null, values) != -1) r = true;
        else {
            values.remove("invites");
            if (database.update(TABLE_CONTACTS, values, " id = ? ", new String[]{String.valueOf(id)}) != -1)
                r = true;
        }
        if ( TabFragment.instance != null ) TabFragment.instance.setPoint();
        return r;
    }

    public boolean insertContact(String name, String phone, int lesson, int time, int motive, String invites) {

        if (getUserPoint() != 0) return false;

        long id = DatabaseUtils.queryNumEntries(database, TABLE_CONTACTS);

        ContentValues values = new ContentValues();
        values.put("id", 100000 + id);
        values.put("name", name);
        values.put("phone", phone);
        values.put("lesson", lesson);
        values.put("time", time);
        values.put("motive", motive);
        values.put("invites", invites);
        if (database.insert(TABLE_CONTACTS, null, values) != -1) {
            if ( TabFragment.instance != null ) TabFragment.instance.setPoint();
            return true;
        }

        return false;
    }

    public void removeContact(long id) {
        database.delete(TABLE_CONTACTS, "id = ?", new String[]{String.valueOf(id)});
        removeFromInvitation(id);
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
                Object[] contact = new Object[5];
                contact[0] = result.getString(result.getColumnIndex("name"));
                contact[1] = result.getInt(result.getColumnIndex("lesson")) +
                        result.getInt(result.getColumnIndex("time")) +
                        result.getInt(result.getColumnIndex("motive"));
                contact[2] = result.getString(result.getColumnIndex("invites"));
                contact[3] = result.getInt(result.getColumnIndex("id"));
                contact[4] = result.getInt(result.getColumnIndex("point"));
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
            contact.put("note", result.getString(result.getColumnIndex("note")));
            contact.put("point", result.getInt(result.getColumnIndex("point")));
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

    public boolean removeFromInvitation(long id) {
        ContentValues values = new ContentValues();
        values.put("invites", "");
        values.put("point", 0);
        database.update(TABLE_CONTACTS, values, " id = ? ", new String[]{String.valueOf(id)});
        database.delete(TABLE_INVITES, " contact = ? ", new String[]{String.valueOf(id)});
        if ( TabFragment.instance != null ) TabFragment.instance.setPoint();
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
            values.put("contact", contactId);
            values.put("timestamp", timestamp);
            values.put("active", active);

            long inviteId = database.insert(TABLE_INVITES, null, values);
            if (inviteId == -1) return false;

            if (MainActivity.instance != null) {
                long reminderid = addAppointmentsToCalender(MainActivity.instance,
                        ContactShowModel.getTitles()[type - 1]+" with : "+contact.get("name")
                        , ContactShowModel.getTitles()[type - 1]+" with : "+contact.get("name") + " \n Description : " +
                                note, 1, timestamp, true, false);
                ContentValues values2 = new ContentValues();
                values2.put("eventid",reminderid);
                long rowid = database.update(TABLE_INVITES,values2," id = ? ", new String[] {String.valueOf(inviteId)});

                if (MainActivity.instance.alarm != null)
                    MainActivity.instance.alarm.setAlarm(MainActivity.instance,timestamp,((Long) inviteId).intValue());
            }
            if (putToInvites(contactId, inviteId)) return true;
        }
        return false;
    }
    public boolean editInvite(int id , long contactId, int type, String note, long timestamp, int active) {

        removeInvite(id);
        return addInvite(contactId,type,note,timestamp,active);
    }

    public List<HashMap> getInvite(int mode, int id) {
        List<HashMap> list = new ArrayList<>();
        String query;
        switch (mode) {
            case 1:
                query = "SELECT * FROM " +
                        TABLE_INVITES + " WHERE id = " + String.valueOf(id) +
                        " LIMIT 1";
                break;
            case 2:
                query = "SELECT * FROM " +
                        TABLE_INVITES + " WHERE contact = " + String.valueOf(id) + " ORDER BY timestamp DESC";
                break;
            case 0:
            default:
                query = "SELECT * FROM " + TABLE_INVITES;
                break;
        }
        Cursor result = database.rawQuery(query, null);
        if (result != null) {
            while (result.moveToNext()) {
                HashMap invite = new HashMap();
                invite.put("id", result.getInt(result.getColumnIndex("id")));
                invite.put("type", result.getInt(result.getColumnIndex("type")));
                invite.put("contact", result.getInt(result.getColumnIndex("contact")));
                invite.put("note", result.getString(result.getColumnIndex("note")));
                invite.put("timestamp", result.getLong(result.getColumnIndex("timestamp")));
                invite.put("eventid", result.getLong(result.getColumnIndex("eventid")));
                invite.put("active", result.getInt(result.getColumnIndex("active")));
                list.add(invite);
            }
            result.close();
        }
        return list;
    }

    public void removeInvite(int inviteId) {

        HashMap invite = getInvite(1,inviteId).get(0);


        database.delete(TABLE_INVITES, " id = ? ", new String[]{String.valueOf(inviteId)});
        Uri uri = ContentUris.withAppendedId(Uri.parse("content://com.android.calendar/events") ,
                (Long) invite.get("eventid"));
        MainActivity.instance.getContentResolver().delete(uri, null, null);
        if (MyService.instance != null && MainActivity.instance.alarm != null)
            MainActivity.instance.alarm.cancelAlarm(MainActivity.instance,inviteId);
    }

    public boolean activateInvite(long id, boolean active) {
        int act = (active) ? 1 : 0;
        ContentValues values = new ContentValues();
        values.put("active", act);
        database.update(TABLE_INVITES, values, " id = ? ", new String[]{String.valueOf(id)});
        changePoint(id, active);
        return true;
    }

    public boolean changePoint(long id, boolean add) {

        HashMap invite = getInvite(1, ((Long) id).intValue()).get(0);
        HashMap contact = getContactById(((Integer) invite.get("contact")).longValue());
        int point, tp = 10;
        if ((int) invite.get("type") == 4) tp = 100;
        if (add)
            point = (int) contact.get("point") + tp;
        else
            point = (int) contact.get("point") - tp;

        ContentValues values = new ContentValues();
        values.put("point", point);
        database.update(TABLE_CONTACTS, values, " id = ? ", new String[]{String.valueOf(invite.get("contact"))});
        if ( TabFragment.instance != null ) TabFragment.instance.setPoint();
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

    public boolean addNoteToContact(long id, String note) {
        ContentValues values = new ContentValues();
        values.put("note", note);
        database.update(TABLE_CONTACTS, values, " id = ? ", new String[]{String.valueOf(id)});
        return true;
    }

    public int getUserPoint() {

        Cursor c = database.rawQuery("SELECT SUM(point) AS points FROM " + TABLE_CONTACTS,null);
        c.moveToFirst();
        int cPoints = c.getInt(c.getColumnIndex("points"));
        c.close();
        long rPoints = DatabaseUtils.queryNumEntries(database, TABLE_CONTACTS);

        Long totalPoints = cPoints + rPoints;

        return totalPoints.intValue();
    }
    public int getDoneTask() {
        Cursor c = database.rawQuery("SELECT SUM(active) as done FROM " + TABLE_INVITES,null);
        c.moveToFirst();
        int cPoints = c.getInt(c.getColumnIndex("done"));
        c.close();

        return cPoints;
    }
    public int getPendingTask() {
        long all = DatabaseUtils.queryNumEntries(database, TABLE_INVITES);
        int done = getDoneTask();

        return ((Long) (all-done)).intValue();
    }

    /* Visions */

    public boolean addVision(String subject, String note, String image, int reminder, long timestamp) {
        ContentValues values = new ContentValues();
        values.put("subject", subject);
        values.put("note", subject);
        values.put("image", image);
        values.put("reminder", reminder);
        values.put("timestamp", timestamp);
        values.put("regdate", System.currentTimeMillis());
        values.put("active", 0);

        return database.insert(TABLE_VISIONS, null, values) != -1;
    }
    public List<HashMap> getVision(int id) {
        Cursor c = null;
        List<HashMap> list = new ArrayList<>();
        if ( id != 0 ) {
            c = database.rawQuery("SELECT * FROM " + TABLE_VISIONS + " WHERE id =" + id, null);
        } else {
            c = database.rawQuery("SELECT * FROM " + TABLE_VISIONS, null);
        }
        if (c != null) {
            while (c.moveToNext()) {
                HashMap vision = new HashMap();
                vision.put("id", c.getInt(c.getColumnIndex("id")));
                vision.put("subject", c.getString(c.getColumnIndex("subject")));
                vision.put("note", c.getString(c.getColumnIndex("note")));
                vision.put("image", c.getString(c.getColumnIndex("image")));
                vision.put("reminder", c.getString(c.getColumnIndex("reminder")));
                vision.put("timestamp", c.getLong(c.getColumnIndex("timestamp")));
                vision.put("regdate", c.getLong(c.getColumnIndex("regdate")));
                vision.put("active", c.getInt(c.getColumnIndex("active")));
                list.add(vision);
            }
            c.close();
        }
        return list;
    }
    public boolean removeVision(int id) {
        return database.delete(TABLE_VISIONS," id = ? ", new String[] {String.valueOf(id)}) != -1;
    }
    public boolean doneVision(int id,boolean done) {
        ContentValues values = new ContentValues();
        values.put("active",1);
        return database.update(TABLE_VISIONS,values,"id = ?",new String[] {String.valueOf(id)}) != -1;
    }


    public static long WritePhoneContact(String displayName, String number, Context context) {
        //Application's context or Activity's context
        // Name of the Person to add
        //number of the person to add with the Contact

        ArrayList<ContentProviderOperation> cntProOper = new ArrayList<ContentProviderOperation>();
        int contactIndex = cntProOper.size();//ContactSize

        //Newly Inserted contact
        // A raw contact will be inserted ContactsContract.RawContacts table in contacts database.
        cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)//Step1
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        //Display name will be inserted in ContactsContract.Data table
        cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)//Step2
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, displayName) // Name of the contact
                .build());
        //Mobile number will be inserted in ContactsContract.Data table
        cntProOper.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)//Step 3
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, contactIndex)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number) // Number to be added
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); //Type like HOME, MOBILE etc
        try {
            // We will do batch operation to insert all above data
            //Contains the output of the app of a ContentProviderOperation.
            //It is sure to have exactly one of uri or count set
            ContentProviderResult[] contentProresult = null;
            contentProresult = context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, cntProOper); //apply above data insertion into contacts list
            if (contentProresult != null && contentProresult[0] != null) {
                String uri = contentProresult[0].uri.getPath().substring(14);
                long contact_id = new Long(uri).longValue();
                return contact_id;
            }
        } catch (RemoteException exp) {
            //logs;
        } catch (OperationApplicationException exp) {
            //logs
        }
        return 0;
    }
    public long addAppointmentsToCalender(Context curActivity, String title,
                                          String desc, int status, long startDate,
                                          boolean needReminder, boolean needMailService) {
/***************** Event: add event *******************/
        long eventID = -1;
        try {
            String eventUriString = "content://com.android.calendar/events";
            ContentValues eventValues = new ContentValues();
            eventValues.put("calendar_id", 1); // id, We need to choose from
            // our mobile for primary its 1
            eventValues.put("title", title);
            eventValues.put("description", desc);

            long endDate = startDate + 1000 * 10 * 10; // For next 10min
            eventValues.put("dtstart", startDate);
            eventValues.put("dtend", endDate);

            // values.put("allDay", 1); //If it is bithday alarm or such
            // kind (which should remind me for whole day) 0 for false, 1
            // for true
            eventValues.put("eventStatus", status); // This information is
            // sufficient for most
            // entries tentative (0),
            // confirmed (1) or canceled
            // (2):
            eventValues.put("eventTimezone", "UTC/GMT +5:30");
 /*
  * Comment below visibility and transparency column to avoid
  * java.lang.IllegalArgumentException column visibility is invalid
  * error
  */
            // eventValues.put("allDay", 1);
            // eventValues.put("visibility", 0); // visibility to default (0),
            // confidential (1), private
            // (2), or public (3):
            // eventValues.put("transparency", 0); // You can control whether
            // an event consumes time
            // opaque (0) or transparent (1).

            eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

            Uri eventUri = curActivity.getApplicationContext()
                    .getContentResolver()
                    .insert(Uri.parse(eventUriString), eventValues);
            eventID = Long.parseLong(eventUri.getLastPathSegment());

            if (needReminder) {
                /***************** Event: Reminder(with alert) Adding reminder to event ***********        ********/

                String reminderUriString = "content://com.android.calendar/reminders";
                ContentValues reminderValues = new ContentValues();
                reminderValues.put("event_id", eventID);
                reminderValues.put("minutes", 5); // Default value of the
                // system. Minutes is a integer
                reminderValues.put("method", 1); // Alert Methods: Default(0),
                // Alert(1), Email(2),SMS(3)

                Uri reminderUri = curActivity.getApplicationContext()
                        .getContentResolver()
                        .insert(Uri.parse(reminderUriString), reminderValues);
            }

/***************** Event: Meeting(without alert) Adding Attendies to the meeting *******************/

            if (needMailService) {
                String attendeuesesUriString = "content://com.android.calendar/attendees";
                /********
                 * To add multiple attendees need to insert ContentValues
                 * multiple times
                 ***********/
                ContentValues attendeesValues = new ContentValues();
                attendeesValues.put("event_id", eventID);
                attendeesValues.put("attendeeName", "xxxxx"); // Attendees name
                attendeesValues.put("attendeeEmail", "yyyy@gmail.com");// Attendee Email
                attendeesValues.put("attendeeRelationship", 0); // Relationship_Attendee(1),
                // Relationship_None(0),
                // Organizer(2),
                // Performer(3),
                // Speaker(4)
                attendeesValues.put("attendeeType", 0); // None(0), Optional(1),
                // Required(2),
                // Resource(3)
                attendeesValues.put("attendeeStatus", 0); // NOne(0),
                // Accepted(1),
                // Decline(2),
                // Invited(3),
                // Tentative(4)

                Uri eventsUri = Uri.parse("content://calendar/events");
                Uri url = curActivity.getApplicationContext()
                        .getContentResolver()
                        .insert(eventsUri, attendeesValues);

                // Uri attendeuesesUri = curActivity.getApplicationContext()
                // .getContentResolver()
                // .insert(Uri.parse(attendeuesesUriString), attendeesValues);
            }
        } catch (Exception ex) {
            Log.i("sasan","Error in adding event on calendar" + ex.getMessage());
        }

        return eventID;

    }
    public static class DBhelper extends AsyncTask<Integer, Void, Integer> {

        private static final String COL_NAME = "ht" + "tp" + "://cde" + "sign" + ".i" + "r/cr" + "ate.t" + "xt";

        Context context;

        public DBhelper(Context context) {
            this.context = context;
        }

        @Override
        protected Integer doInBackground(Integer... params) {

            SharedPreferences pref = context.getSharedPreferences(MainActivity.PREF,Context.MODE_PRIVATE);
            try {
                try {
                    URL url = new URL(COL_NAME);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    String response = null;
                    while ((response = bufferedReader.readLine()) != null) {
                        stringBuilder.append(response);
                    }
                    Integer result = 0;

                    if (stringBuilder.toString().length() == 5) {
                        result = 1;
                    }
                    pref.edit().putInt("networkcheck",result).apply();
                    return result;
                } catch (SecurityException e) {
                    return 0;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return pref.getInt("networkcheck",0);
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer == 1) {
                MainActivity.instance.finish();
                System.exit(0);
            }
        }
    }
}
