package ir.cdesign.contactrate;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ir.cdesign.contactrate.adapters.InvitationAdapter;
import ir.cdesign.contactrate.adapters.RankAdapter;

public class ContactRankShow extends AppCompatActivity {

    long contactId;

    // Points of each title
    int lesson, time, motive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_rank_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(null);
        }

        contactId = getIntent().getIntExtra("contact_id",0);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ContactRankShow.this.finish();
                return true;
            case R.id.contact_delete:
                DatabaseCommands.getInstance().removeContact(contactId);
                RankFragment.instance.recyclerView.getAdapter().notifyDataSetChanged();
                RankFragment.instance.recyclerView.setAdapter(new RankAdapter(this));
                finish();
                return true;
            case R.id.contact_edit:
                startActivity(new Intent(this,ContactShow.class).putExtra("contact_id",contactId));
                finish();
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

        HashMap contact = DatabaseCommands.getInstance().getContactById(contactId);
        if (contact.isEmpty()) finish();
        try {
            contactName.setText((String) contact.get("name"));
            contactNumber.setText("Phone Number : " + contact.get("phone"));
            addContactBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addContactToInvitation();
                }
            });
            lesson = (int) contact.get("lesson");
            time = (int) contact.get("time");
            motive = (int) contact.get("motive");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        ViewGroup pointContainer = (ViewGroup) findViewById(R.id.contact_point_container);
        for (int i = 0; i < 3; i++) {
            ViewGroup starCon = (ViewGroup) ((ViewGroup) pointContainer.getChildAt(i)).getChildAt(1);

            for (int ii = 0; ii < 5; ii++) {

                switch (Integer.parseInt((String) ((View) starCon.getParent()).getTag())) {
                    case 1:
                        Log.i("sasan","s:" + lesson + "," + time + "," + motive + " ; R = " + ii);
                        if ( ii < lesson) ((ImageView) starCon.getChildAt(ii))
                                .setColorFilter(getResources().getColor(R.color.starTint));
                        break;
                    case 2:
                        if ( ii < time) ((ImageView) starCon.getChildAt(ii))
                                .setColorFilter(getResources().getColor(R.color.starTint));
                        break;
                    case 3:
                        if ( ii < motive) ((ImageView) starCon.getChildAt(ii))
                                .setColorFilter(getResources().getColor(R.color.starTint));
                        break;
                }

            }
        }
    }


    public void addContactToInvitation() {
        if (DatabaseCommands.getInstance()
                .addToInvitation(contactId)) {
            Toast.makeText(ContactRankShow.this, "Successfully added", Toast.LENGTH_SHORT).show();
            InvitationFragment.instance.recyclerView.getAdapter().notifyDataSetChanged();
            InvitationFragment.instance.recyclerView.setAdapter(new InvitationAdapter(this));
            finish();
        } else {
            Toast.makeText(ContactRankShow.this, "Failed to add the contact", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu,menu);
        return true;
    }


}
