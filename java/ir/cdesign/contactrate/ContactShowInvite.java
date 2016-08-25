package ir.cdesign.contactrate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import ir.cdesign.contactrate.adapters.ContactShowAdapter;
import ir.cdesign.contactrate.adapters.ContactTasksAdapter;
import ir.cdesign.contactrate.adapters.InvitationAdapter;
import ir.cdesign.contactrate.adapters.RankAdapter;
import ir.cdesign.contactrate.utilities.CustomLayoutManager;

public class ContactShowInvite extends AppCompatActivity {

    long contactId;
    TextView contactName, point;
    EditText phone, note;
    FloatingActionButton fab;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_show_invite);

        fab = (FloatingActionButton) findViewById(R.id.add_invite_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactShowInvite.this, NewTaskActivity.class).putExtra("contact_id", contactId));
            }
        });
//        init
        setToolbar();

        contactId = getIntent().getLongExtra("contact_id", 0);
        if (contactId == 0) finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        contactName = (TextView) findViewById(R.id.contact_name);
        phone = (EditText) findViewById(R.id.contact_phone);
        note = (EditText) findViewById(R.id.contact_note);
        point = (TextView) findViewById(R.id.contact_ipoint);

        final HashMap contact = DatabaseCommands.getInstance().getContactById(contactId);

        contactName.setText((String) contact.get("name"));
        phone.setText((String) contact.get("phone"));
        note.setText((String) contact.get("note"));

        final Button call = (Button) findViewById(R.id.call_btn);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact.get("phone")));
                if (ActivityCompat.checkSelfPermission(ContactShowInvite.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                }

            }
        });

        ImageView contactImage = (ImageView) findViewById(R.id.contact_img);
        Uri imageUri = ContactShow.getPhotoUri(contactId,this);
        if (imageUri != null) contactImage.setImageURI(imageUri);
        if(contactImage.getDrawable() == null) contactImage.setImageResource(R.drawable.contact);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setListView();
        startService(new Intent(this, MyService.class));
    }

    private void setListView(){
        listView = (ListView) findViewById(R.id.contact_task_rv);
        ContactTasksAdapter adapter = new ContactTasksAdapter(this);
        listView.setAdapter(adapter);

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
            case R.id.contact_invite_remove:
                DatabaseCommands.getInstance().removeFromInvitation(contactId);
                InvitationFragment.instance.recyclerView.getAdapter().notifyDataSetChanged();
                InvitationFragment.instance.recyclerView.setAdapter(new InvitationAdapter(this));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_invite_menu,menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseCommands.getInstance().addNoteToContact(contactId,note.getText().toString());
    }
}
