package ir.cdesign.contactrate;

import android.app.Service;
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

public class MainActivity extends AppCompatActivity {
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

        setDrawer();

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();


        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        new AsyncServerCheck().execute();
    }

    private void setDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationDrawer navigationDrawer = (NavigationDrawer) getSupportFragmentManager().findFragmentById(R.id.nav_dwr_frg);
        navigationDrawer.setUpDrawer(R.id.nav_dwr_frg, mDrawerLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseInit();
        startService(new Intent(this,MyService.class));
    }

    public void databaseInit() {
        database = openOrCreateDatabase(DatabaseCommands.DB_NAME, MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseCommands.TABLE_CONTACTS + "(id INTEGER PRIMARY KEY," +
                "name VARCHAR," +
                "phone VARCHAR," +
                "lesson INTEGER," +
                "time INTEGER," +
                "motive INTEGER," +
                "note TEXT," +
                "invites VARCHAR);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseCommands.TABLE_INVITES +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "type VARCHAR," +
                "contact INTEGER," +
                "note TEXT," +
                "timestamp INTEGER," +
                "active INTEGER);");
    }
}