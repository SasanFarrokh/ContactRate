package ir.cdesign.contactrate.homeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

/**
 * Created by amin pc on 31/08/2016.
 */
public class AllRankInv extends Fragment {

    TextView checkAll;
    RecyclerView news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_allrankinv,container,false);

        news = (RecyclerView) view.findViewById(R.id.home_news_rv);
        checkAll = (TextView) view.findViewById(R.id.news_checkall);
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),NewsActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        (new AsyncGetNews(getContext(),news,4)).execute();

    }
}
