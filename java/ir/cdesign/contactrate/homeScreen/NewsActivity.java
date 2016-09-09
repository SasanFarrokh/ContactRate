package ir.cdesign.contactrate.homeScreen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

public class NewsActivity extends AppCompatActivity {

    RecyclerView news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        news = (RecyclerView) findViewById(R.id.news_rv);
    }

    @Override
    protected void onStart() {
        super.onStart();
        (new AsyncGetNews(this,news)).execute();
    }
}
