package ir.cdesign.contactrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by amin pc on 23/08/2016.
 */
public class NavigationDrawer extends Fragment {

    LinearLayout testing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);

        testing = (LinearLayout) view.findViewById(R.id.testing);
        testing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),AddContact.class));
            }
        });

        return view;
    }

    public void setUpDrawer(int FragmentId, DrawerLayout drawerLayout) {

    }

}
