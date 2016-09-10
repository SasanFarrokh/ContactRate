package ir.cdesign.contactrate.Vision;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.VisionAdapter;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;

public class Visions extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FloatingActionButton fab;
    Button toolbarImage;
    RecyclerView visions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visions);

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();
        visions.setAdapter(new VisionAdapter());
    }

    private void init(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbarImage = (Button) findViewById(R.id.toolbar_iv);
        visions = (RecyclerView) findViewById(R.id.vision_rv);

        fab.setOnClickListener(listener);
        toolbarImage.setOnClickListener(listener);
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        // Note: monthOfYear is 0-indexed
        String date = "You picked the following date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//        dateTextView.setText(date);
    }
}
