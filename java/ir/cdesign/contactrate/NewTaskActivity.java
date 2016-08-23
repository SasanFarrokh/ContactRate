package ir.cdesign.contactrate;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import ir.cdesign.contactrate.adapters.TaskAdapter;
import ir.cdesign.contactrate.models.TaskModel;

public class NewTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        setRecyclerView();
        setToolbar();

    }

    private void setRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.tasks_recycler);
        TaskAdapter taskAdapter = new TaskAdapter(NewTaskActivity.this, TaskModel.getData());
        GridLayoutManager glm = new GridLayoutManager(NewTaskActivity.this,3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(null);
        }
    }

}
