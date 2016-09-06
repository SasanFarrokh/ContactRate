package ir.cdesign.contactrate.utilities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.dialogs.DialogImageDisplay;
import ir.cdesign.contactrate.dialogs.DialogSettings;

public class Settings extends AppCompatActivity {

    LinearLayout backgroundOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CustomRadioGroup customRadioGroup = new CustomRadioGroup(this);

        backgroundOpen = (LinearLayout) findViewById(R.id.background_open);
        backgroundOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogImageDisplay dialogImageDisplay = new DialogImageDisplay();
                dialogImageDisplay.show(getSupportFragmentManager(),"ayeah");
            }
        });

    }


}
