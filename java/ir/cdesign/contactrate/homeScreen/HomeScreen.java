package ir.cdesign.contactrate.homeScreen;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.ContactTasksAdapter;
import ir.cdesign.contactrate.imagePicker.DefaultCallback;
import ir.cdesign.contactrate.imagePicker.EasyImage;
import ir.cdesign.contactrate.utilities.AsyncGetNews;
import ir.cdesign.contactrate.utilities.CalendarTool;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class HomeScreen extends AppCompatActivity {

    ViewPager viewPager;
    ImageView toolbarImage;
    private List<ImageView> dots;
    LinearLayout homeContent;
    ListView tasks;

    Handler handler;
    boolean pageChanged;

    GraphPage graphPage = new GraphPage();
    AllRankInv allRankInv = new AllRankInv();
    UserTab userTab = new UserTab();

    public static Bitmap profileImage;
    int theMan;

    public static DrawerLayout drawerLayout;
    String profileName, profileNumber;

    SharedPreferences sharedPreferences;
    public static String BACKGROUND_KEY = "shareInt";

    public static int manInTheMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileName = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE).getString("userName", "Unknown");
        profileNumber = getSharedPreferences(MainActivity.PREF, MODE_PRIVATE).getString("phoneNumber", "09XXXXXXXXX");
        if ((new File(getFilesDir(), "profile.jpg")).exists()) {
            profileImage = BitmapFactory.decodeFile((new File(getFilesDir(), "profile.jpg")).getAbsolutePath());
        }

        setContentView(R.layout.activity_home_screen);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);


        toolbarImage = (ImageView) findViewById(R.id.toolbar_iv);
        toolbarImage.setOnClickListener(listener);

        homeContent = (LinearLayout) findViewById(R.id.home_content);

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
                if (state == ViewPager.SCROLL_STATE_DRAGGING) pageChanged = true;
                else if (state==ViewPager.SCROLL_STATE_IDLE) {
                    pageChanged = false;
                    pageAutoChange();
                }
            }
        });

        init();
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

        handler = new Handler();
        tasks = (ListView) findViewById(R.id.home_tasks_lv);
        TextView textView = new TextView(this);
        textView.setText("No Tasks Today");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        tasks.setEmptyView(textView);
        pageAutoChange();
    }

    private void pageAutoChange() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!pageChanged) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1 % 3, true);
                    pageAutoChange();
                }
            }
        }, 5000);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.toolbar_iv:
                    drawerLayout.openDrawer(Gravity.LEFT);
                    break;
            }
        }
    };


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
                dot.animate().setDuration(250).scaleY(1.4f).scaleX(1.4f).alpha(1f).start();
            } else {
                dot.animate().setDuration(1).scaleY(1.0f).scaleX(1.0f).alpha(1f).start();
            }
        }
    }

    private void animateProgress(ProgressBar progressBar, int start, int end) {
        ObjectAnimator visionAnim = ObjectAnimator.ofInt(progressBar, "progress", start, end); // see this max value coming back here, we animale towards that value
        visionAnim.setDuration(3000);
        //visionAnim.setInterpolator(new DecelerateInterpolator());
        visionAnim.start();
        progressBar.setTag(visionAnim);
    }

    private void setBackground() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.home_content);

        sharedPreferences = getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        manInTheMiddle = sharedPreferences.getInt(BACKGROUND_KEY, 20);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(manInTheMiddle, this);

        linearLayout.setBackgroundResource(drawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tasks.setAdapter(new ContactTasksAdapter(this, 0));
        setBackground();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void setProfileImage(Bitmap image) {
        profileImage = image;
        userTab.updateProfileImage();
        File file = new File(getFilesDir(), "profile.jpg");
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
