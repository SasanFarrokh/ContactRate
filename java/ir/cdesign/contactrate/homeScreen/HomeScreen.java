package ir.cdesign.contactrate.homeScreen;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.NavigationDrawer;
import ir.cdesign.contactrate.R;

public class HomeScreen extends AppCompatActivity {

    LinearLayout news;
    ViewPager viewPager;
    FrameLayout newsContainer;
    ImageView toolbarImage;
    float firstY, lastY;
    boolean newsToggle = false;
    private List<ImageView> dots;

    DrawerLayout drawerLayout;
    public static HomeScreen homeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbarImage = (ImageView) findViewById(R.id.toolbar_iv);
        toolbarImage.setOnClickListener(listener);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.news_container, new HomeNewsContent());
        ft.commit();

        setNews();
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
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.toolbar_iv :
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
            }
        }
    };

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        GraphPage graphPage = new GraphPage();
        AllRankInv allRankInv = new AllRankInv();
        OnlineServices onlineServices = new OnlineServices();

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return graphPage;
                case 1:
                    return allRankInv;
                case 2:
                    return onlineServices;
                default:
                    return allRankInv;
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

    private void setNews() {
        final TextView toolbarText = (TextView) findViewById(R.id.toolbar_tv);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
//        news = (LinearLayout) findViewById(R.id.news);

        final int height = size.y;
        int khoob = (int) (height);

        newsContainer = (FrameLayout) findViewById(R.id.news_container);
        newsContainer.setTranslationY(khoob - 150);

        final ImageView arrow = (ImageView) findViewById(R.id.news_indicator);

        newsContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        firstY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (firstY > lastY) {
                            if (!newsToggle) {
                                newsContainer.animate().y(160);
                                arrow.animate().rotationBy(180).start();
                                toolbarText.setText("News");
                                newsToggle = true;
                            }
                        }
                }
                return true;
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsContainer.getY() == 160) {
                    // open
                    arrow.animate().rotationBy(-180).start();
                    newsContainer.animate().y(1180).start();
                    toolbarText.setText("Home");
                    newsToggle = false;
                } else if (newsContainer.getY() == 1180) {
                    //close
                    toolbarText.setText("News");
                    arrow.animate().rotationBy(180).start();
                    newsContainer.animate().y(160).start();
                } else {
                    toolbarText.setText("News");
                    arrow.animate().rotationBy(180).start();
                    newsContainer.animate().y(160).start();
                }
            }
        });
    }

}
