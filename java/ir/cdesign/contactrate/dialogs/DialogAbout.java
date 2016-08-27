package ir.cdesign.contactrate.dialogs;

import android.animation.Animator;
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

/**
 * Created by amin pc on 26/08/2016.
 */
public class DialogAbout extends DialogFragment {

    LinearLayout linearLayout;
    ImageView imageView;
    TextView textOne , textTwo , textThree;
    View line;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_aboutus,container,false);

        linearLayout = (LinearLayout) view.findViewById(R.id.LinearLayout);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        imageView = (ImageView) linearLayout.getChildAt(0);
        textOne = (TextView) linearLayout.getChildAt(1);
        line = (View) linearLayout.getChildAt(2);
        textTwo = (TextView) linearLayout.getChildAt(3);
        textThree = (TextView) linearLayout.getChildAt(4);

        imageView.setAlpha(0f);
        imageView.setTranslationY(-20);
        textOne.setAlpha(0f);
        textOne.setTranslationY(-20);
        line.setAlpha(0f);
        line.setTranslationY(-20);
        textThree.setAlpha(0f);
        textThree.setTranslationY(-20);
        textTwo.setTranslationY(-20);
        textTwo.setAlpha(0f);

        imageView.animate().translationY(0).alpha(1).setDuration(1000).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                textOne.animate().alpha(1).translationY(0).setDuration(1200).start();
                line.animate().alpha(1).translationY(0).setDuration(1300).start();
                textTwo.animate().alpha(1).translationY(0).setDuration(1400).start();
                textThree.animate().alpha(1).translationY(0).setDuration(1500).start();
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

        return view;
    }
}