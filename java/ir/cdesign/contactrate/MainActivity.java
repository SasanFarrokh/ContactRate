package ir.cdesign.contactrate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    CoordinatorLayout coordinatorLayout;
    public static String name;

    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        setDrawer();


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        new AsyncServerCheck().execute();
        nameInNav();

    }



    private void setDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationDrawer navigationDrawer = (NavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.nav_dwr_frg);
        navigationDrawer.setUpDrawer(R.id.nav_dwr_frg, mDrawerLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(this, MyService.class));
    }

    //to set user name in navigation drawer
    private void nameInNav(){
        Bundle bundle = getIntent().getExtras();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.mother);
        name = bundle.getString("userName");
        TextView userName = (TextView) findViewById(R.id.nav_user_name);
        userName.setText(name);
        SharedPreferences user = getSharedPreferences("userName",MODE_PRIVATE);
        SharedPreferences.Editor editor = user.edit();
        editor.putString("name",name).apply();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}