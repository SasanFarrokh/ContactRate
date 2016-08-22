package ir.cdesign.contactrate;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.cdesign.contactrate.adapters.RankAdapter;

public class ContactShow extends AppCompatActivity {

    long contactId;

    // Points of each title
    int lesson, time, motive;

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

        ArrayList<String> contact = getContactById(contactId);
        try {
            contactName.setText(contact.get(0));
            contactNumber.setText("Phone Number : " + contact.get(1));
            addContactBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addContact();
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        View.OnClickListener starClickListener = new OnStarClick();

        ViewGroup pointContainer = (ViewGroup) findViewById(R.id.contact_point_container);
        for (int i = 0; i < 3; i++) {
            ViewGroup starCon = (ViewGroup) ((ViewGroup) pointContainer.getChildAt(i)).getChildAt(1);
            for (int ii = 0; ii < 5; ii++) {
                starCon.getChildAt(ii).setTag(ii);
                starCon.getChildAt(ii).setOnClickListener(starClickListener);
            }
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
            cursor.moveToFirst();
            phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            phones.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            cursor.close();
        }

        return phones;
    }

    public void addContact() {
        if (DatabaseCommands.getInstance()
                .insertContact(contactId, lesson, time, motive, "")) {
            Toast.makeText(ContactShow.this, "Successfully added", Toast.LENGTH_SHORT).show();
            RankFragment.instance.recyclerView.getAdapter().notifyDataSetChanged();
            RankFragment.instance.recyclerView.setAdapter(new RankAdapter(this));
            finish();
        } else {
            Toast.makeText(ContactShow.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();
        }
    }




    private class OnStarClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int type = Integer.parseInt((String) ((View) v.getParent().getParent()).getTag());
            int rate = 0;
            switch (type) {
                case 1:
                    if (lesson == (int) v.getTag() + 1)
                        lesson = 0;
                    else
                        lesson = (int) v.getTag() + 1;
                    rate = lesson;
                    break;
                case 2:
                    if (time == (int) v.getTag() + 1)
                        time = 0;
                    else
                        time = (int) v.getTag() + 1;
                    rate = time;
                    break;
                case 3:
                    if (motive == (int) v.getTag() + 1)
                        motive = 0;
                    else
                        motive = (int) v.getTag() + 1;
                    rate = motive;
                    break;
            }
            for (int j = 0; j < 5; j++) {
                if (j <= rate - 1) {
                    ((ImageView) ((ViewGroup) v.getParent()).getChildAt(j)).setColorFilter(getResources().getColor(R.color.starTint));
                } else {
                    ((ImageView) ((ViewGroup) v.getParent()).getChildAt(j)).setColorFilter(null);
                }
            }
        }
    }
}
