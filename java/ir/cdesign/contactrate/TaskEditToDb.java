package ir.cdesign.contactrate;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.utilities.CalendarStrategy;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class TaskEditToDb extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String DATEPICKER = "DatePickerDialog";
    LinearLayout timePick;
    long contactId;
    TextView timeTxt,dateTxt;
    TextView note;

    int active = 0;

    LinearLayout completed;

    CalendarStrategy taskCalendar = new CalendarStrategy(new PersianCalendar());
    ScrollView scrollView;

    int type;
    long inviteId;

    boolean edit = false;

    HashMap contact, invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit_to_db);

        contactId = getIntent().getLongExtra("contact_id", 0);
        type = getIntent().getIntExtra("type", 0);
        inviteId = getIntent().getLongExtra("invite_id", 0);

        init();

        if (inviteId != 0) setByInviteId(inviteId);

        timeSet();

        if (contactId == 0 || type == 0) finish();

        setToolbar();

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        scrollView= (ScrollView) findViewById(R.id.scrollView);
        scrollView.setBackgroundResource(drawable);

        timePick = (LinearLayout) findViewById(R.id.time);
        timePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerShow();
            }
        });
    }

    private void init() {
        dateTxt = (TextView) findViewById(R.id.date_tv);
        findViewById(R.id.date).setOnClickListener(datePicker);
        note = (TextView) findViewById(R.id.note);
        timeTxt = (TextView) findViewById(R.id.timeTxt);
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
                if (!edit)
                    DatabaseCommands.getInstance(TaskEditToDb.this).addInvite(contactId, type, note.getText().toString(),
                            taskCalendar.getTimeInMillis(), active);
                else
                    DatabaseCommands.getInstance(TaskEditToDb.this).editInvite(inviteId, contactId, type, note.getText().toString(),
                            taskCalendar.getTimeInMillis(), active);
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
        Uri imageUri = ContactShow.getPhotoUri(contactId, this);
        if (imageUri != null) contactImage.setImageURI(imageUri);
        if (contactImage.getDrawable() == null) contactImage.setImageResource(R.drawable.contact);

    }


    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.real_text);
        title.setText(ContactShowModel.getTitles()[type - 1]);
        setSupportActionBar(toolbar);
    }

    public void setByInviteId(final long inviteId) {
        invite = DatabaseCommands.getInstance().getInvite(1, inviteId).get(0);
        contactId = (long) invite.get("contact");
        type = (int) invite.get("type");
        edit = true;

        long timestamp = (long) invite.get("timestamp");

        taskCalendar.setTimeInMillis(timestamp);

        note.setText((String) invite.get("note"));

        completed = (LinearLayout) findViewById(R.id.task_completed);
        completed.setVisibility(View.VISIBLE);
        CheckBox checkBox = (CheckBox) completed.getChildAt(2);
        checkBox.setChecked((int) invite.get("active") ==0 ?false:true);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseCommands.getInstance(TaskEditToDb.this).activateInvite(
                        ((long) invite.get("id")),
                        isChecked
                );
                active = isChecked ? 1:0;
            }
        });

    }

    public void openNoteDialog(View view) {

        FragmentManager fm = getSupportFragmentManager();
        EnterTextDialog enterTextDialog = new EnterTextDialog();
        enterTextDialog.show(fm, "NoteDialog");
    }

    public View.OnClickListener datePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dpd = CalendarStrategy.getDatePicker(
                    TaskEditToDb.this
            );

            dpd.show(getFragmentManager(), DATEPICKER);
        }
    };

    private void timePickerShow() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        taskCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        taskCalendar.set(Calendar.MINUTE,minute);
                        timeTxt.setText(new DecimalFormat("00").format(hourOfDay) + " : " + new DecimalFormat("00").format(minute));
                    }
                }, taskCalendar.get(Calendar.HOUR_OF_DAY), taskCalendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DialogFragment view, int year, int monthOfYear, int dayOfMonth) {
        taskCalendar.set(year,monthOfYear,dayOfMonth);
        timeSet();
    }

    private void timeSet() {
        timeTxt.setText(new DecimalFormat("00").format(taskCalendar.get(Calendar.HOUR_OF_DAY)) +
                " : " + new DecimalFormat("00").format(taskCalendar.get(Calendar.MINUTE)));
        dateTxt.setText(taskCalendar.getDate());
    }

}
