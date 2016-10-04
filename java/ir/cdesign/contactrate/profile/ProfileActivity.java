package ir.cdesign.contactrate.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.MedalAdapter;
import ir.cdesign.contactrate.models.MedalModel;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.utilities.Settings;

public class ProfileActivity extends AppCompatActivity implements MedalAdapter.MedalShow {

    ViewPager viewPager;
    ImageView medalImage,toolbarImage;
    MedalFragment medalFragment = new MedalFragment();

    TextView medalTitle,medalSubtitle,medalProgressText;
    ProgressBar medalProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.profile_viewPager);

        medalImage = (ImageView) findViewById(R.id.medal_last_iv);
        medalTitle = (TextView) findViewById(R.id.medal_last_title);
        medalSubtitle = (TextView) findViewById(R.id.medal_last_subtitle);
        medalProgressText = (TextView) findViewById(R.id.medal_progress_tv);
        medalProgress = (ProgressBar) findViewById(R.id.medal_progress);

        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        setToolbar();

    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Medals");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setBackgroundDrawable(null);

        }

    }

    @Override
    public void show(MedalModel medal) {
        medalImage.setImageResource(medal.imageId);
        medalTitle.setText(medal.title);
        medalSubtitle.setText(medal.subtitle);

        int percent = Math.max(0,Math.min(medal.progress*100/medal.completeMax,100));
        medalProgress.setProgress(percent);
        if (percent<100) {
            medalProgressText.setText("Progress : " + medal.progress + " / " + medal.completeMax);
        } else {
            PersianCalendar calendar = new PersianCalendar();
            calendar.setTimeInMillis(medal.achieved);
            String date = (Settings.calendarType == 1)?calendar.getPersianLongDate():
                    calendar.get(Calendar.YEAR) + "-"
                            + (calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH)) + "-" + calendar.get(Calendar.DAY_OF_MONTH)
                            + "  " + calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH);
            medalProgressText.setText("Achieved at : " + date);
        }
    }

    @Override
    public int getMedal() {
        return getIntent().getIntExtra("medal",0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                default:
                case 0:
                    return medalFragment;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                ProfileActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
