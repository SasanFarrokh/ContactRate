package ir.cdesign.contactrate.homeScreen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.tasks.TasksActivity;

/**
 * Created by sasan pc on 31/08/2016.
 */
public class UserTab extends Fragment {

    ImageView profileBig;
    TextView profileName;

    public static UserTab instance;

    private View view;

    TextView doneText, pendingText, points, visions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_user, container, false);

        init();

        updateProfileImage();

        instance = this;

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    public void updateProfileImage() {
        if (HomeScreen.profileImage != null && profileBig != null) {
            profileBig.setColorFilter(Color.TRANSPARENT);
            profileBig.setImageBitmap(HomeScreen.profileImage);
        }
    }

    private void init() {
        profileBig = (ImageView) view.findViewById(R.id.profile_big);
        profileName = (TextView) view.findViewById(R.id.profile_name);
        doneText = (TextView) view.findViewById(R.id.done_text);
        pendingText = (TextView) view.findViewById(R.id.pending_text);
        points = (TextView) view.findViewById(R.id.points);
        visions = (TextView) view.findViewById(R.id.vision_text);

        profileName.setText(((HomeScreen) getActivity()).profileName);

        ((View) doneText.getParent().getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TasksActivity.class).putExtra(TasksActivity.PAGE_SCROLL,1));
            }
        });
        ((View) pendingText.getParent().getParent()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TasksActivity.class).putExtra(TasksActivity.PAGE_SCROLL,0));
            }
        });
    }

    public void setData() {
        DatabaseCommands db = DatabaseCommands.getInstance(getContext());
        setPoints();
        int doneTask = db.getDoneTask();
        int pendingTask = db.getPendingTask();

        List<HashMap> visionsData = db.getVision(0);

        doneText.setText(String.valueOf(doneTask));
        pendingText.setText(String.valueOf(pendingTask));
        visions.setText(String.valueOf(visionsData.size()));

        Double visionPercent = (visionsData.size() != 0)?
                ((double) (System.currentTimeMillis() - (long) visionsData.get(0).get("regdate")) /
                        ((long) visionsData.get(0).get("timestamp") - (long) visionsData.get(0).get("regdate"))) * 100:0;
        visionPercent = Math.max(0,Math.min(visionPercent,100));
    }

    public void setPoints() {
        DatabaseCommands db = DatabaseCommands.getInstance(getContext());
        int p = db.getUserPoint();
        points.setText(String.valueOf(p));
    }
}
