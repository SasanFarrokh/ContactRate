package ir.cdesign.contactrate.homeScreen;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

public class HomeScreen extends AppCompatActivity {

    ViewPager viewPager;
    ImageView toolbarImage;
    TextView checkAll;
    private List<ImageView> dots;

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbarImage = (ImageView) findViewById(R.id.toolbar_iv);
        toolbarImage.setOnClickListener(listener);


        addDots();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
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

        init();

    }

    private void init(){
        //Set Progress Bars
        ProgressBar pending = (ProgressBar) findViewById(R.id.pending);
        ObjectAnimator pendingAnim = ObjectAnimator.ofInt (pending, "progress", 0, 500); // see this max value coming back here, we animale towards that value
        pendingAnim.setDuration (5000); //in milliseconds
        pendingAnim.setInterpolator (new DecelerateInterpolator());
        pendingAnim.start ();

        ProgressBar done = (ProgressBar) findViewById(R.id.done);
        ObjectAnimator doneAnim = ObjectAnimator.ofInt (done, "progress", 0, 500); // see this max value coming back here, we animale towards that value
        doneAnim.setDuration (5000); //in milliseconds
        doneAnim.setInterpolator (new DecelerateInterpolator());
        doneAnim.start ();

        ProgressBar vision = (ProgressBar) findViewById(R.id.vision);
        ObjectAnimator visionAnim = ObjectAnimator.ofInt (vision, "progress", 0, 500); // see this max value coming back here, we animale towards that value
        visionAnim.setDuration (5000); //in milliseconds
        visionAnim.setInterpolator (new DecelerateInterpolator());
        visionAnim.start ();

        checkAll = (TextView) findViewById(R.id.checkAll);

        checkAll.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.toolbar_iv :
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
                case R.id.checkAll :
                    startActivity(new Intent(HomeScreen.this, NewsActivity.class));
                     break;
            }
        }
    };

    public void onNews(View view) {
        NewsDialog newsDialog = new NewsDialog();
        newsDialog.show(getSupportFragmentManager(),"are");
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        GraphPage graphPage = new GraphPage();
        AllRankInv allRankInv = new AllRankInv();
        userTab userTab = new userTab();

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return userTab;
                case 1:
                    return allRankInv;
                case 2:
                    return graphPage;
                default:
                    return userTab;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    public void addDots() {
        dots = new ArrayList<>();
        LinearLayout dot = (LinearLayout) findViewById(R.id.tutorial_indicators);
        for (int i = 0; i < dot.getChildCount(); i++) {


            dots.add((ImageView) dot.getChildAt(i));
        }

    }

    public void selectDot(int idx) {
        for (int i = 0; i < dots.size(); i++) {
            ImageView dot = dots.get(i);
            int drawableId = (i == idx) ? (R.drawable.indicator_dots_selected) : (R.drawable.indicator_dots);
            dot.setImageResource(drawableId);
            if (i == idx) {
                dot.setAlpha(0.5f);
                dot.animate().setDuration(250).scaleY(1.25f).scaleX(1.25f).alpha(1f).start();
            } else {
                dot.animate().setDuration(1).scaleY(1.0f).scaleX(1.0f).alpha(1f).start();
            }
        }
    }

}
