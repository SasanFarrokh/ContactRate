package ir.cdesign.contactrate.utilities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);

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
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
