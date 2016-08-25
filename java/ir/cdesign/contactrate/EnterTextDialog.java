package ir.cdesign.contactrate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by amin pc on 25/08/2016.
 */
public class EnterTextDialog extends DialogFragment{

    TextView textView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doalog_text_enter,container,false);

        textView = (TextView) getActivity().findViewById(R.id.note);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        View btn = getView().findViewById(R.id.dialog_note_submit);
        final EditText editText = (EditText) getView().findViewById(R.id.note_edit_text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(editText.getText().toString());
                dismiss();
            }
        });
    }
}
