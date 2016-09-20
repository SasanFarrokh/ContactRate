package ir.cdesign.contactrate.demoedition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import ir.cdesign.contactrate.R;

public class VisionDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.demo_vision_activity);



    }
}
