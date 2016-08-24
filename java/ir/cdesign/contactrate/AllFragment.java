package ir.cdesign.contactrate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.TextView;

import ir.cdesign.contactrate.adapters.AllAdapter;
import ir.cdesign.contactrate.models.AllModel;

/**
 * Created by Ratan on 7/29/2015.
 */
public class AllFragment extends Fragment {

    RecyclerView recyclerView;
    TextView search;
    AllModel model = new AllModel();
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;
    Boolean firstTime = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_tab_layout,container , false);


        showSnackBar(view);

        search = (EditText) view.findViewById(R.id.search);
        search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                }
            }
        });
        setRecyclerView(view);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AllAdapter.searchPattern = search.getText().toString();
                AllAdapter adapter = new AllAdapter(getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    private void showSnackBar(View view){
        String user = getActivity().getSharedPreferences(MainActivity.PREF,Context.MODE_PRIVATE).getString("userName","");
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.mother);
        if (firstTime && user != null){
            Snackbar.make(coordinatorLayout,"Welcome " + NavigationDrawer.titleCase(user),Snackbar.LENGTH_LONG).show();
            firstTime = false;
        }
    }

    private void setRecyclerView(View view){
        recyclerView = (RecyclerView) view.findViewById(R.id.all_rv);
        fab = (FloatingActionButton) view.findViewById(R.id.all_fab);
        AllAdapter adapter = new AllAdapter(getActivity());
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddContact.class));
            }
        });

    }

}
