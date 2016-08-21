package ir.cdesign.contactrate;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.cdesign.contactrate.adapters.RankAdapter;

public class ContactShow extends AppCompatActivity {

    long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(null);
        }

        contactId = Long.parseLong(getIntent().getStringExtra("contact_id"));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ContactShow.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView contactName = (TextView) findViewById(R.id.contact_name);
        TextView contactNumber = (TextView) findViewById(R.id.contact_number);
        Button addContactBtn = (Button) findViewById(R.id.add_contact_btn);
        try {
            contactName.setText(getContactById(contactId).get(0));
            contactNumber.setText(getContactById(contactId).get(1));

            addContactBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addContact();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getContactById(long id) {
        ArrayList<String> phones = new ArrayList<String>();

        Cursor cursor = MainActivity.instance.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{String.valueOf(id)}, null);

        if (cursor != null) {
            cursor.moveToNext();
            phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }


        cursor.close();
        return phones;
    }

    public void addContact() {
        if ( DatabaseCommands.getInstance()
                .insertContact(contactId, 0, 0, 0, ""))
        {
            Toast.makeText(ContactShow.this, "Successfully added", Toast.LENGTH_SHORT).show();
            RankFragment.instance.recyclerView.getAdapter().notifyDataSetChanged();
            RankFragment.instance.recyclerView.setAdapter(new RankAdapter(this));
            finish();
        }
        else {
            Toast.makeText(ContactShow.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();
        }
    }
}
