package ir.cdesign.contactrate;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ir.cdesign.contactrate.adapters.TaskAdapter;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.models.TaskModel;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class NewTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView toolbarText;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;

    long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        setToolbar();

        fab = (FloatingActionButton) findViewById(R.id.new_task_ignore);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.Coordinato);
        coordinatorLayout.setBackgroundResource(drawable);

        contactId = getIntent().getLongExtra("contact_id",0);
        if (contactId == 0) finish();

        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.tasks_recycler);
        TaskAdapter taskAdapter = new TaskAdapter(NewTaskActivity.this, TaskModel.getData(), contactId);
        GridLayoutManager glm = new GridLayoutManager(NewTaskActivity.this, 3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);

        setSupportActionBar(toolbar);

        toolbarText = (TextView) findViewById(R.id.real_text);
        toolbarText.setText(R.string.addnew);

    }

}
