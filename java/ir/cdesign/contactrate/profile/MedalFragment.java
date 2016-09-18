package ir.cdesign.contactrate.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.MedalAdapter;
import ir.cdesign.contactrate.models.MedalModel;

/**
 * Created by amin pc on 18/09/2016.
 */
public class MedalFragment extends Fragment {

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medals_layout,container,false);

        setRecyclerView(view);

        return view;
    }

    private void setRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.medals_recycler);
        MedalAdapter medalAdapter = new MedalAdapter(getActivity(), MedalModel.getData());
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(glm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(medalAdapter);
    }
}
