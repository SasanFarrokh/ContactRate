package ir.cdesign.contactrate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class ContactShowInvite extends AppCompatActivity {

    long contactId;
    TextView contactName, point;
    EditText phone, note;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show_invite);

        fab = (FloatingActionButton) findViewById(R.id.add_invite_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactShowInvite.this,NewTaskActivity.class));
            }
        });
//        init
        setToolbar();

        contactId = getIntent().getLongExtra("contact_id",0);
        if (contactId == 0) finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        contactName = (TextView) findViewById(R.id.contact_name);
        phone = (EditText) findViewById(R.id.contact_phone);
        note = (EditText) findViewById(R.id.contact_note);
        point = (TextView) findViewById(R.id.contact_ipoint);

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

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseCommands.getInstance().addNoteToContact(contactId,note.getText().toString());
    }
}
