package ir.cdesign.contactrate.lessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class LessonMarkActivity extends AppCompatActivity {

    Toolbar toolbar ;
    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_mark_activity);

        init();

        // set wallpaper
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setBackgroundResource(drawable);

    }

    private void init(){
        toolbar = new Toolbar(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        setSupportActionBar(toolbar);
    }

    private void setRecyclerView(){

    }

}
