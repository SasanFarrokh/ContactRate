package ir.cdesign.contactrate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.mofakhrpour.farhoosh.androidpayment.FarhooshPayment;


import ir.cdesign.contactrate.demoedition.ContactsDemo;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;


public class MainActivity extends AppCompatActivity {
    public static final String PREF = "MlmPref";
    DrawerLayout mDrawerLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    public static Boolean firstTime = true;

    public static Boolean appActive = false;

    public static MainActivity instance;

    public static AlarmReciever alarm = new AlarmReciever();;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        linearLayout= (LinearLayout) findViewById(R.id.LinearLayout);
        linearLayout.setBackgroundResource(drawable);

//        FarhooshPayment farhooshPayment = new FarhooshPayment();
//        farhooshPayment.Start(this,26378337,R.mipmap.ic_launcher);

        instance = this;

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        if (!getSharedPreferences(MainActivity.PREF,MODE_PRIVATE).getBoolean("contacts_demo",false)) {
            ContactsDemo demo = new ContactsDemo();
            demo.show(getSupportFragmentManager(),"demo");
            getSharedPreferences(MainActivity.PREF,MODE_PRIVATE).edit().putBoolean("contacts_demo",true).apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, MyService.class));
    }


}