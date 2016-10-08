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

import java.util.HashMap;

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

    private long edit = 0;

    private ImageView visionDate, visionImage, imageSet;
    private TextView dateText, subject, note;
    private View done;

    private int repeaterCounter = -1;
    private static final String[] repeaterStrings = {"None", "Daily", "Weekly", "Monthly"};

    private String visionPath = "";

    private static final String TIMEPICKER = "TimePickerDialog",
            DATEPICKER = "DatePickerDialog";

    CalendarStrategy calendar = new CalendarStrategy(new PersianCalendar());

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vision_add_activity);

        edit = getIntent().getLongExtra("edit", 0);

        init();

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle, this);
        relativeLayout = (RelativeLayout) findViewById(R.id.containerLayout);
        relativeLayout.setBackgroundResource(drawable);

        setToolbar();

        if (edit != 0) setById();
    }

    private void setById() {
        HashMap vision = DatabaseCommands.getInstance(this).getVision(edit).get(0);
        subject.setText((String) vision.get("subject"));
        note.setText((String) vision.get("note"));
        visionPath = (String) vision.get("image");
        repeaterCounter = (int) vision.get("reminder");
        calendar.setTimeInMillis((Long) vision.get("timestamp"));

        if (visionPath != null) {
            Bitmap image = HomeNavigation.getProfileBitmap(this, Uri.parse(visionPath));
            if (image != null) {
                visionImage.setImageBitmap(image);
                visionImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
        }
        dateText.setText(calendar.getDate());

    }

    private void init() {
        dateText = (TextView) findViewById(R.id.vision_add_date_text);
        visionDate = (ImageView) findViewById(R.id.vision_add_date_image);
        imageSet = (ImageView) findViewById(R.id.vision_image_set);
        visionImage = (ImageView) findViewById(R.id.vision_image);
        subject = (TextView) findViewById(R.id.vision_subject);
        note = (TextView) findViewById(R.id.vision_note);
        done = findViewById(R.id.vision_done);

        dateText.setOnClickListener(listener);
        visionDate.setOnClickListener(listener);
        done.setOnClickListener(listener);
        imageSet.setOnClickListener(listener);
        visionImage.setOnClickListener(listener);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(R.string.addvision);
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
                case R.id.vision_done:
                    boolean b;
                    if (edit == 0) {
                        b = DatabaseCommands.getInstance(ActivityVisionAdd.this).addVision(
                                subject.getText().toString(),
                                note.getText().toString(),
                                visionPath,
                                repeaterCounter,
                                calendar.getTimeInMillis()
                        );
                    } else {
                        b = DatabaseCommands.getInstance(ActivityVisionAdd.this).editVision(
                                edit,
                                subject.getText().toString(),
                                note.getText().toString(),
                                visionPath,
                                repeaterCounter,
                                calendar.getTimeInMillis()
                        );
                    }
                    if (b) {
                        Toast.makeText(ActivityVisionAdd.this, "Vision is set", Toast.LENGTH_SHORT).show();
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

    /*private void setRepeaterCounter() {
        repeaterCounter += 1;
        repeaterCounter %= 4;
        repeatText.setText(repeaterStrings[repeaterCounter]);
    }*/

    @Override
    public void onDateSet(DialogFragment view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        calendar.set(year, monthOfYear, dayOfMonth);
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
                if (image != null) {
                    visionImage.setImageBitmap(image);
                    visionImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }

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
