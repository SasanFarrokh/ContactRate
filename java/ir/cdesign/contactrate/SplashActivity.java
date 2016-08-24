package ir.cdesign.contactrate;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SplashActivity extends AppCompatActivity {

    Boolean regHelp;
    ImageView imageView ,tutImg;
    TextView textView, textViewTwo;
    EditText editText;
    LinearLayout linearLayout, splashTutText, btnLayouts;
    Button nextButton, nextButtonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, MyService.class));
        // Remove The Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        init();
        // Create Shared Preference
        SharedPreferences reg = getApplicationContext().getSharedPreferences("REG", MODE_PRIVATE);

        onStartViewAnimator();

        regHelp = reg.getBoolean("STATE", false);



    }

    private void init() {
        editText = (EditText) findViewById(R.id.splash_edit_text);
        textView = (TextView) findViewById(R.id.splash_text);
        nextButtonTwo = (Button) findViewById(R.id.next_button_two);
        imageView = (ImageView) findViewById(R.id.splash_logo);
        nextButton = (Button) findViewById(R.id.next_button);
        linearLayout = (LinearLayout) findViewById(R.id.company_layout);
        textViewTwo = (TextView) findViewById(R.id.splash_text_two);
        splashTutText = (LinearLayout) findViewById(R.id.splash_tutorial_layout);
        btnLayouts = (LinearLayout) findViewById(R.id.splash_btn_layouts);
        tutImg = (ImageView) findViewById(R.id.tut_img);

        tutImg.setVisibility(View.INVISIBLE);
        tutImg.setAlpha(0f);
        tutImg.setTranslationY(1000);

        btnLayouts.setVisibility(View.INVISIBLE);
        btnLayouts.setTranslationY(250);

        splashTutText.setVisibility(View.INVISIBLE);
        splashTutText.setAlpha(0);

        textViewTwo.setAlpha(0);
        editText.setAlpha(0);
        editText.setVisibility(View.INVISIBLE);

        textView.setAlpha(0f);
        textView.setScaleX(0);
        textView.setScaleY(0);

        imageView.setAlpha(0f);
        imageView.setScaleX(0);
        imageView.setScaleY(0);

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
                if (regHelp){
                    nextButton.setVisibility(View.INVISIBLE);
                }
                nextButton.animate().translationY(0).setDuration(600).start();


                if (regHelp) {
                    Thread thread = new Thread() {
                        public void run() {
                            try {
                                sleep(2500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    };
                    thread.start();

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

        imageView.animate().yBy(-50).alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textView.animate().yBy(-200).setDuration(300).start();
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
        SharedPreferences reg = getApplicationContext().getSharedPreferences("REG", MODE_PRIVATE);
        SharedPreferences.Editor editor = reg.edit();
        editor.putBoolean("STATE", true).apply();
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
            splashTutText = (LinearLayout) findViewById(R.id.splash_tutorial_layout);
            btnLayouts = (LinearLayout) findViewById(R.id.splash_btn_layouts);
            nextButtonTwo = (Button) findViewById(R.id.next_button_two);
            editText = (EditText) findViewById(R.id.splash_edit_text);
            tutImg = (ImageView) findViewById(R.id.tut_img);

            nextButtonTwo.animate().alpha(0).translationYBy(-20).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    btnLayouts.setVisibility(View.VISIBLE);
                    btnLayouts.animate().alpha(1).translationY(0).setDuration(500).start();
                    editText.animate().alpha(0).translationYBy(-20).start();
                    textView.animate().alpha(0).translationYBy(-20).start();
                    textViewTwo.animate().alpha(0).translationYBy(-20).start();
                    editText.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    textViewTwo.setVisibility(View.INVISIBLE);
                    splashTutText.setVisibility(View.VISIBLE);
                    tutImg.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    splashTutText.animate().alpha(1).setDuration(400).start();
                    tutImg.animate().translationY(0).alpha(1).setDuration(500);
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

    public void onYes(View view) {

    }


    public void onNo(View view) {
        String userName = String.valueOf(editText.getText());
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
        regSave();
        finish();
    }
}
