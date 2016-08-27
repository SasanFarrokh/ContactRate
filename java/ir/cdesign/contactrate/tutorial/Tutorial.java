package ir.cdesign.contactrate.tutorial;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

public class Tutorial extends FragmentActivity {

    private ViewPager viewPager;
    private List<ImageView> dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        addDots();

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        selectDot(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        FragmentTutorialOne fragmentTutorialOne = new FragmentTutorialOne();
        FragmentTutorialTwo fragmentTutorialTwo = new FragmentTutorialTwo();
        FragmentTutorialThree fragmentTutorialthree = new FragmentTutorialThree();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return fragmentTutorialOne;
                case 1: return fragmentTutorialTwo;
                case 2 : return fragmentTutorialthree;
                default: return fragmentTutorialOne;
            }
        }

        @Override
        public int getCount() {
            return dots.size();
        }
    }

    public void addDots() {
        dots = new ArrayList<>();
        LinearLayout dot = (LinearLayout) findViewById(R.id.tutorial_indicators);
        for(int i = 0; i < dot.getChildCount(); i++) {


            dots.add((ImageView) dot.getChildAt(i));
        }

    }

    public void selectDot(int idx) {
        for(int i = 0; i < dots.size(); i++) {
            ImageView dot = dots.get(i);
            int drawableId = (i==idx)?(R.drawable.indicator_dots_selected):(R.drawable.indicator_dots);
            dot.setImageResource(drawableId);
            if (i==idx) {
                dot.setAlpha(0.5f);
                dot.animate().setDuration(250).scaleY(1.25f).scaleX(1.25f).alpha(1f).start();
            } else {
                dot.animate().setDuration(1).scaleY(1.0f).scaleX(1.0f).alpha(1f).start();
            }
        }
    }

}
