package ir.cdesign.contactrate.dialogs;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 26/08/2016.
 */
public class DialogUpdate extends DialogFragment {

    Button accept, deny;

    int result = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update,container,false);

        accept = (Button) view.findViewById(R.id.btn_update_accept);
        deny = (Button) view.findViewById(R.id.btn_update_cancel);

        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = 0;
                dismiss();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = 1;
                dismiss();
            }
        });


        return view;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (result == 0) getActivity().finish();
        if (result == 1) {
            Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse("market://details?id=ir.cdesign.contactrate"));
            startActivity(intent);
        }
    }
}
