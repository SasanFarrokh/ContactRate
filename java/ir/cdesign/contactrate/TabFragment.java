package ir.cdesign.contactrate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ir.cdesign.contactrate.tasks.TasksActivity;

//import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by Ratan on 7/27/2015.
 */
public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;
    public static Toolbar toolbar;

    TextView point;

    public static TabFragment instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
            View x =  inflater.inflate(R.layout.tab_layout,null);

            tabLayout = (TabLayout) x.findViewById(R.id.tabs);
            viewPager = (ViewPager) x.findViewById(R.id.viewpager);
            toolbar = (Toolbar) x.findViewById(R.id.toolbar);

        Button hamburgerBtn = (Button) x.findViewById(R.id.toolbar_iv);
        hamburgerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        point = (TextView) x.findViewById(R.id.toolbar_tv);
        setPoint();
        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                   }
        });

        viewPager.setCurrentItem(getActivity().getIntent().getIntExtra(TasksActivity.PAGE_SCROLL,0));

        instance = this;

        return x;



    }

    public void setPoint() {

        int p = DatabaseCommands.getInstance().getUserPoint();

        point.setText(String.valueOf(p));
    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
          switch (position){
              case 0 : return new AllFragment();
              case 1 : return new RankFragment();
              case 2 : return new InvitationFragment();
          }
        return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "All";
                case 1 :
                    return "Rank";
                case 2 :
                    return "Invitation";
            }
                return null;
        }
    }

}
