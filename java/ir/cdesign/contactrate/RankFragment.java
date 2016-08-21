package ir.cdesign.contactrate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ir.cdesign.contactrate.adapters.AllAdapter;
import ir.cdesign.contactrate.adapters.RankAdapter;

/**
 * Created by Ratan on 7/29/2015.
 */
public class RankFragment extends Fragment {

    public static RankFragment instance;

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rank_tab_layout, null);
        instance = this;
        setRecycler(view);

        return view;
    }

    private void setRecycler(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.rank_rv);
        RankAdapter adapter = new RankAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setFocusable(true);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.requestFocusFromTouch();
                return false;
            }
        });
    }
}
