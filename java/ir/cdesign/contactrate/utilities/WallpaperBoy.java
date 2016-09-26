package ir.cdesign.contactrate.utilities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 06/09/2016.
 */
public class WallpaperBoy {

    public int manSitting(int manInTheMiddle, Activity activity) {
        int manSitting = R.drawable.background;
        switch (manInTheMiddle) {
            case 0:
                manSitting = R.drawable.bg_2;
                break;
            case 1:
                manSitting = R.drawable.bg_3;
                break;
            case 2:
                manSitting = R.drawable.bg_4;
                break;
            case 3:
                manSitting = R.drawable.bg_5;
                break;
            case 4:
                manSitting = R.drawable.bg_6;
                break;
            case 5:
                manSitting = R.drawable.bg_7;
                break;
            case 6:
                manSitting = R.drawable.bg_8;
                break;
            case 7:
                manSitting = R.drawable.bg_9;
                break;
            case 8:
                manSitting = R.drawable.bg_10;
                break;
            case 9:
                manSitting = R.drawable.bg_11;
                break;
            case 10:
                manSitting = R.drawable.bg_12;
                break;
            case 11:
                manSitting = R.drawable.bg_13;
                break;
            case 12:
                manSitting = R.drawable.bg_14;
                break;
            case 13:
                manSitting = R.drawable.bg_15;
                break;
            case 14:
                manSitting = R.drawable.bg_16;
                break;
            case 15:
                manSitting = R.drawable.bg_17;
                break;
            case 16:
                manSitting = R.drawable.bg_18;
                break;
            case 17:
                manSitting = R.drawable.bg_19;
                break;
            case 18:
                manSitting = R.drawable.bg_20;
                break;
            case 19:
                manSitting = R.drawable.bg_21;
                break;
            case 20:
                manSitting = R.drawable.background;
                break;
        }
        return manSitting;
    }
}
