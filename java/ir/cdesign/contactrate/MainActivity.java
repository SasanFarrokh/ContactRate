package ir.cdesign.contactrate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mofakhrpour.farhoosh.androidpayment.FarhooshPayment;

import java.util.Calendar;

import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.JalaliCalendar;
import ir.cdesign.contactrate.utilities.WallpaperBoy;


public class MainActivity extends AppCompatActivity {
    public static final String PREF = "MlmPref";
    DrawerLayout mDrawerLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    public static Boolean firstTime = true;

    public static Boolean appActive = false;

    public static MainActivity instance;

    public AlarmReciever alarm;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int theSitter = HomeScreen.manInTheMiddle;
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        Drawable drawable = wallpaperBoy.manSitting(theSitter,this);
        linearLayout= (LinearLayout) findViewById(R.id.LinearLayout);
        linearLayout.setBackground(drawable);

        FarhooshPayment farhooshPayment = new FarhooshPayment();
//        farhooshPayment.Start(this,26378337,R.mipmap.ic_launcher);

        instance = this;
        alarm = new AlarmReciever();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MyService.class));
        new AsyncServerCheck(this).execute();
    }


    /*@Override
    public void onBackPressed() {
        finish();
    }*/

}