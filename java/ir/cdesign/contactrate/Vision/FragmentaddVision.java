package ir.cdesign.contactrate.Vision;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 02/09/2016.
 */
public class FragmentaddVision extends DialogFragment {
    Button backButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vision_add_fragment,container,false);


        init(view);

        getDialog().requestWindowFeature(STYLE_NO_TITLE);

        return view;
    }

    private void init(View view){

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

            }
        }
    };

    public void onResume()
    {
        super.onResume();
        Window window = getDialog().getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,1100);
        window.setGravity(Gravity.CENTER);
        //TODO:
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        ((Visions)getActivity()).fab.show();
    }
}
