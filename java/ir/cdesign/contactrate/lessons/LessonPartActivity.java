package ir.cdesign.contactrate.lessons;

import android.animation.Animator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class LessonPartActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    TextView toolbarText, lessonPartTitle, lessonPartBody;
    ViewGroup container;
    LessonPartAdapter adapter;

    LessonModel lesson;

    ViewFlipper flipper;
    boolean readingLesson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_part_activity);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle, this);

        long id = getIntent().getLongExtra(LessonMarkAdapter.LESSON_ID, 0);
        if (id == 0) finish();
        lesson = DatabaseCommands.getInstance(this).getLessons(id).get(0);

        init();

        container = (ViewGroup) recyclerView.getRootView();
        container.setBackgroundResource(drawable);

        setRecyclerView();

        flipper.setDisplayedChild(0);

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        toolbarText = (TextView) findViewById(R.id.toolbar_tv);
        flipper = (ViewFlipper) findViewById(R.id.lesson_viewflipper);
        lessonPartTitle = (TextView) findViewById(R.id.lesson_part_title);
        lessonPartBody = (TextView) findViewById(R.id.lesson_part_text);

        setToolbar();
    }

    private void setRecyclerView() {
        adapter = new LessonPartAdapter(this, lesson.parts);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(lesson.title);
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
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!readingLesson)
            LessonPartActivity.this.finish();
        else {
            flipper.getChildAt(1).animate().setDuration(200).alpha(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    flipper.getChildAt(0).setAlpha(0);
                    flipper.setDisplayedChild(0);
                    flipper.getChildAt(0).animate().setDuration(200).setListener(null).alpha(1).start();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
            readingLesson = false;
        }
    }
}
