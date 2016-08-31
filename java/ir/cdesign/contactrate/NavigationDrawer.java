package ir.cdesign.contactrate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ir.cdesign.contactrate.dialogs.DialogAbout;
import ir.cdesign.contactrate.tutorial.Tutorial;
import ir.cdesign.contactrate.utilities.MedalDialog;

/**
 * Created by amin pc on 23/08/2016.
 */
public class NavigationDrawer extends Fragment {

    LinearLayout medals, tutorial, about;
    String name;
    int point = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        tutorial = (LinearLayout) view.findViewById(R.id.nav_item_logout);
        about = (LinearLayout) view.findViewById(R.id.nav_item_about);

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogAbout dialogAbout = new DialogAbout();
                dialogAbout.show(fm, "Sample Fragment");
            }
        });
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Tutorial.class));
            }
        });

        medals = (LinearLayout) view.findViewById(R.id.nav_item_medals);
        medals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MedalsActivity.class));
            }
        });

        name = getActivity().getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE).getString("userName", "Unknown");
        name = titleCase(name);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        TextView userName = (TextView) getView().findViewById(R.id.nav_user_name);
        userName.setText(name);
        TextView point = (TextView) getView().findViewById(R.id.nav_user_point);
//        point.setText(String.valueOf(DatabaseCommands.getInstance().getUserPoint()));
    }

    public static String titleCase(String str) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : str.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
