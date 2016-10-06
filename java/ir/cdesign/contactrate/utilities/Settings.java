package ir.cdesign.contactrate.utilities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.dialogs.DialogImageDisplay;
import ir.cdesign.contactrate.dialogs.DialogSettings;
import ir.cdesign.contactrate.homeScreen.DisplayImageActivity;
import ir.cdesign.contactrate.homeScreen.HomeScreen;

public class Settings extends AppCompatActivity {

    public static final int ENGLISH = 0;
    public static final int PERSIAN = 1;
    public static final int GREGORIAN = 0;
    public static final int SHAMSI = 1;
    public static final String[] LANGUAGES = {"en", "fa"};

    LinearLayout backgroundOpen;
    RadioGroup calendarTypeRg;
    CheckBox reminderSetCb;
    ViewGroup container;

    public static int language = 0;
    public static int calendarType = 1;
    public static boolean reminderSet = true;
    private boolean langChanged = false;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        container = (ViewGroup) findViewById(R.id.english).getParent();

        //Background Setting ! :D
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle, this);
        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
        linearLayout.setBackgroundResource(drawable);

        backgroundOpen = (LinearLayout) findViewById(R.id.background_open);
        backgroundOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this, DisplayImageActivity.class));
            }
        });

        for (int i = 0; i < 2; i++) {
            container.getChildAt(i).setOnClickListener(new langItemClick(i));
        }

        calendarTypeRg = (RadioGroup) findViewById(R.id.calendar_type_rg);
        reminderSetCb = (CheckBox) findViewById(R.id.reminder_set_cb);

        calendarTypeRg.check(calendarTypeRg.getChildAt(calendarType).getId());
        calendarTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (calendarTypeRg.getCheckedRadioButtonId()) {
                    default:
                    case R.id.calendar_shamsi:
                        calendarType = 1;
                        break;
                    case R.id.calendar_greg:
                        calendarType = 0;
                        break;
                }
            }
        });
        reminderSetCb.setChecked(reminderSet);
        reminderSetCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reminderSet = isChecked;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        selectLang(language);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle, this);

        findViewById(R.id.background_thumbnail).setBackgroundResource(drawable);
    }

    private void selectLang(int lang) {
        language = lang;
        for (int i = 0; i < 2; i++) {
            ImageView iv = (ImageView) ((ViewGroup) container.getChildAt(i)).getChildAt(0);
            float scale;
            if (i == lang) {
                scale = 1.25f;
                iv.setColorFilter(Color.parseColor("#40ffffff"));
            } else {
                scale = 1f;
                iv.setColorFilter(Color.parseColor("#00ffffff"));
            }
            iv.animate().scaleX(scale).scaleY(scale).start();
        }
    }

    private class langItemClick implements View.OnClickListener {

        private int i;

        public langItemClick(int i) {
            this.i = i;
        }

        @Override
        public void onClick(View v) {
            selectLang(i);
            String lang = "en";
            switch (language) {
                case 0:
                    lang = "en";
                    break;
                case 1:
                    lang = "fa";
                    break;
            }
            setLocale(lang);
            langChanged = true;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Settings.this, HomeScreen.class));
        finish();
    }

    @Override
    protected void onPause() {
        settingsSave();
        super.onPause();
    }

    protected void settingsSave() {
        String lang = "en";
        switch (language) {
            case 0:
                lang = "en";
                break;
            case 1:
                lang = "fa";
                break;
        }
        getSharedPreferences(MainActivity.PREF, MODE_PRIVATE).edit()
                .putString("lang", lang)
                .putInt("calendar", calendarType)
                .putBoolean("reminder", reminderSet)
                .commit();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        /*if (langChanged)
            Toast.makeText(this, "Restart app for take changes", Toast.LENGTH_SHORT).show();*/
    }

    public static int getLangIndex(String lang) {
        return Arrays.asList(LANGUAGES).indexOf(lang);
    }
}
