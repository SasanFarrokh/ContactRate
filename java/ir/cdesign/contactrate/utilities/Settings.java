package ir.cdesign.contactrate.utilities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.cdesign.contactrate.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CustomRadioGroup customRadioGroup = new CustomRadioGroup(this);

    }


}
