package ir.cdesign.contactrate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    public static MainActivity instance;
    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        /**
         *Setup the DrawerLayout and NavigationView
         */

             mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
             mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

             mFragmentManager = getSupportFragmentManager();
             mFragmentTransaction = mFragmentManager.beginTransaction();
             mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */
             mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                 if (menuItem.getItemId() == R.id.nav_item_sent) {
                     startActivity(new Intent(MainActivity.this,ContactShow.class));

                 }

                if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }

                 return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

                final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
                ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);
                //mDrawerLayout.setDrawerListener(mDrawerToggle);
                mDrawerLayout.addDrawerListener(mDrawerToggle);
                /*mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                        toolbar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        toolbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        super.onDrawerSlide(drawerView, slideOffset);
                        toolbar.setVisibility(View.GONE);
                    }
                });*/

                mDrawerToggle.syncState();

    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseInit();


    }
    public void databaseInit() {
        database = openOrCreateDatabase(DatabaseCommands.DB_NAME,MODE_PRIVATE,null);
        database.execSQL("CREATE TABLE IF NOT EXISTS "+DatabaseCommands.TABLE_CONTACTS+"(id INTEGER PRIMARY KEY," +
                "name VARCHAR," +
                "phone VARCHAR," +
                "lesson INTEGER," +
                "time INTEGER," +
                "motive INTEGER," +
                "invites VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS "+DatabaseCommands.TABLE_INVITES+
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "type VARCHAR," +
                "note TEXT," +
                "timestamp INTEGER," +
                "active INTEGER);");
    }
}