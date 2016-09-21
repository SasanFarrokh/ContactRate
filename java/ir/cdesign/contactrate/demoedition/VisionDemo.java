package ir.cdesign.contactrate.demoedition;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.Vision.Visions;

public class VisionDemo extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.demo_vision_activity,container,false);

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

        demo.setText(R.string.DemoText);

    }
}
