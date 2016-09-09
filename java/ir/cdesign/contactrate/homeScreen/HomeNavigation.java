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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.Vision.Visions;
import ir.cdesign.contactrate.dialogs.DialogAbout;
import ir.cdesign.contactrate.dialogs.DialogSettings;
import ir.cdesign.contactrate.imagePicker.DefaultCallback;
import ir.cdesign.contactrate.imagePicker.EasyImage;
import ir.cdesign.contactrate.lessons.Lesson;
import ir.cdesign.contactrate.tutorial.Tutorial;
import ir.cdesign.contactrate.utilities.Settings;

/**
 * Created by amin pc on 31/08/2016.
 */
public class HomeNavigation extends Fragment {

    Intent intent;
    TextView settings, about, task, tutorial, home, vision, statistics, lessons, news;
    ImageView profile;
    HomeScreen homeScreen;

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
        statistics = (TextView) view.findViewById(R.id.stats);
        lessons = (TextView) view.findViewById(R.id.lessons);
        news = (TextView) view.findViewById(R.id.news);
        profile = (ImageView) view.findViewById(R.id.profile_photo);

        settings.setOnClickListener(listener);
        about.setOnClickListener(listener);
        task.setOnClickListener(listener);
        tutorial.setOnClickListener(listener);
        home.setOnClickListener(listener);
        vision.setOnClickListener(listener);
        statistics.setOnClickListener(listener);
        lessons.setOnClickListener(listener);
        news.setOnClickListener(listener);
        profile.setOnClickListener(listener);

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();

            switch (v.getId()) {
                case R.id.settings:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    intent = new Intent(getActivity(), Settings.class);
                    startActivity(intent);
                    break;
                case R.id.about:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    DialogAbout dialogAbout = new DialogAbout();
                    dialogAbout.show(fm, "about");
                    break;
                case R.id.task:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tutorial:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    intent = new Intent(getActivity(), Tutorial.class);
                    startActivity(intent);
                    break;
                case R.id.home:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    break;
                case R.id.vision:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    intent = new Intent(getActivity(), Visions.class);
                    startActivity(intent);
                    break;
                case R.id.stats:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    intent = new Intent(getActivity(), Statistics.class);
                    startActivity(intent);
                    break;
                case R.id.lessons:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    intent = new Intent(getActivity(), Lesson.class);
                    startActivity(intent);
                    break;
                case R.id.news:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    intent = new Intent(getActivity(), NewsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.profile_photo:
                    ((HomeScreen) getActivity()).drawerLayout.closeDrawer(Gravity.LEFT);
                    EasyImage.openGallery(getActivity(),0);
            }
        }
    };

}
