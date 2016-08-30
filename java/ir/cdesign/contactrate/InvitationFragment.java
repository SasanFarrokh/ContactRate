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
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.adapters.AllAdapter;
import ir.cdesign.contactrate.adapters.ExpandableListAdapter;
import ir.cdesign.contactrate.adapters.InvitationAdapter;

/**
 * Created by Ratan on 7/29/2015.
 */
public class InvitationFragment extends Fragment {

    public static InvitationFragment instance;
    RecyclerView recyclerView;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.invitation_tab_layout,null);
        instance = this;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setExpandableList(view);
//        setRecyclerView(view);
    }

    private void setRecyclerView(View view){
//        recyclerView = (RecyclerView) view.findViewById(R.id.inv_rv);
//        InvitationAdapter adapter = new InvitationAdapter(getActivity());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setExpandableList(View view){

        makeData();

        expandableListView = (ExpandableListView) view.findViewById(R.id.listview);
        expandableListAdapter = new ExpandableListAdapter(getActivity(),listDataHeader,listDataChild);
        expandableListView.setAdapter(expandableListAdapter);




    }

    private void makeData(){listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Level One");
        listDataHeader.add("Level Two");
        listDataHeader.add("Level Three");
        listDataHeader.add("Level Four");

        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        listDataChild.put(listDataHeader.get(0),top250);}
}
