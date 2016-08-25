package ir.cdesign.contactrate;

import android.app.TimePickerDialog;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.utilities.MedalDialog;

public class TaskEditToDb extends AppCompatActivity {
    TextView toolbarText;

    LinearLayout timePick;
    long contactId;
    TextView timeTxt;
    TimePickerDialog timePickerDialog ;
    EditText year , month , day ;

    int type, inviteId , hourOfDay , minute;

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

        init();

        timePick = (LinearLayout) findViewById(R.id.time);

        timePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             timePickerShow();
            }
        });
    }

    private  void init(){
        day = (EditText) findViewById(R.id.dayEdit);
        month = (EditText) findViewById(R.id.monthEdit);
        year = (EditText) findViewById(R.id.yearEdit);


    }

    private void calendarShits(){
        Calendar c = Calendar.getInstance();
        hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    private void timePickerShow(){
        calendarShits();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        timeTxt = (TextView) findViewById(R.id.timeTxt);
                        timeTxt.setText(new DecimalFormat("00").format(hourOfDay) + " : " + new DecimalFormat("00").format(minute));

                    }
                }, hourOfDay, minute, false);
        timePickerDialog.show();
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
                DatabaseCommands.getInstance().addInvite(contactId, type, "salam", System.currentTimeMillis() + 30000, 0);
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

    public void openTheBitch(View view) {

        FragmentManager fm = getSupportFragmentManager();
        EnterTextDialog enterTextDialog = new EnterTextDialog();
        enterTextDialog.show(fm, "Sample Fragment");

    }
}
