package ir.cdesign.contactrate.utilities;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 06/09/2016.
 */
public class CustomRadioGroup {

    Activity activity;
    RelativeLayout persian , english;

    ImageView persianImage , englishImage;

    String persianTag = "persian";
    String englishTag = "english";
    String langToggle = "english";

    boolean englishClicked , persianClicked;
    boolean englishSelected , persianSelected;

    public CustomRadioGroup(Activity activity) {

        this.activity = activity;

        persian = (RelativeLayout) this.activity.findViewById(R.id.persian);
        english = (RelativeLayout) this.activity.findViewById(R.id.english);

        persianImage = (ImageView) this.activity.findViewById(R.id.persian_img);
        englishImage = (ImageView) this.activity.findViewById(R.id.english_img);

        persian.setOnClickListener(listener);
        english.setOnClickListener(listener);

        //Tempo
        englishSelected = true;

        onStartAnimate();
    }

    private void onStartAnimate(){

        if (englishSelected){
            englishImage.animate().scaleXBy(.3f).scaleYBy(0.3f).setDuration(250).start();
            langToggle = englishTag; //default language is english
        } else if (persianSelected){
            persianImage.animate().scaleXBy(.3f).scaleYBy(.3f).setDuration(250).start();
            langToggle = persianTag; //default language is persian
        }
    }

    private void Animate(){
        if (langToggle == englishTag && persianClicked){

            persianImage.animate().scaleXBy(.3f).scaleYBy(.3f).setDuration(250).start();
            englishImage.animate().scaleYBy(-0.3f).scaleXBy(-0.3f).setDuration(250).start();
        }else if(langToggle == persianTag && englishClicked){

            englishImage.animate().scaleXBy(.3f).scaleYBy(0.3f).setDuration(250).start();
            persianImage.animate().scaleYBy(-0.3f).scaleXBy(-0.3f).setDuration(250).start();
        }
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.persian:{

                    persianClicked = true;
                    Animate();
                    break;
                }
                case R.id.english:{

                    englishClicked = true;
                    Animate();
                    break;
                }
            }
        }
    };

}
