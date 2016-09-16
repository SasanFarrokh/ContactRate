package ir.cdesign.contactrate.utilities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 16/09/2016.
 */
public class BottomDrawer {

    Activity activity;
    Button toggle;
    LinearLayout container, colorToggle, fontToggle;
    TextView readText;
    boolean isDrawerOpen = false;
    boolean isNightModeActive = false;
    boolean isFontSizeLarge = false;

    public BottomDrawer(Activity activity) {
        this.activity = activity;

        toggle = (Button) activity.findViewById(R.id.bottomDrawerButton);
        container = (LinearLayout) activity.findViewById(R.id.bottomDrawerContainer);
        readText = (TextView) activity.findViewById(R.id.lesson_read_text);
        colorToggle = (LinearLayout) activity.findViewById(R.id.colorModeToggle);
        fontToggle = (LinearLayout) activity.findViewById(R.id.fontSizeToggle);


        toggle.setOnClickListener(listener);
        colorToggle.setOnClickListener(listener);
        fontToggle.setOnClickListener(listener);

        container.setY(120);
        toggle.setY(120);

    }

    private void colorModeToggle() {
        if (isNightModeActive) {
            //toggle to day mode
            readText.setBackgroundResource(R.color.backGroundTransparentWhite);
            readText.setTextColor(Color.parseColor("#333333"));
            isNightModeActive = false;
        } else if (!isNightModeActive) {
            readText.setBackgroundResource(R.color.backGroundTransparentBlack);
            readText.setTextColor(Color.parseColor("#f2f2f2"));
            isNightModeActive = true;
        }
    }

    private void fontSizeToggle() {
        float a = readText.getTextSize();
        if (isFontSizeLarge) {
            //small it
            readText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            isFontSizeLarge = false;

        } else if (!isFontSizeLarge) {
            readText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            isFontSizeLarge = true;
        }
    }

    private void open() {
        container.animate().yBy(-120).start();
        toggle.animate().yBy(-120).start();
        isDrawerOpen = true;
    }

    private void close() {
        container.animate().yBy(120).start();
        toggle.animate().yBy(120).start();
        isDrawerOpen = false;
    }

    public void decide() {
        if (!isDrawerOpen) {
            open();
        } else if (isDrawerOpen) {
            close();
        }
    }


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottomDrawerButton: {
                    decide();
                    break;
                }
                case R.id.fontSizeToggle: {
                    fontSizeToggle();
                    break;
                }
                case R.id.colorModeToggle: {
                    colorModeToggle();
                    break;
                }
            }
        }
    };

}
