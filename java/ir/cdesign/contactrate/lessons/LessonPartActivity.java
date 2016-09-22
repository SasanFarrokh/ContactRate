package ir.cdesign.contactrate.lessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import ir.cdesign.contactrate.R;

public class LessonPartActivity extends AppCompatActivity implements View.OnClickListener {

    //demo edition
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_part_activity);

        init();
    }

    private void init(){
        frameLayout = (FrameLayout) findViewById(R.id.lesson_part_row_layout);

        frameLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lesson_part_row_layout:
                startActivity(new Intent(LessonPartActivity.this,ReadLessonActivity.class));
                break;
        }
    }
}
