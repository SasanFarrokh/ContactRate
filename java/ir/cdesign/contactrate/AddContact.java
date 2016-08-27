package ir.cdesign.contactrate;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ir.cdesign.contactrate.adapters.RankAdapter;

public class AddContact extends AppCompatActivity {

    String name,phone;
    int lesson,time,motive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

//        init
        setToolbar();

    }

    private void setToolbar(){
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
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                AddContact.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();

        View addBtn = findViewById(R.id.add_contact_btn);
        addBtn.setOnClickListener(new OnAddContact());

        View.OnTouchListener starTouch = new OnStarTouch();

        ViewGroup pointContainer = (ViewGroup) findViewById(R.id.contact_point_container);
        for (int i = 0; i < 3; i++) {
            ViewGroup starCon = (ViewGroup) ((ViewGroup) pointContainer.getChildAt(i)).getChildAt(1);
            starCon.setOnTouchListener(starTouch);
        }

    }

    private  class OnAddContact implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            addContact();
        }
    }
    public void addContact() {

        name = ((EditText) findViewById(R.id.contact_name)).getText().toString();
        phone = ((EditText) findViewById(R.id.contact_number)).getText().toString();

        if (DatabaseCommands.getInstance().insertContact(name,phone,lesson,time,motive,"")) {
            Toast.makeText(AddContact.this, "Successfully added", Toast.LENGTH_SHORT).show();
            RankFragment.instance.recyclerView.getAdapter().notifyDataSetChanged();
            RankFragment.instance.recyclerView.setAdapter(new RankAdapter(this));
            finish();
        } else {
            Toast.makeText(AddContact.this, "To add more contact, you should purchase the full version", Toast.LENGTH_SHORT).show();
        }
    }

    private class OnStarTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    ViewGroup con = (ViewGroup) v;
                    int type = Integer.parseInt((String) ((View) v.getParent()).getTag());
                    float x = event.getX();
                    float col = v.getWidth()/5;

                    int rate = Math.max(0,Math.min(5,(int) Math.ceil(x/col)));

                    for (int i = 0 ; i < 5; i++) {
                        ImageView star = (ImageView) con.getChildAt(i);
                        if (i <= rate - 1) {

                            star.setColorFilter(getResources().getColor(R.color.starTint));
                        } else {
                            star.setColorFilter(null);
                        }
                    }

                    switch (type) {
                        case 1:
                            lesson = rate;
                            break;
                        case 2:
                            time = rate;
                            break;
                        case 3:
                            motive = rate;
                            break;
                    }
                    break;
            }

            return true;
        }
    }

}
