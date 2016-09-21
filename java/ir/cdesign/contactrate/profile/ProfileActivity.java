package ir.cdesign.contactrate.profile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import ir.cdesign.contactrate.MedalsActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.AllRankInv;
import ir.cdesign.contactrate.homeScreen.HomeNavigation;
import ir.cdesign.contactrate.homeScreen.HomeScreen;

public class ProfileActivity extends AppCompatActivity {

    ViewPager viewPager;
    ImageView profilePhoto;
    MedalFragment medalFragment = new MedalFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
        profilePhoto.setImageBitmap(HomeScreen.profileImage);
    }

    private void init(){
        viewPager = (ViewPager) findViewById(R.id.profile_viewPager);
        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return medalFragment;
                case 1 :
                    return new AllRankInv();
                default:
                    return medalFragment;
            }
        }

        @Override
        public int getCount() {
            return 2 ;
        }

    }


}
