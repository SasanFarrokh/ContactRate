package ir.cdesign.contactrate.Vision;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeNavigation;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.utilities.CalendarStrategy;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

/**
 * Created by amin pc on 02/09/2016.
 */
public class ActivityVisionAdd extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {
    private static final int IMAGE_PICKED = 12;

    private ImageView visionDate, repeatImage, visionImage,imageSet;
    private TextView dateText, repeatText, subject, note;
    private View done;

    private int repeaterCounter = 0;
    private static final String[] repeaterStrings = {"Daily","Weekly","Monthly"};

    private String visionPath = "";

    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";

    CalendarStrategy calendar = new CalendarStrategy(new PersianCalendar());

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision_add_activity);

        init();

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        relativeLayout = (RelativeLayout) findViewById(R.id.containerLayout);
        relativeLayout.setBackgroundResource(drawable);

        setToolbar();

    }

    private void init() {
        dateText = (TextView) findViewById(R.id.vision_add_date_text);
        visionDate = (ImageView) findViewById(R.id.vision_add_date_image);
        imageSet = (ImageView) findViewById(R.id.vision_image_set);
        visionImage = (ImageView) findViewById(R.id.vision_image);
        repeatText = (TextView) findViewById(R.id.vision_add_repeat_text);
        subject = (TextView) findViewById(R.id.vision_subject);
        note = (TextView) findViewById(R.id.vision_note);
        repeatImage = (ImageView) findViewById(R.id.vision_add_repeat_image);
        done = findViewById(R.id.vision_done);

        dateText.setOnClickListener(listener);
        visionDate.setOnClickListener(listener);
        repeatText.setOnClickListener(listener);
        repeatImage.setOnClickListener(listener);
        done.setOnClickListener(listener);
        imageSet.setOnClickListener(listener);
        visionImage.setOnClickListener(listener);
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Add Visions");
            actionBar.setBackgroundDrawable(null);

        }

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
                    DialogFragment dpd = CalendarStrategy.getDatePicker(
                            ActivityVisionAdd.this
                    );
                    dpd.show(getFragmentManager(), DATEPICKER);
                    break;
                case R.id.vision_add_repeat_image:
                case R.id.vision_add_repeat_text:
                    setRepeaterCounter();
                    break;
                case R.id.vision_done:
                    if (
                            DatabaseCommands.getInstance().addVision(
                                    subject.getText().toString(),
                                    note.getText().toString(),
                                    visionPath,
                                    repeaterCounter,
                                    calendar.getTimeInMillis()
                            )
                            ) {
                        Toast.makeText(ActivityVisionAdd.this, "Vision Added", Toast.LENGTH_SHORT).show();
                        finish();
                    } else
                        Toast.makeText(ActivityVisionAdd.this, "Error on Creating Vision", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.vision_image:
                case R.id.vision_image_set:
                    Intent i = new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, IMAGE_PICKED);

                    break;
            }
        }
    };

    private void setRepeaterCounter() {
        repeaterCounter += 1;
        repeaterCounter %= 3;
        repeatText.setText(repeaterStrings[repeaterCounter]);
    }

    @Override
    public void onDateSet(DialogFragment view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        calendar.set(year,monthOfYear,dayOfMonth);
        dateText.setText(calendar.getDate());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_PICKED:
                if (data == null) return;
                Uri selectedImage = data.getData();
                visionPath = selectedImage.toString();

                Bitmap image = HomeNavigation.getProfileBitmap(this, selectedImage);

                visionImage.setImageBitmap(image);

                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ActivityVisionAdd.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
