package ir.cdesign.contactrate.homeScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.ImageDisplayAdapter;
import ir.cdesign.contactrate.models.ImageDisplayModel;

public class DisplayImageActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        setRecycler();
    }

    private void setRecycler(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        ImageDisplayAdapter adapter = new ImageDisplayAdapter(this, ImageDisplayModel.getData());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
