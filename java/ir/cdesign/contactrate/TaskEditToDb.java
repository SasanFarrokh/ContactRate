package ir.cdesign.contactrate;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import ir.cdesign.contactrate.models.ContactShowModel;

public class TaskEditToDb extends AppCompatActivity {
    TextView toolbarText;

    long contactId;
    int type,inviteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit_to_db);

        contactId = getIntent().getLongExtra("contact_id",0);
        type = getIntent().getIntExtra("type",0);
        inviteId = getIntent().getIntExtra("invite_id",0);

        if (inviteId != 0) setByInviteId(inviteId);

        if (contactId == 0 || type == 0) finish();

        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();

        View taskAccept = findViewById(R.id.task_accept);
        taskAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseCommands.getInstance().addInvite(contactId,type,"salam",System.currentTimeMillis()+30000);
                finish();
            }
        });
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);
        setSupportActionBar(toolbar);
        toolbarText = (TextView) findViewById(R.id.real_text);
        /*
        *
        *       Must Get Text From Recycler Adapter of The Selected Item
        *           \/\/\/\/\/\/\/
        * */
        toolbarText.setText(ContactShowModel.getTitles()[type-1]);
        toolbarText.setTypeface(Typeface.DEFAULT_BOLD);

    }

    public void setByInviteId(int inviteId) {
        HashMap invite = DatabaseCommands.getInstance().getInvite(1,inviteId).get(0);
        contactId = ((Integer) invite.get("contact")).longValue();
        type = (int) invite.get("type");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_menu,menu);
        return true;
    }
}
