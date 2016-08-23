package ir.cdesign.contactrate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class TaskEditToDb extends AppCompatActivity {
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit_to_db);

        setToolbar();

    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.real_toolbar);
        setSupportActionBar(toolbar);
        toolbarText = (TextView) findViewById(R.id.real_text);
        /*
        *
        *       Must Get Text From Recycler Adapter of The Selected Item
        *           \/\/\/\/\/\/\/
        * */
        toolbarText.setText("Add New ...");

    }

}
