package ir.cdesign.contactrate.demoedition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 21/09/2016.
 */
public class LessonDemo extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_fragment,container,false);

        init(view);

        return view;
    }

    private void init(View view){

        TextView demo = (TextView) view.findViewById(R.id.demo_text);
        Button dismiss = (Button) view.findViewById(R.id.demo_btn_back);
        Button proceed = (Button) view.findViewById(R.id.demo_btn_proceed);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        demo.setText(Html.fromHtml(getString(R.string.lessons_demo)));

    }
}