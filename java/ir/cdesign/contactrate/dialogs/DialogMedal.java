package ir.cdesign.contactrate.dialogs;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.MedalModel;
import ir.cdesign.contactrate.profile.ProfileActivity;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by amin pc on 25/08/2016.
 */
public class DialogMedal extends DialogFragment {

    PulsatorLayout pulsatorLayout;
    ImageView imageView;
    TextView textView;
    Button btn;

    public static FragmentManager fragmentManager;

    public MedalModel medal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medal_dialog,container,false);
        getDialog().requestWindowFeature(STYLE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pulsatorLayout = (PulsatorLayout) view.findViewById(R.id.pulsator);

        imageView = (ImageView) view.findViewById(R.id.medal_iv);
        textView = (TextView) view.findViewById(R.id.medal_tv);
        btn = (Button) view.findViewById(R.id.medal_btn);

        imageView.setImageResource(medal.imageId);
        textView.setText("Congragulations ! New Achievement : " + medal.title);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class).putExtra("medal",medal.id));
                dismiss();
            }
        });

        imageView.setAlpha(0f);
        imageView.setScaleX(2f);
        imageView.setScaleY(2f);

        textView.setAlpha(0f);
        btn.setAlpha(0f);


        imageView.animate().alpha(1).scaleY(1).scaleX(1).rotationBy(360*2).setDuration(1500).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                pulsatorLayout.start();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                textView.animate().setDuration(800).alpha(1).start();
                btn.animate().setDuration(800).alpha(1).start();
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
