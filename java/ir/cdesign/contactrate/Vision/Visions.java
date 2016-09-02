package ir.cdesign.contactrate.Vision;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ir.cdesign.contactrate.R;

public class Visions extends AppCompatActivity {

    FloatingActionButton fab;

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;
    FragmentaddVision fragmentaddVision = new FragmentaddVision();

    public static Visions visions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visions);

        init();

    }

    private void init(){
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.fab:
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.add(R.id.container,fragmentaddVision);
                    fragmentTransaction.commit();
                    fab.hide();
                    break;
            }
        }
    };

}
