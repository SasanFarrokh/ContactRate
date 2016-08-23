package ir.cdesign.contactrate;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ir.cdesign.contactrate.adapters.TaskAdapter;
import ir.cdesign.contactrate.models.TaskModel;

public class NewTaskActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView toolbarText;
    FloatingActionButton fab;

    long contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        setRecyclerView();
        setToolbar();

        fab = (FloatingActionButton) findViewById(R.id.new_task_ignore);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        contactId = getIntent().getIntExtra("contact_id",0);
        if (contactId == 0) finish();
    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.tasks_recycler);
        TaskAdapter taskAdapter = new TaskAdapter(NewTaskActivity.this, TaskModel.getData());
        GridLayoutManager glm = new GridLayoutManager(NewTaskActivity.this, 3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);

        setSupportActionBar(toolbar);

        toolbarText = (TextView) findViewById(R.id.real_text);
        toolbarText.setText("Add New ...");

    }

}
