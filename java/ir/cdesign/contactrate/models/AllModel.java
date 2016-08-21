package ir.cdesign.contactrate.models;

import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;

/**
 * Created by amin pc on 21/08/2016.
 */
public class AllModel {

    public String Title;
    public int imageId;



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public ArrayList<AllModel> getData() {

        ArrayList<AllModel> dataList = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            AllModel model = new AllModel();
            model.setTitle("Contact " + i);

            dataList.add(model);
        }

        return dataList;
    }

}
