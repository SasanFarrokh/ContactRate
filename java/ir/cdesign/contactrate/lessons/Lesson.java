package ir.cdesign.contactrate.lessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.demoedition.LessonDemo;
import ir.cdesign.contactrate.demoedition.VisionDemo;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class Lesson extends AppCompatActivity implements View.OnClickListener{

    Button all ,mark ;
    RecyclerView recyclerView;
    TextView toolbarText;
    LinearLayout linearLayout;
    Button toolbarImage;

    //demo edition
    FrameLayout lessonAllRow ,lessonMarkRow;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        linearLayout= (LinearLayout) findViewById(R.id.LinearLayout);
        linearLayout.setBackgroundResource(drawable);


        LessonDemo visionDemo = new LessonDemo();
        visionDemo.show(getSupportFragmentManager(),"VisionDemo");

        init();
    }

    private void init(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        all = (Button) findViewById(R.id.all_btn);
        mark = (Button) findViewById(R.id.mark_btn);
        toolbarText = (TextView) findViewById(R.id.toolbar_tv);

        toolbarImage = (Button) findViewById(R.id.toolbar_iv);
        toolbarImage.setOnClickListener(this);

        //demo edition
        lessonAllRow = (FrameLayout) findViewById(R.id.lesson_all_row_layout);
        lessonMarkRow = (FrameLayout) findViewById(R.id.lesson_mark_row_layout);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        toolbarText.setText("Lesson");

        lessonAllRow.setOnClickListener(this);
        lessonMarkRow.setOnClickListener(this);
        all.setOnClickListener(this);
        mark.setOnClickListener(this);
    }

    private void adapterSwitch(){

        all.setOnClickListener(this);
        mark.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_btn: {
                viewFlipper.setDisplayedChild(0);
                break;
            }
            case R.id.mark_btn: {
                viewFlipper.setDisplayedChild(1);
                break;
            }
            case R.id.lesson_all_row_layout:
                Toast.makeText(Lesson.this, "Lesson Added to Mark", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lesson_mark_row_layout:
                startActivity(new Intent(Lesson.this,LessonPartActivity.class));
                break;
            case R.id.toolbar_iv:
                finish();
                break;
        }
    }
}
