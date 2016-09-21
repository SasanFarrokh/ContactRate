package ir.cdesign.contactrate.lessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.utilities.BottomDrawer;

public class ReadLessonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_lesson);

        // read utilities setup
        BottomDrawer bottomDrawer = new BottomDrawer(this);

    }
}
