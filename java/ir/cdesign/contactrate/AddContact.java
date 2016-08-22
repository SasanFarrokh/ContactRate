package ir.cdesign.contactrate;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AddContact extends AppCompatActivity {

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

        View.OnClickListener starClickListener = new OnStarClick();

        ViewGroup pointContainer = (ViewGroup) findViewById(R.id.contact_point_container);
        for (int i = 0; i < 3; i++) {
            ViewGroup starCon = (ViewGroup) ((ViewGroup) pointContainer.getChildAt(i)).getChildAt(1);
            for (int ii = 0; ii < 5; ii++) {
                starCon.getChildAt(ii).setTag(ii);
                starCon.getChildAt(ii).setOnClickListener(starClickListener);
            }
        }

    }

    private class OnStarClick implements View.OnClickListener {

        int lesson, time, motive;

        @Override
        public void onClick(View v) {
            int type = Integer.parseInt((String) ((View) v.getParent().getParent()).getTag());
            int rate = 0;
            switch (type) {
                case 1:
                    if (lesson == (int) v.getTag() + 1)
                        lesson = 0;
                    else
                        lesson = (int) v.getTag() + 1;
                    rate = lesson;
                    break;
                case 2:
                    if (time == (int) v.getTag() + 1)
                        time = 0;
                    else
                        time = (int) v.getTag() + 1;
                    rate = time;
                    break;
                case 3:
                    if (motive == (int) v.getTag() + 1)
                        motive = 0;
                    else
                        motive = (int) v.getTag() + 1;
                    rate = motive;
                    break;
            }
            for (int j = 0; j < 5; j++) {
                if (j <= rate - 1) {
                    ((ImageView) ((ViewGroup) v.getParent()).getChildAt(j)).setColorFilter(getResources().getColor(R.color.starTint));
                } else {
                    ((ImageView) ((ViewGroup) v.getParent()).getChildAt(j)).setColorFilter(null);
                }
            }
        }
    }

}
