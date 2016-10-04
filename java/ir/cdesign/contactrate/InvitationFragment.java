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
import ir.cdesign.contactrate.adapters.InvitationAdapter;

/**
 * Created by Ratan on 7/29/2015.
 */
public class InvitationFragment extends Fragment {

    public static InvitationFragment instance;
    RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invitation_tab_layout,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.inv_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        instance = this;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
    }

    private void setRecyclerView(){
        recyclerView.setAdapter(new InvitationAdapter(getActivity()));
    }

}
