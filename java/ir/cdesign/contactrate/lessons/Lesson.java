package ir.cdesign.contactrate.lessons;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class Lesson extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    TextView toolbarText;
    FrameLayout frameLayout;
    FloatingActionButton fab ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        frameLayout = (FrameLayout) findViewById(R.id.FrameParent);
        frameLayout.setBackgroundResource(drawable);

        init();
    }

    private void init(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbarText = (TextView) findViewById(R.id.toolbar_tv);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setToolbar();
        fab.setOnClickListener(this);
    }

    private void setRecyclerView(){

    }

    private void setToolbar(){
        Toolbar toolbar = new Toolbar(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Lessons");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setBackgroundDrawable(null);

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_iv:
                finish();
                break;
            case R.id.fab :
                startActivity(new Intent(Lesson.this , LessonAllActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Lesson.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
