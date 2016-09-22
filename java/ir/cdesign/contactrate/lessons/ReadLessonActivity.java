package ir.cdesign.contactrate.lessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.BottomDrawer;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class ReadLessonActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearLayout;
    Button toolbarImage;
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_lesson);

        //set BackGround
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        linearLayout= (LinearLayout) findViewById(R.id.LinearLayout);
        linearLayout.setBackgroundResource(drawable);

        //handle toolbar
        toolbarImage = (Button) findViewById(R.id.toolbar_iv);
        toolbarImage.setOnClickListener(this);
        toolbarText = (TextView) findViewById(R.id.toolbar_tv);
        toolbarText.setText("Read Lesson");


        // read utilities setup
        BottomDrawer bottomDrawer = new BottomDrawer(this);

    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.toolbar_iv:
                    finish();
                    break;
        }
    }
}
