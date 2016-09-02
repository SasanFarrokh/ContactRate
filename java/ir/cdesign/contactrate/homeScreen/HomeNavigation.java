package ir.cdesign.contactrate.homeScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.Vision.Visions;
import ir.cdesign.contactrate.dialogs.DialogAbout;
import ir.cdesign.contactrate.dialogs.DialogSettings;
import ir.cdesign.contactrate.tutorial.Tutorial;

/**
 * Created by amin pc on 31/08/2016.
 */
public class HomeNavigation extends Fragment {

    Intent intent;
    TextView settings , about , task , tutorial , home , vision;
    HomeScreen homeScreen ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_navigation, container, false);

        settings = (TextView) view.findViewById(R.id.settings);
        about = (TextView) view.findViewById(R.id.about);
        task = (TextView) view.findViewById(R.id.task);
        tutorial = (TextView) view.findViewById(R.id.tutorial);
        home = (TextView) view.findViewById(R.id.home);
        vision = (TextView) view.findViewById(R.id.vision);

        settings.setOnClickListener(listener);
        about.setOnClickListener(listener);
        task.setOnClickListener(listener);
        tutorial.setOnClickListener(listener);
        home.setOnClickListener(listener);
        vision.setOnClickListener(listener);

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();

            switch (v.getId()) {
                case R.id.settings:
                    DialogSettings dialogSettings = new DialogSettings();
                    dialogSettings.show(fm, "settings");
                    break;
                case R.id.about:
                    DialogAbout dialogAbout = new DialogAbout();
                    dialogAbout.show(fm, "about");
                    break;
                case R.id.task:
                    intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tutorial:
                    intent = new Intent(getActivity(), Tutorial.class);
                    startActivity(intent);
                    break;
                case R.id.home:

                    break;
                case R.id.vision:
                    intent = new Intent(getActivity(), Visions.class);
                    startActivity(intent);
            }
        }
    };
}
