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
import android.util.Log;
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

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.utilities.CalendarTool;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class HomeScreen extends AppCompatActivity {

    ViewPager viewPager;
    ImageView toolbarImage;
    TextView checkAll;
    private List<ImageView> dots;
    boolean bn = true;
    LinearLayout homeContent;

    TextView doneText;
    TextView pendingText;
    TextView points;
    TextView todayDate;
    TextView visions;
    ProgressBar pending,done,vision;



    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        toolbarImage = (ImageView) findViewById(R.id.toolbar_iv);
        toolbarImage.setOnClickListener(listener);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
//        wallpaperBoy.setWallpaper(drawerLayout,R.drawable.tutpictwo);
        homeContent = (LinearLayout) findViewById(R.id.home_content);
        if (bn){
            //homeContent.setBackground(getResources().getDrawable(R.drawable.custombg));
            bn = false;
            Log.d("aha",String.valueOf(bn) + "xeyle xo");

        }


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
        pending = (ProgressBar) findViewById(R.id.pending);
        done = (ProgressBar) findViewById(R.id.done);
        vision = (ProgressBar) findViewById(R.id.vision);

        checkAll = (TextView) findViewById(R.id.checkAll);
        doneText = (TextView) findViewById(R.id.done_text);
        pendingText = (TextView) findViewById(R.id.pending_text);
        points = (TextView) findViewById(R.id.points);
        todayDate = (TextView) findViewById(R.id.today_date);

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

    //Temporary Just For Testing
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
            int drawableId = R.drawable.home_view_pager_indicator;
            dot.setImageResource(drawableId);
            if (i == idx) {
                dot.setAlpha(0.5f);
                dot.animate().setDuration(250).scaleY(1.25f).scaleX(1.25f).alpha(1f).start();
            } else {
                dot.animate().setDuration(1).scaleY(1.0f).scaleX(1.0f).alpha(1f).start();
            }
        }
    }

    private void setData() {
        DatabaseCommands db = DatabaseCommands.getInstance();
        int p = db.getUserPoint();
        int doneTask = db.getDoneTask();
        int pendingTask = db.getPendingTask();
        CalendarTool calendar = new CalendarTool();
        String date = calendar.getIranianDate();

        points.setText(String.valueOf(p));
        doneText.setText(String.valueOf(doneTask));
        pendingText.setText(String.valueOf(pendingTask));
        todayDate.setText("Today : \n" + String.valueOf(date));

        int allTask = (doneTask+pendingTask == 0)?0:doneTask+pendingTask;

        animateProgress(pending,0,(pendingTask/allTask)*100);
        animateProgress(done,0,(doneTask/allTask)*100);
        animateProgress(vision,0,3/5);
    }

    private void animateProgress(ProgressBar progressBar, int start, int end) {
        ObjectAnimator visionAnim = ObjectAnimator.ofInt (progressBar, "progress", start, end); // see this max value coming back here, we animale towards that value
        visionAnim.setDuration (5000); //in milliseconds
        visionAnim.setInterpolator (new DecelerateInterpolator());
        visionAnim.start ();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}
