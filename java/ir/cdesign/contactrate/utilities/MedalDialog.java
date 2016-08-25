package ir.cdesign.contactrate.utilities;

import android.animation.Animator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
//import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by amin pc on 25/08/2016.
 */
public class MedalDialog extends DialogFragment {

    //PulsatorLayout pulsatorLayout;
    ImageView imageView;
    TextView textView;
    LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medal_dialog,container,false);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //pulsatorLayout = (PulsatorLayout) view.findViewById(R.id.pulsator);

        imageView = (ImageView) view.findViewById(R.id.medal_iv);
        textView = (TextView) view.findViewById(R.id.medal_tv);
        linearLayout = (LinearLayout) view.findViewById(R.id.medal_ll);

        imageView.setAlpha(0f);
        imageView.setScaleX(2f);
        imageView.setScaleY(2f);

        linearLayout.setAlpha(0f);


        imageView.animate().alpha(1).scaleY(1).scaleX(1).rotationBy(360*2).setDuration(1500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                //pulsatorLayout.start();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                linearLayout.animate().setDuration(800).alpha(1).start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();

        return view;
    }
}
