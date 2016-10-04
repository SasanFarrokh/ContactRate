package ir.cdesign.contactrate;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import ir.cdesign.contactrate.adapters.MedalAdapter;
import ir.cdesign.contactrate.adapters.TaskAdapter;
import ir.cdesign.contactrate.models.MedalModel;
import ir.cdesign.contactrate.models.TaskModel;

public class MedalsActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medals_layout);

        setRecyclerView();
        setToolbar();


    }

    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.medals_recycler);
        MedalAdapter medalAdapter = new MedalAdapter(MedalsActivity.this);
        GridLayoutManager glm = new GridLayoutManager(MedalsActivity.this, 3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(medalAdapter);
    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setBackgroundDrawable(null);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
}
