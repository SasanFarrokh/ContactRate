package ir.cdesign.contactrate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.cdesign.contactrate.adapters.AllAdapter;
import ir.cdesign.contactrate.models.AllModel;

/**
 * Created by Ratan on 7/29/2015.
 */
public class AllFragment extends Fragment {

    RecyclerView recyclerView;
    AllModel model = new AllModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_tab_layout,container , false);

        setRecyclerView(view);

        return view;
    }

    private void setRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.all_rv);
        AllAdapter adapter = new AllAdapter(getActivity(), model.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
