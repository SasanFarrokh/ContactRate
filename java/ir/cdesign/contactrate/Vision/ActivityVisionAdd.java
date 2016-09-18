package ir.cdesign.contactrate.Vision;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.time.RadialPickerLayout;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.time.TimePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianDateParser;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

/**
 * Created by amin pc on 02/09/2016.
 */
public class ActivityVisionAdd extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    private Button backButton;
    private ImageView visionDate, visionRepeat, visionImage;
    private TextView dateText, repeatText, subject, note;
    private View done;

    private String visionPath = "";
    private int reminder = 0;

    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";

    private long timestamp;

    FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision_add_activity);

        init();

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        frameLayout= (FrameLayout) findViewById(R.id.FrameParent);
        frameLayout.setBackgroundResource(drawable);

    }

    private void init() {
        dateText = (TextView) findViewById(R.id.vision_add_date_text);
        visionDate = (ImageView) findViewById(R.id.vision_add_date_image);
        repeatText = (TextView) findViewById(R.id.vision_add_repeat_text);
        subject = (TextView) findViewById(R.id.vision_subject);
        note = (TextView) findViewById(R.id.vision_note);
        visionRepeat = (ImageView) findViewById(R.id.vision_add_repeat_image);
        backButton = (Button) findViewById(R.id.toolbar_iv);
        done = findViewById(R.id.vision_done);

        dateText.setOnClickListener(listener);
        visionDate.setOnClickListener(listener);
        repeatText.setOnClickListener(listener);
        visionRepeat.setOnClickListener(listener);
        backButton.setOnClickListener(listener);
        done.setOnClickListener(listener);
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_iv:
                    finish();
                    break;
                case R.id.vision_add_date_image:
                case R.id.vision_add_date_text:
                    PersianCalendar now = new PersianCalendar();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            ActivityVisionAdd.this,
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.show(getFragmentManager(), DATEPICKER);
                    break;
                case R.id.vision_add_repeat_image:
                case R.id.vision_add_repeat_text:

                    break;
                case R.id.vision_done:
                    if (
                            DatabaseCommands.getInstance().addVision(
                                    subject.getText().toString(),
                                    note.getText().toString(),
                                    visionPath,
                                    reminder,
                                    timestamp
                            )
                            ) {
                        Toast.makeText(ActivityVisionAdd.this, "Vision Added", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(ActivityVisionAdd.this, "Error on Creating Vision", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
        dateText.setText(date);
        timestamp = (new PersianDateParser(date, "/")).getPersianDate().getTimeInMillis();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = "You picked the following time: " + hourString + ":" + minuteString;
//        timeTextView.setText(time);
    }
}
