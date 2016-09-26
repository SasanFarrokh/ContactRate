package ir.cdesign.contactrate.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.Vision.ActivityVisionAdd;
import ir.cdesign.contactrate.adapters.NewsAdapter;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

public class NewsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TextView point;
    RecyclerView news;
    SwipeRefreshLayout swipeRefresh;
    private Button toolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        news = (RecyclerView) findViewById(R.id.news_rv);
        swipeRefresh = (SwipeRefreshLayout) news.getParent();
        point = (TextView) findViewById(R.id.toolbar_tv);
        swipeRefresh.setOnRefreshListener(this);
        setToolbar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        (new AsyncGetNews(this,news)).execute();
    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("News");
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setBackgroundDrawable(null);

        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NewsActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadingFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView text = new TextView(NewsActivity.this);
                text.setText("Failed to connect to network");
                text.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                text.setGravity(Gravity.CENTER);
                text.setTextColor(getResources().getColor(R.color.white));
                ((ViewGroup) news.getRootView()).addView(text);
            }
        });
    }

    @Override
    public void onRefresh() {
        (new AsyncGetNews(this,news)).execute();
    }
}
