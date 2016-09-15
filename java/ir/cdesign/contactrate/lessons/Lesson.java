package ir.cdesign.contactrate.lessons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;

public class Lesson extends AppCompatActivity implements View.OnClickListener{

    Button all ,mark ;
    RecyclerView recyclerView;
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        init();
    }

    private void init(){

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        all = (Button) findViewById(R.id.all_btn);
        mark = (Button) findViewById(R.id.mark_btn);
        toolbarText = (TextView) findViewById(R.id.toolbar_tv);

        toolbarText.setText("Lesson");
    }

    private void adapterSwitch(){

        all.setOnClickListener(this);
        mark.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_btn: {

                break;
            }
            case R.id.mark_btn: {

                break;
            }
        }
    }
}
