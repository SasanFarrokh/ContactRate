package ir.cdesign.contactrate.homeScreen;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.imagePicker.DefaultCallback;
import ir.cdesign.contactrate.imagePicker.EasyImage;
import ir.cdesign.contactrate.utilities.AsyncGetNews;
import ir.cdesign.contactrate.utilities.CalendarTool;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class HomeScreen extends AppCompatActivity {

    ViewPager viewPager;
    ImageView toolbarImage;
    TextView checkAll;
    private List<ImageView> dots;
    boolean bn = true, progressAnim = true;
    LinearLayout homeContent;
    RecyclerView news;

    TextView doneText, pendingText, points, todayDate, visions;
    ProgressBar pending, done, vision;

    GraphPage graphPage = new GraphPage();
    AllRankInv allRankInv = new AllRankInv();
    UserTab userTab = new UserTab();

    Bitmap profileImage;
    int theMan ;

    public static DrawerLayout drawerLayout;
    String profileName, profileNumber;

    SharedPreferences sharedPreferences;
    public static String PREF_NAME = "sharedPreference";
    public static String INT_NAME = "shareInt";

    public static int manInTheMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileName = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE).getString("userName", "Unknown");
        profileNumber = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE).getString("number", "0912 000 00 00");
        if ((new File(getFilesDir(), "profile.jpg")).exists()) {
            profileImage = BitmapFactory.decodeFile((new File(getFilesDir(), "profile.jpg")).getAbsolutePath());
        }

        setContentView(R.layout.activity_home_screen);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);




        toolbarImage = (ImageView) findViewById(R.id.toolbar_iv);
        toolbarImage.setOnClickListener(listener);

        homeContent = (LinearLayout) findViewById(R.id.home_content);
        if (bn) {
            //homeContent.setBackground(getResources().getDrawable(R.drawable.custombg));
            bn = false;
            Log.d("aha", String.valueOf(bn) + "xeyle xo");

        }

        addDots();
        selectDot(0);

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
        (new AsyncGetNews(this, news, 3)).execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                //Handle the image
//                onPhotoReturned(imageFile);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(HomeScreen.this);
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    private void init() {
        //Set Progress Bars
        pending = (ProgressBar) findViewById(R.id.pending);
        done = (ProgressBar) findViewById(R.id.done);
        vision = (ProgressBar) findViewById(R.id.vision);

        checkAll = (TextView) findViewById(R.id.checkAll);
        doneText = (TextView) findViewById(R.id.done_text);
        pendingText = (TextView) findViewById(R.id.pending_text);
        points = (TextView) findViewById(R.id.points);
        todayDate = (TextView) findViewById(R.id.today_date);

        news = (RecyclerView) findViewById(R.id.home_news_rv);

        checkAll.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_iv:
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
                case R.id.checkAll:
                    startActivity(new Intent(HomeScreen.this, NewsActivity.class));
                    break;
            }
        }
    };

    //Temporary Just For Testing
    public void onNews(View view) {
        NewsDialog newsDialog = new NewsDialog();
        newsDialog.show(getSupportFragmentManager(), "are");
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return userTab;
                case 2:
                    return allRankInv;
                case 1:
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

    private void setData(boolean anim) {
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

        int allTask = (doneTask + pendingTask == 0) ? 1 : doneTask + pendingTask;

        Float pendingPercent = ((float) pendingTask) / allTask * 100;
        Float donePercent = ((float) doneTask) / allTask * 100;

        animateProgress(pending, 0, pendingPercent.intValue());
        animateProgress(done, 0, donePercent.intValue());
        animateProgress(vision, 0, 60);
        if (!anim) {
            ObjectAnimator visionAnim = (ObjectAnimator) pending.getTag();
            visionAnim.end();
            visionAnim = (ObjectAnimator) done.getTag();
            visionAnim.end();
            visionAnim = (ObjectAnimator) vision.getTag();
            visionAnim.end();
        }
        progressAnim = false;
    }

    private void animateProgress(ProgressBar progressBar, int start, int end) {
        ObjectAnimator visionAnim = ObjectAnimator.ofInt(progressBar, "progress", start, end); // see this max value coming back here, we animale towards that value
        visionAnim.setDuration(3000);
        //visionAnim.setInterpolator(new DecelerateInterpolator());
        visionAnim.start();
        progressBar.setTag(visionAnim);
    }

    private void setBackground(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.home_content);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        manInTheMiddle = sharedPreferences.getInt(INT_NAME,0);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int  drawable = wallpaperBoy.manSitting(manInTheMiddle,this);

        linearLayout.setBackgroundResource(drawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(progressAnim);

        setBackground();
    }

    public void setProfileImage(Bitmap image) {
        profileImage = image;
        userTab.updateProfileImage();
        File file = new File(getFilesDir(),"profile.jpg");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
