package ir.cdesign.contactrate.utilities;

import android.app.Activity;
import android.view.View;

/**
 * Created by amin pc on 06/09/2016.
 */
public class WallpaperBoy {
    int imageId, bgId;
    View view;

    public void setWallpaper(View view, int imageId) {
        this.view = view;
        this.imageId = imageId;

        view.setBackgroundResource(imageId);

    }

}
