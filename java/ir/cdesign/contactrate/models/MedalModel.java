package ir.cdesign.contactrate.models;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 23/08/2016.
 */
public class MedalModel {

    public String Title ;
    public int imageId ;

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getTitle() {
        return Title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static ArrayList<MedalModel> getData(){

        ArrayList<MedalModel> dataList = new ArrayList<>();
        int[] images = getImages();
        String[] titles = getTitles();

        for (int i = 0 ; i <images.length ; i++){
            MedalModel task = new MedalModel();
            task.setImageId(images[i]);
            task.setTitle(titles[i]);

            dataList.add(task);
        }

        return dataList;
    }

    private static String[] getTitles() {

        String[] getTitles = {
              "Reach 100 Invitations"  ,"Reach 300 Invitations"  ,"Reach 600 Invitations"  ,
              "Reach 700 Invitations"      ,"Reach 1000 Invitations" ,"Reach 1500 Invitations",
              "Reach 1700 Invitations"
        };
        return getTitles;
    }

    private static int[] getImages() {

        int[] getImages = {
            R.drawable.one , R.drawable.two ,
            R.drawable.three , R.drawable.four ,
            R.drawable.five , R.drawable.six ,
            R.drawable.seven
        };

        return getImages;
    }

}
