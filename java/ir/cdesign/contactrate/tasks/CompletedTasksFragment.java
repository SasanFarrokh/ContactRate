package ir.cdesign.contactrate.tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.RankAdapter;
import ir.cdesign.contactrate.adapters.TasksAdapter;

/**
 * Created by Sasan on 2016-09-27.
 */
public class CompletedTasksFragment extends Fragment implements TasksActivity.UpdateRecycler {


    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tasks_recycler, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.tasks_rv);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecycler(getContext());
    }

    @Override
    public void setRecycler(Context context){
        TasksAdapter adapter = new TasksAdapter(context,TasksAdapter.CM_TASKS);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
