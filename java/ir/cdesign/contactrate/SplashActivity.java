package ir.cdesign.contactrate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
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
import android.widget.ViewFlipper;

import java.util.Locale;

import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.Settings;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{


    ViewFlipper viewFlipper ;
    TextView signUp , langSelect;
    ImageView cLogo , designLogo , mlmLogo;
    ImageView account;

    EditText nameSignUp , numberSignUp ;
    Button submitSignUp;

    Boolean regHelp;

    LinearLayout registrationLayout ;

    SharedPreferences reg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, MyService.class));
        // Remove The Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        // Create Shared Preference
        reg = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE);

        init();
    }


    private void init(){

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        cLogo = (ImageView) findViewById(R.id.clogo);
        designLogo = (ImageView) findViewById(R.id.designlogo);
        mlmLogo = (ImageView) findViewById(R.id.mlm_logo);
        registrationLayout = (LinearLayout) findViewById(R.id.registration_layout);
        signUp = (TextView) findViewById(R.id.signup_btn);
        langSelect = (TextView) findViewById(R.id.langSet);
        account = (ImageView) findViewById(R.id.account_img_sign);
        numberSignUp = (EditText) findViewById(R.id.number_input_sign);
        nameSignUp = (EditText) findViewById(R.id.name_input_sign);
        submitSignUp = (Button) findViewById(R.id.submit_sign);

        viewFlipper.setDisplayedChild(0);

        //defaults
        cLogo.setAlpha(0f);         designLogo.setAlpha(0f);
        cLogo.setTranslationY(5);   designLogo.setTranslationX(5);
        mlmLogo.setTranslationY(-50) ; mlmLogo.setAlpha(0f);
        registrationLayout.setAlpha(0);

        account.setAlpha(0f);       account.setTranslationY(-20);
        nameSignUp.setAlpha(0f);    nameSignUp.setTranslationY(-20);
        numberSignUp.setAlpha(0f);  numberSignUp.setTranslationY(-20);
        submitSignUp.setAlpha(0f);  submitSignUp.setTranslationY(-20);

        //listeners
        signUp.setOnClickListener(this);
        submitSignUp.setOnClickListener(this);

        String lang = reg.getString("lang","en");
        Settings.language = Settings.getLangIndex(lang);
        Settings.calendarType = reg.getInt("calendar",1);
        Settings.reminderSet = reg.getBoolean("reminder",true);
        setLocale(lang);

        regHelp = !reg.getString("userName","").isEmpty();

        if (regHelp) {
            final Handler handler = new Handler();
            signUp.setVisibility(View.GONE);
            langSelect.setVisibility(View.GONE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, HomeScreen.class));
                    finish();
                }
            },3000);
        } else if (!regHelp){
            mainAnimator();
        }
        mainAnimator();
    }

    private void mainAnimator(){
        mlmLogo.animate().setStartDelay(300).alpha(1).translationY(0).setDuration(700).start();
        registrationLayout.animate().alpha(1).setStartDelay(900).setDuration(500);
        cLogo.animate().translationY(0).alpha(1).setDuration(500).setStartDelay(1500).start();
        designLogo.animate().alpha(1).translationX(0).setDuration(800).setStartDelay(1800).start();
    }

    private void signUpAnimator() {
        account.animate().setStartDelay(300).alpha(1).translationY(0).setDuration(500).start();
        nameSignUp.animate().setStartDelay(400).alpha(1).translationY(0).setDuration(500).start();
        numberSignUp.animate().setStartDelay(500).alpha(1).translationY(0).setDuration(500).start();
        submitSignUp.animate().setStartDelay(700).alpha(1).translationY(0).setDuration(500).start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.signup_btn :{
                viewFlipper.setDisplayedChild(1);
                signUpAnimator();
                break;
            }
            case R.id.submit_sign:{
                regSave();
                startActivity(new Intent(SplashActivity.this , HomeScreen.class));
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncServerCheck(this).execute();
    }

    private void regSave() {
        reg = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = reg.edit();
        editor.putString("userName", titleCase(nameSignUp.getText().toString()));
        editor.putString("phoneNumber", titleCase(numberSignUp.getText().toString()));
        editor.apply();
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

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Log.i("sasan","local set to " + lang);
    }

}
