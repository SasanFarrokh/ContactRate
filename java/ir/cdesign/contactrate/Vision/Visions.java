package ir.cdesign.contactrate.Vision;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.date.DatePickerDialog;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;

public class Visions extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FloatingActionButton fab;
    Button toolbarImage;

    FragmentManager fm = getSupportFragmentManager();
    FragmentaddVision fragmentaddVision = new FragmentaddVision();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visions);

        init();


        PersianCalendar now = new PersianCalendar();
        DatePickerDialog dpd = DatePickerDialog.newInstance(Visions.this,
                now.getPersianYear(),
                now.getPersianMonth(),
                now.getPersianDay()
        );
//                    dpd.setThemeDark(modeDarkDate.isChecked());
        dpd.show(getFragmentManager(), "");

    }

    private void init(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbarImage = (Button) findViewById(R.id.toolbar_iv);

        fab.setOnClickListener(listener);
        toolbarImage.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fab:
                    fm.beginTransaction();
                    fragmentaddVision.show(fm,"are");
                    fab.hide();
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
