package ir.cdesign.contactrate;

import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import ir.cdesign.contactrate.models.ContactShowModel;

public class TaskEditToDb extends AppCompatActivity {
    TextView toolbarText;

    long contactId;
    int type, inviteId;

    HashMap contact, invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit_to_db);

        contactId = getIntent().getLongExtra("contact_id", 0);
        type = getIntent().getIntExtra("type", 0);
        inviteId = getIntent().getIntExtra("invite_id", 0);

        if (inviteId != 0) setByInviteId(inviteId);

        if (contactId == 0 || type == 0) finish();

        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        contact = DatabaseCommands.getInstance().getContactById(contactId);

        TextView contactNameView = (TextView) findViewById(R.id.contact_name);
        contactNameView.setText((String) contact.get("name"));

        View taskAccept = findViewById(R.id.task_accept);
        taskAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseCommands.getInstance().addInvite(contactId, type, "salam", System.currentTimeMillis() + 30000, 1);
                finish();
            }
        });
        View taskCancel = findViewById(R.id.task_cancel);
        taskCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inviteId != 0)
                    DatabaseCommands.getInstance().removeInvite(inviteId);
                finish();
            }
        });

        ImageView contactImage = (ImageView) findViewById(R.id.contact_img);
        Uri imageUri = ContactShow.getPhotoUri(contactId,this);
        if (imageUri != null) contactImage.setImageURI(imageUri);
        if(contactImage.getDrawable() == null) contactImage.setImageResource(R.drawable.contact);
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.real_text);
        title.setText(ContactShowModel.getTitles()[type - 1]);
        setSupportActionBar(toolbar);
        /*
        *
        *       Must Get Text From Recycler Adapter of The Selected Item
        *           \/\/\/\/\/\/\/
        * */

    }

    public void setByInviteId(int inviteId) {
        invite = DatabaseCommands.getInstance().getInvite(1, inviteId).get(0);
        contactId = ((Integer) invite.get("contact")).longValue();
        type = (int) invite.get("type");
    }

}
