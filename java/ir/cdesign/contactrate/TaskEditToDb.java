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
import ir.cdesign.contactrate.utilities.CalendarTool;

public class TaskEditToDb extends AppCompatActivity {

    LinearLayout timePick;
    long contactId;
    TextView timeTxt;
    EditText year, month, day;
    TextView note;

    Calendar taskCalendar = Calendar.getInstance();


    int type, inviteId;
    Integer hourOfDay, minute;

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
        final View container = findViewById(R.id.containerLayout);
        container.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(container.getWindowToken(), 0);
            }
        });
        container.requestFocus();
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.requestFocus();
            }
        });
    }

    private void init() {
        day = (EditText) findViewById(R.id.dayEdit);
        month = (EditText) findViewById(R.id.monthEdit);
        year = (EditText) findViewById(R.id.yearEdit);
        note = (TextView) findViewById(R.id.note);
        timeTxt = (TextView) findViewById(R.id.timeTxt);

    }

    private void calendarGet() {

        hourOfDay = taskCalendar.get(Calendar.HOUR_OF_DAY);
        minute = taskCalendar.get(Calendar.MINUTE);
        timeTxt.setText(new DecimalFormat("00").format(hourOfDay) + " : " + new DecimalFormat("00").format(minute));

        CalendarTool calendar2 = new CalendarTool(
                taskCalendar.get(Calendar.YEAR),
                taskCalendar.get(Calendar.MONTH) + 1,
                taskCalendar.get(Calendar.DAY_OF_MONTH)
        );

        year.setHint(String.valueOf(calendar2.getIranianYear() - 1300));
        month.setHint(String.valueOf(calendar2.getIranianMonth()));
        day.setHint(String.valueOf(calendar2.getIranianDay()));
    }

    private void timePickerShow() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        TaskEditToDb.this.hourOfDay = hourOfDay;
                        TaskEditToDb.this.minute = minute;
                        timeTxt.setText(new DecimalFormat("00").format(hourOfDay) + " : " + new DecimalFormat("00").format(minute));

                    }
                }, hourOfDay, minute, false);
        timePickerDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!edit) calendarGet();

        contact = DatabaseCommands.getInstance().getContactById(contactId);

        TextView contactNameView = (TextView) findViewById(R.id.contact_name);
        contactNameView.setText((String) contact.get("name"));

        View taskAccept = findViewById(R.id.task_accept);
        taskAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCalender();
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


        day.addTextChangedListener(new GoNextClass(day, 3));
        month.addTextChangedListener(new GoNextClass(month, 2));
        year.addTextChangedListener(new GoNextClass(year, 1));
    }

    private void setCalender() {

        CalendarTool calendar2 = new CalendarTool();
        try {
            int fYear,fMonth,fDay;
            if (year.getText().toString().isEmpty()) fYear = Integer.parseInt(year.getHint().toString());
            else fYear = Integer.parseInt(year.getHint().toString());
            if (month.getText().toString().isEmpty()) fMonth = Integer.parseInt(month.getHint().toString());
            else fMonth = Integer.parseInt(month.getHint().toString());
            if (day.getText().toString().isEmpty()) fDay = Integer.parseInt(day.getHint().toString());
            else fDay = Integer.parseInt(day.getHint().toString());

            calendar2.setIranianDate(
                    fYear + 1300,
                    fMonth,
                    fDay
            );

        } catch (Exception e) {
            calendar2.setIranianDate(1395,6, 4);
        }

        taskCalendar.set(
                calendar2.getGregorianYear(),
                calendar2.getGregorianMonth() - 1,
                calendar2.getGregorianDay(),
                hourOfDay,
                minute
        );

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
        edit = true;

        long timestamp = (long) invite.get("timestamp");

        taskCalendar.setTimeInMillis(timestamp);

        hourOfDay = taskCalendar.get(Calendar.HOUR_OF_DAY);
        minute = taskCalendar.get(Calendar.MINUTE);
        timeTxt.setText(new DecimalFormat("00").format(hourOfDay) + " : " + new DecimalFormat("00").format(minute));

        CalendarTool calendar2 = new CalendarTool(
                taskCalendar.get(Calendar.YEAR),
                taskCalendar.get(Calendar.MONTH) + 1,
                taskCalendar.get(Calendar.DAY_OF_MONTH)
        );

        year.setText(String.valueOf(calendar2.getIranianYear() - 1300));
        month.setText(String.valueOf(calendar2.getIranianMonth()));
        day.setText(String.valueOf(calendar2.getIranianDay()));

        note.setText((String) invite.get("note"));

    }

    public void openNoteDialog(View view) {

        FragmentManager fm = getSupportFragmentManager();
        EnterTextDialog enterTextDialog = new EnterTextDialog();
        enterTextDialog.show(fm, "Sample Fragment");

    }

    private class GoNextClass implements TextWatcher {

        EditText v;
        int n;

        public GoNextClass(EditText v, int n) {
            this.v = v;
            this.n = n;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (v.getText().length() == 2) {
                if (n == 1 || n == 2)
                    ((ViewGroup) v.getParent()).getChildAt(n).requestFocus();
                else findViewById(R.id.containerLayout).requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
