package ir.cdesign.contactrate.homeScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 31/08/2016.
 */
public class userTab extends Fragment {

    ImageView profileBig;
    TextView profileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_user, container, false);

        profileBig = (ImageView) view.findViewById(R.id.profile_big);
        profileName = (TextView) view.findViewById(R.id.profile_name);
        profileName.setText(((HomeScreen) getActivity()).profileName);
        updateProfileImage();

        return view;
    }

    public void updateProfileImage() {
        if (((HomeScreen) getActivity()).profileImage != null) {
            profileBig.setColorFilter(Color.TRANSPARENT);
            profileBig.setImageBitmap(((HomeScreen) getActivity()).profileImage);
        }
    }
}
