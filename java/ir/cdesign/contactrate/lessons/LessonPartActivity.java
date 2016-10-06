package ir.cdesign.contactrate.lessons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class LessonPartActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearLayout;
    Button toolbarImage;
    TextView toolbarText;

    //demo edition
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_part_activity);

        init();

        //set BackGround
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle, this);
        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
        linearLayout.setBackgroundResource(drawable);

    }

    private void init() {
        //handle toolbarlbar_tv);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
