package ir.cdesign.contactrate;

import android.animation.ValueAnimator;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import ir.cdesign.contactrate.adapters.RankAdapter;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class ContactShow extends AppCompatActivity {

    long contactId;

    // Points of each title
    int lesson, time, motive;
    CoordinatorLayout coordinatorLayout;

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

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.Coordinato);
        coordinatorLayout.setBackgroundResource(drawable);

        contactId = getIntent().getLongExtra("contact_id",0);
        if (contactId == 0) finish();

        getWindow().getDecorView().getRootView().requestFocus();
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
        ImageView contactImage = (ImageView) findViewById(R.id.contact_img);
        Uri imageUri = getPhotoUri(contactId,this);

        ArrayList<String> contact = getContactById(contactId);
        if (contact != null && !contact.isEmpty()) {
            try {
                contactName.setText(contact.get(0));
                contactNumber.setText("Phone Number : " + contact.get(1));
                addContactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addContact();
                    }
                });

                if (imageUri != null) contactImage.setImageURI(imageUri);
                if (contactImage.getDrawable() == null)
                    contactImage.setImageResource(R.drawable.contact);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else {

            HashMap contact2 = DatabaseCommands.getInstance().getContactById(contactId);

            contactName.setText((String) contact2.get("name"));
            contactNumber.setText("Phone Number : " + contact2.get("phone"));
            addContactBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addContact();
                }
            });
        }


        View.OnTouchListener starTouch = new OnStarTouch();

        ViewGroup pointContainer = (ViewGroup) findViewById(R.id.contact_point_container);
        for (int i = 0; i < 3; i++) {
            ViewGroup starCon = (ViewGroup) ((ViewGroup) pointContainer.getChildAt(i)).getChildAt(1);
            starCon.setOnTouchListener(starTouch);
        }
    }

    public static ArrayList<String> getContactById(long id) {
        try {
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
        } catch (Exception e) {
            return null;
        }
    }
    public static Uri getPhotoUri(long id, Context context) {
        try {
            Cursor cur = context.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    return null; // no photo
                }
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }

    public void addContact() {
        if (DatabaseCommands.getInstance()
                .insertContact(contactId, lesson, time, motive, "")) {
            Toast.makeText(ContactShow.this, "Successfully added", Toast.LENGTH_SHORT).show();
            RankFragment.instance.recyclerView.getAdapter().notifyDataSetChanged();
            RankFragment.instance.recyclerView.setAdapter(new RankAdapter(this));
            finish();
        } else {
            Toast.makeText(ContactShow.this, "To add more contact, you should purchase the full version", Toast.LENGTH_SHORT).show();
        }
    }





    private class OnStarTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    ViewGroup con = (ViewGroup) v;
                    int type = Integer.parseInt((String) ((View) v.getParent()).getTag());
                    float x = event.getX();
                    float col = v.getWidth()/5;

                    int rate = Math.max(0,Math.min(5,(int) Math.ceil(x/col)));

                    for (int i = 0 ; i < 5; i++) {
                        ImageView star = (ImageView) con.getChildAt(i);
                        if (i <= rate - 1) {

                            star.setColorFilter(getResources().getColor(R.color.starTint));
                        } else {
                            star.setColorFilter(null);
                        }
                    }

                    switch (type) {
                        case 1:
                            lesson = rate;
                            break;
                        case 2:
                            time = rate;
                            break;
                        case 3:
                            motive = rate;
                            break;
                    }
                    break;
            }

            return true;
        }
    }
}
