package ir.cdesign.contactrate.Vision;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.VisionAdapter;
import ir.cdesign.contactrate.caligraphy.CalligraphyContextWrapper;
import ir.cdesign.contactrate.demoedition.VisionDemo;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class Visions extends AppCompatActivity implements VisionAdapter.AdapterUpdate{

    FloatingActionButton fab;
    ImageView toolbarImage;
    RecyclerView visions;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visions);

        init();

        //set BackGround
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        relativeLayout = (RelativeLayout) findViewById(R.id.FrameParent);
        relativeLayout.setBackgroundResource(drawable);

        if (!getSharedPreferences(MainActivity.PREF,MODE_PRIVATE).getBoolean("vision_demo",false)) {
            VisionDemo demo = new VisionDemo();
            demo.show(getSupportFragmentManager(), "demo");
            getSharedPreferences(MainActivity.PREF,MODE_PRIVATE).edit().putBoolean("vision_demo",true).apply();
        }
    }
    // font library
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        visions.setAdapter(new VisionAdapter(this));
        visions.setHasFixedSize(true);
        visions.setLayoutManager(new LinearLayoutManager(this));
    }

    private void init(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        visions = (RecyclerView) findViewById(R.id.vision_rv);

        setToolbar();

        fab.setOnClickListener(listener);
    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Visions");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setBackgroundDrawable(null);

        }

    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fab:
                    startActivity(new Intent(Visions.this,ActivityVisionAdd.class));
                    break;
                case R.id.toolbar_iv:
                    finish();
                    break;
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Visions.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateRecycler(int pos,int mode) {
        switch (mode) {
            case 0:
                visions.setAdapter(new VisionAdapter(this));
                break;
            case 1:
                visions.getAdapter().notifyDataSetChanged();
                break;
        }
    }
}
