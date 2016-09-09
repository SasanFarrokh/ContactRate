package ir.cdesign.contactrate.homeScreen;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.NewsAdapter;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

public class NewsActivity extends AppCompatActivity {

    TextView point;
    RecyclerView news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        news = (RecyclerView) findViewById(R.id.news_rv);
        point = (TextView) findViewById(R.id.toolbar_tv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setPoint();
        (new AsyncGetNews(this,news)).execute();
    }
    public void setPoint() {

        ((View) point.getParent()).setVisibility(View.GONE);

        //int p = DatabaseCommands.getInstance().getUserPoint();
        //point.setText(String.valueOf(p));
    }


}
