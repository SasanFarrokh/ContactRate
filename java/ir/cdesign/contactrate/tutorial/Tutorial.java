package ir.cdesign.contactrate.tutorial;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.cdesign.contactrate.R;

public class Tutorial extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        FragmentTutorialOne fragmentTutorialOne = new FragmentTutorialOne();
        FragmentTutorialTwo fragmentTutorialTwo = new FragmentTutorialTwo();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return fragmentTutorialOne;
                case 1: return fragmentTutorialTwo;
                default: return fragmentTutorialOne;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
