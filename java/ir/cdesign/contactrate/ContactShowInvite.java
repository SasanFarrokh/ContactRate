package ir.cdesign.contactrate;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ContactShowInvite extends AppCompatActivity {

    long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show_invite);

//        init
        setToolbar();

        contactId = getIntent().getLongExtra("contact_id",0);
        if (contactId == 0) finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView contactName = (TextView) findViewById(R.id.contact_name);
        EditText phone = (EditText) findViewById(R.id.contact_phone);
        EditText note = (EditText) findViewById(R.id.contact_note);
        TextView point = (TextView) findViewById(R.id.contact_ipoint);

        HashMap contact = DatabaseCommands.getInstance().getContactById(contactId);

        contactName.setText((String) contact.get("name"));
        phone.setText((String) contact.get("phone"));
        note.setText((String) contact.get("note"));
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(null);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ContactShowInvite.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
