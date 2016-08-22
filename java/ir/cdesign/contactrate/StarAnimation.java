package ir.cdesign.contactrate;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Created by Sasan on 2016-08-23.
 */
public class StarAnimation implements ValueAnimator.AnimatorUpdateListener {

    float satur;
    float[] hsv;

    public static ValueAnimator anim;

    ImageView imageView;
    public StarAnimation(ImageView v) {

        imageView = v;
        hsv = new float[3];
        anim = ValueAnimator.ofFloat(0f,1f);
        anim.setDuration(500);
        anim.addUpdateListener(this);
        Color.colorToHSV(MainActivity.instance.getResources().getColor(R.color.starTint),hsv);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        hsv[1] = (float) animation.getAnimatedValue();
        imageView.setColorFilter(Color.HSVToColor(hsv));
    }
    public void start() {
        anim.start();
    }
}
