package ir.cdesign.contactrate.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
        toolbarImage = (Button) findViewById(R.id.toolbar_iv);
        if (toolbarImage != null) {
            toolbarImage.setOnClickListener(listener);
        }
        swipeRefresh.setOnRefreshListener(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.toolbar_iv:
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        (new AsyncGetNews(this,news)).execute();
    }
}
