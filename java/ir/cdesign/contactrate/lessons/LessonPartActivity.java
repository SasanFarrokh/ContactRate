package ir.cdesign.contactrate.lessons;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class LessonPartActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    TextView toolbarText;
    RelativeLayout container;
    FloatingActionButton fab;
    LessonPartAdapter adapter;

    LessonModel lesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_part_activity);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle, this);
        container = (RelativeLayout) findViewById(R.id.FrameParent);
        container.setBackgroundResource(drawable);

        long id = getIntent().getLongExtra("id",0);
        if (id == 0) finish();
        lesson = DatabaseCommands.getInstance(this).getLessons(id).get(0);

        init();
        setRecyclerView();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbarText = (TextView) findViewById(R.id.toolbar_tv);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        setToolbar();
        fab.setOnClickListener(this);
    }

    private void setRecyclerView() {

        adapter = new LessonPartAdapter(this , lesson.parts);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

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
        switch (v.getId()) {
            case R.id.toolbar_iv:
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                LessonPartActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
