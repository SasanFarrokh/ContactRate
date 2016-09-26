package ir.cdesign.contactrate.homeScreen;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.OutputStream;

import ir.cdesign.contactrate.MainActivity;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.Vision.Visions;
import ir.cdesign.contactrate.demoedition.VisionDemo;
import ir.cdesign.contactrate.dialogs.DialogAbout;
import ir.cdesign.contactrate.dialogs.DialogSettings;
import ir.cdesign.contactrate.imagePicker.DefaultCallback;
import ir.cdesign.contactrate.imagePicker.EasyImage;
import ir.cdesign.contactrate.lessons.Lesson;
import ir.cdesign.contactrate.lessons.ReadLessonActivity;
import ir.cdesign.contactrate.profile.ProfileActivity;
import ir.cdesign.contactrate.tasks.TasksActivity;
import ir.cdesign.contactrate.tutorial.Tutorial;
import ir.cdesign.contactrate.utilities.Settings;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

/**
 * Created by amin pc on 31/08/2016.
 */
public class HomeNavigation extends Fragment {

    private static final int IMAGE_PICKED = 9;
    Intent intent;
    View settings,about,task,tutorial,home,vision,statistics,lessons,news,contacts;
    ImageView profile;
    HomeScreen homeScreen;
    TextView profileName, profileNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_navigation, container, false);

        settings = view.findViewById(R.id.settings);
        about = view.findViewById(R.id.about);
        task = view.findViewById(R.id.task);
        tutorial = view.findViewById(R.id.tutorial);
        home =  view.findViewById(R.id.home);
        vision = view.findViewById(R.id.vision);
        statistics = view.findViewById(R.id.stats);
        lessons = view.findViewById(R.id.lessons);
        news = view.findViewById(R.id.news);
        contacts = view.findViewById(R.id.contacts);
        profile = (ImageView) view.findViewById(R.id.profile_photo);
        profileName = (TextView) view.findViewById(R.id.profile_name);
        profileNumber = (TextView) view.findViewById(R.id.profile_number);


        settings.setOnClickListener(listener);
        about.setOnClickListener(listener);
        task.setOnClickListener(listener);
        tutorial.setOnClickListener(listener);
        home.setOnClickListener(listener);
        vision.setOnClickListener(listener);
        statistics.setOnClickListener(listener);
        lessons.setOnClickListener(listener);
        news.setOnClickListener(listener);
        contacts.setOnClickListener(listener);
        profile.setOnClickListener(listener);
        
        profileName.setText(((HomeScreen) getActivity()).profileName);
        profileNumber.setText(((HomeScreen) getActivity()).profileNumber);
        if (((HomeScreen) getActivity()).profileImage != null)
            profile.setImageBitmap(((HomeScreen) getActivity()).profileImage);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting((HomeScreen.manInTheMiddle +1) % 21,getActivity());
        view.setBackgroundResource(drawable);

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();

            switch (v.getId()) {
                case R.id.settings:
                    intent = new Intent(getActivity(), Settings.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                case R.id.about:
                    DialogAbout dialogAbout = new DialogAbout();
                    dialogAbout.show(fm, "about");
                    break;
                case R.id.task:
                    intent = new Intent(getActivity(), TasksActivity.class);
                    startActivity(intent);
                    break;
                case R.id.contacts:
                    intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.tutorial:
                    intent = new Intent(getActivity(), Tutorial.class);
                    startActivity(intent);
                    break;
                case R.id.home:
                    intent = new Intent(getActivity(), ProfileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.vision:
                    intent = new Intent(getActivity(), Visions.class);
                    startActivity(intent);
                    break;
                case R.id.stats:
                    intent = new Intent(getActivity(), Statistics.class);
                    startActivity(intent);
                    break;
                case R.id.lessons:
                    intent = new Intent(getActivity(), Lesson.class);
                    startActivity(intent);
                    break;
                case R.id.news:
                    intent = new Intent(getActivity(), NewsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.profile_photo:
                    Intent i = new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, IMAGE_PICKED);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IMAGE_PICKED:
                if (data == null) return;
                Uri selectedImage = data.getData();

                Bitmap image = getProfileBitmap(getContext(), selectedImage);

                profile.setColorFilter(Color.TRANSPARENT);
                ((HomeScreen) getActivity()).setProfileImage(image);
                ((HomeScreen) getActivity()).userTab.updateProfileImage();
                profile.setImageBitmap(image);

                break;
        }
    }

    public static Bitmap getProfileBitmap(Context ctx, Uri selectedImage) {
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = ctx.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                Bitmap bigImage = BitmapFactory.decodeFile(picturePath, options);

                int imageSize = ctx.getResources().getDimensionPixelSize(R.dimen.nav_profile_image);
                imageSize *= 7;
                float ratio = ((float) bigImage.getWidth()) / bigImage.getHeight();
                Bitmap image = null;
                if (ratio >= 1) {
                    Float height = imageSize / ratio;
                    Log.i("sasan", "gallery pick h : " + height.intValue());
                    image = Bitmap.createScaledBitmap(bigImage, imageSize, height.intValue(), true);
                    image = Bitmap.createBitmap(
                            image,
                            image.getWidth() / 2 - image.getHeight() / 2,
                            0,
                            image.getHeight(),
                            image.getHeight()
                    );
                } else {
                    Float width = imageSize * ratio;
                    Log.i("sasan", "gallery pick w : " + width.intValue());
                    image = Bitmap.createScaledBitmap(bigImage, width.intValue(), imageSize, true);
                    image = Bitmap.createBitmap(
                            image,
                            0,
                            image.getHeight() / 2 - image.getWidth() / 2,
                            image.getWidth(),
                            image.getWidth()
                    );
                }
                return image;
            }
        } catch (Exception ignore) {}
        return null;
    }
}
