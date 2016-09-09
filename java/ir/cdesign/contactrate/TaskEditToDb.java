package ir.cdesign.contactrate;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

import ir.cdesign.contactrate.models.ContactShowModel;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianDateParser;
import ir.cdesign.contactrate.utilities.CalendarTool;

public class TaskEditToDb extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private static final String DATEPICKER = "DatePickerDialog";
    LinearLayout timePick;
    long contactId;
    TextView timeTxt,dateTxt;
    TextView note;

    Calendar taskCalendar = Calendar.getInstance();


    int type, inviteId;

    boolean edit = false;

    HashMap contact, invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit_to_db);

        contactId = getIntent().getLongExtra("contact_id", 0);
        type = getIntent().getIntExtra("type", 0);
        inviteId = getIntent().getIntExtra("invite_id", 0);

        init();

        if (inviteId != 0) setByInviteId(inviteId);

        if (contactId == 0 || type == 0) finish();

        setToolbar();


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

    private void calendarGet() {

        PersianCalendar date =  new PersianCalendar();
        date.setTimeInMillis(taskCalendar.getTimeInMillis());
        String dateStr =  date.getPersianYear() + "/" + (date.getPersianMonth() + 1) + "/" + date.getPersianDay();
        dateTxt.setText(dateStr);
        timeTxt.setText(new DecimalFormat("00").format(taskCalendar.get(Calendar.HOUR_OF_DAY)) +
                " : " + new DecimalFormat("00").format(taskCalendar.get(Calendar.MINUTE)));

    }

    @Override
    protected void onStart() {
        super.onStart();

        calendarGet();

        contact = DatabaseCommands.getInstance().getContactById(contactId);

        TextView contactNameView = (TextView) findViewById(R.id.contact_name);
        contactNameView.setText((String) contact.get("name"));

        View taskAccept = findViewById(R.id.task_accept);
        taskAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit)
                    DatabaseCommands.getInstance().addInvite(contactId, type, note.getText().toString(),
                            taskCalendar.getTimeInMillis(), 0);
                else
                    DatabaseCommands.getInstance().editInvite(inviteId, contactId, type, note.getText().toString(),
                            taskCalendar.getTimeInMillis(), 0);
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

    public void setByInviteId(int inviteId) {
        invite = DatabaseCommands.getInstance().getInvite(1, inviteId).get(0);
        contactId = ((Integer) invite.get("contact")).longValue();
        type = (int) invite.get("type");
        edit = true;

        long timestamp = (long) invite.get("timestamp");

        taskCalendar.setTimeInMillis(timestamp);

        timeTxt.setText(new DecimalFormat("00").format(Calendar.HOUR_OF_DAY) +
                " : " + new DecimalFormat("00").format(taskCalendar.get(Calendar.MINUTE)));

        note.setText((String) invite.get("note"));

    }

    public void openNoteDialog(View view) {

        FragmentManager fm = getSupportFragmentManager();
        EnterTextDialog enterTextDialog = new EnterTextDialog();
        enterTextDialog.show(fm, "Sample Fragment");
    }

    public View.OnClickListener datePicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PersianCalendar now = new PersianCalendar();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    TaskEditToDb.this,
                    now.getPersianYear(),
                    now.getPersianMonth(),
                    now.getPersianDay()
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

                        timeTxt.setText(new DecimalFormat("00").format(hourOfDay) + " : " + new DecimalFormat("00").format(minute));
                        taskCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        taskCalendar.set(Calendar.MINUTE,minute);
                    }
                }, taskCalendar.get(Calendar.HOUR_OF_DAY), taskCalendar.get(Calendar.MINUTE), false);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date =  year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        dateTxt.setText(date);
        timeSet((new PersianDateParser(date,"/")).getPersianDate().getTimeInMillis());
    }

    private void timeSet(long time) {
        taskCalendar.setTimeInMillis(time);
        timeTxt.setText(new DecimalFormat("00").format(taskCalendar.get(Calendar.HOUR_OF_DAY)) +
                " : " + new DecimalFormat("00").format(taskCalendar.get(Calendar.MINUTE)));
    }

}
