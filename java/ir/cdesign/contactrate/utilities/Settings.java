package ir.cdesign.contactrate.utilities;

<<<<<<< HEAD
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
=======
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
>>>>>>> refs/remotes/origin/master
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Locale;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.dialogs.DialogImageDisplay;
import ir.cdesign.contactrate.dialogs.DialogSettings;

public class Settings extends AppCompatActivity {

    public static final int ENGLISH = 0;
    public static final int PERSIAN = 1;
    public static final String[] LANGUAGES = {"en","fa"};

    LinearLayout backgroundOpen;
    ViewGroup container;

    public static int language = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        container = (ViewGroup) findViewById(R.id.english).getParent();

        backgroundOpen = (LinearLayout) findViewById(R.id.background_open);
        backgroundOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImageDisplay dialogImageDisplay = new DialogImageDisplay();
                dialogImageDisplay.show(getSupportFragmentManager(),"ayeah");
            }
        });

<<<<<<< HEAD
        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(null);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
=======
        for (int i = 0; i < 2; i++) {
            container.getChildAt(i).setOnClickListener(new langItemClick(i));
        }
        selectLang(language);
    }

    private void selectLang(int lang) {
        language = lang;
        for (int i = 0; i < 2 ; i++) {
            ImageView iv = (ImageView) ((ViewGroup) container.getChildAt(i)).getChildAt(0);
            float scale;
            if (i == lang) {
                scale = 1.25f;
                iv.setColorFilter(Color.parseColor("#40ffffff"));
            }
            else {
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
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String lang = "en";
        switch (language) {
            case 0:
                lang = "en";
                break;
            case 1:
                lang = "fa";
                break;
        }
        getSharedPreferences(MainActivity.PREF,MODE_PRIVATE).edit().putString("lang",lang).commit();
        Toast.makeText(this,"Restart app for take changes",Toast.LENGTH_SHORT).show();
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, this.getClass());
        startActivity(refresh);
        finish();
    }
    public static int getLangIndex(String lang) {
        return Arrays.asList(LANGUAGES).indexOf(lang);
>>>>>>> refs/remotes/origin/master
    }
}
