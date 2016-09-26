package ir.cdesign.contactrate;

import android.Manifest;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.Settings;


public class SplashActivity extends AppCompatActivity {

    Boolean regHelp;
    ImageView imageView;
    TextView textView, textViewTwo;
    EditText editText;
    LinearLayout linearLayout;
    Button nextButton, nextButtonTwo;

    SharedPreferences reg;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, MyService.class));
        // Remove The Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Create Shared Preference
        reg = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE);




        //
        String lang = reg.getString("lang","en");
        Settings.language = Settings.getLangIndex(lang);
        setLocale(lang);

        setContentView(R.layout.activity_splash);
        init();

        onStartViewAnimator();

        regHelp = !reg.getString("userName","").isEmpty();
    }


    private void init() {
        editText = (EditText) findViewById(R.id.splash_edit_text);
        textView = (TextView) findViewById(R.id.splash_text);
        nextButtonTwo = (Button) findViewById(R.id.next_button_two);
        imageView = (ImageView) findViewById(R.id.splash_logo);
        nextButton = (Button) findViewById(R.id.next_button);
        linearLayout = (LinearLayout) findViewById(R.id.company_layout);
        textViewTwo = (TextView) findViewById(R.id.splash_text_two);

        textViewTwo.setAlpha(0);
        editText.setAlpha(0);
        editText.setVisibility(View.INVISIBLE);

        textView.setAlpha(0f);
        textView.setScaleX(0.8f);
        textView.setScaleY(0.8f);

        imageView.setAlpha(0f);
        imageView.setScaleX(0.8f);
        imageView.setScaleY(0.8f);

        linearLayout.setTranslationY(150);

        nextButton.setTranslationY(250);
        nextButtonTwo.setTranslationY(250);
    }

    private void onStartViewAnimator() {
        imageView = (ImageView) findViewById(R.id.splash_logo);
        textView = (TextView) findViewById(R.id.splash_text);
        linearLayout = (LinearLayout) findViewById(R.id.company_layout);
        nextButton = (Button) findViewById(R.id.next_button);

        imageView.animate().alpha(1).scaleX(1f).scaleY(1f).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textView.animate().scaleX(1).scaleY(1).alpha(1).setDuration(800).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                linearLayout.animate().translationY(0).setDuration(500).start();
                if (regHelp) {
                    nextButton.setVisibility(View.INVISIBLE);
                }
                nextButton.animate().translationY(0).setDuration(600).start();


                if (regHelp) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, HomeScreen.class));
                            finish();
                        }
                    },1500);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

    }

    private void viewAnimator() {
        textView = (TextView) findViewById(R.id.splash_text);
        textViewTwo = (TextView) findViewById(R.id.splash_text_two);
        imageView = (ImageView) findViewById(R.id.splash_logo);
        editText = (EditText) findViewById(R.id.splash_edit_text);
        nextButtonTwo = (Button) findViewById(R.id.next_button_two);
        nextButton = (Button) findViewById(R.id.next_button);

        textView.animate().yBy(-200).setDuration(400).start();
        imageView.animate().yBy(-50).alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textViewTwo.animate().yBy(-200).alpha(1).setDuration(300).start();
                editText.setVisibility(View.VISIBLE);
                editText.animate().yBy(-150).alpha(1).setDuration(300).start();
                nextButton.animate().alpha(0).translationYBy(-20).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                nextButton.setVisibility(View.INVISIBLE);
                nextButtonTwo.animate().alpha(1).translationY(0).setDuration(500).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    private void regSave() {
        SharedPreferences reg = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = reg.edit();
        editor.putString("userName", titleCase(editText.getText().toString())).apply();
    }

    public void onOkClick(View view) {
        viewAnimator();
    }

    public void onDoneClick(View view) {
        String userName = String.valueOf(editText.getText());
        if (userName.equals("")) {
            Toast.makeText(this, "You Can't Leave The Name Field Empty", Toast.LENGTH_LONG).show();
        } else {
            textView = (TextView) findViewById(R.id.splash_text);
            textViewTwo = (TextView) findViewById(R.id.splash_text_two);
            editText = (EditText) findViewById(R.id.splash_edit_text);
            nextButtonTwo = (Button) findViewById(R.id.next_button_two);
            editText = (EditText) findViewById(R.id.splash_edit_text);

            nextButtonTwo.animate().alpha(0).translationYBy(-20).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    editText.animate().alpha(0).translationYBy(-20).start();
                    textView.animate().alpha(0).translationYBy(-20).start();
                    textViewTwo.animate().alpha(0).translationYBy(-20).start();
                    editText.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    textViewTwo.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SplashActivity.this, HomeScreen.class));
                    regSave();
                    finish();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncServerCheck(this).execute();
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Log.i("sasan","local set to " + lang);
    }

    public static String titleCase(String str) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : str.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
