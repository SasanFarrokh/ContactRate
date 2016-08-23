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

    LinearLayout medals;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);

        medals = (LinearLayout) view.findViewById(R.id.nav_item_medals);
        medals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MedalsActivity.class));
            }
        });
        View about = view.findViewById(R.id.nav_item_about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseCommands.getInstance().addInvite(
                        Long.parseLong(String.valueOf(DatabaseCommands.getInstance().getContactsForInvitation().get(0)[3])),
                        2,"Salam",
                        System.currentTimeMillis() + 20000
                );
            }
        });

        return view;
    }

    public void setUpDrawer(int FragmentId, DrawerLayout drawerLayout) {

    }

}
