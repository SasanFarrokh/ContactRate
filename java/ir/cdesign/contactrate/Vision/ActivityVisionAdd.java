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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.time.RadialPickerLayout;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.time.TimePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;

/**
 * Created by amin pc on 02/09/2016.
 */
public class ActivityVisionAdd extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {
    Button backButton;
    ImageView visionDate, visionRepeat;
    TextView dateText, repeatText;
    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision_add_activity);

        init();

    }

    private void init() {
        dateText = (TextView) findViewById(R.id.vision_add_date_text);
        visionDate = (ImageView) findViewById(R.id.vision_add_date_image);
        repeatText = (TextView) findViewById(R.id.vision_add_repeat_text);
        visionRepeat = (ImageView) findViewById(R.id.vision_add_repeat_image);

        dateText.setOnClickListener(listener);
        visionDate.setOnClickListener(listener);
        repeatText.setOnClickListener(listener);
        visionRepeat.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.vision_add_date_image: {
                    PersianCalendar now = new PersianCalendar();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            ActivityVisionAdd.this,
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.show(getFragmentManager(), DATEPICKER);
                }
                    break;
                case R.id.vision_add_date_text: {
                    PersianCalendar now = new PersianCalendar();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            ActivityVisionAdd.this,
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.show(getFragmentManager(), DATEPICKER);
                }
                    break;
                case R.id.vision_add_repeat_image:

                    break;
                case R.id.vision_add_repeat_text:

                    break;
            }
        }
    };

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//        dateTextView.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String time = "You picked the following time: " + hourString + ":" + minuteString;
//        timeTextView.setText(time);
    }
}
