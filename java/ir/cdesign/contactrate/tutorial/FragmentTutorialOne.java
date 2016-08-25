package ir.cdesign.contactrate.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 25/08/2016.
 */
public class FragmentTutorialOne extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutorial_view_one,container,false);

        return view;
    }
}
