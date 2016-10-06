package ir.cdesign.contactrate.models;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 23/08/2016.
 */
public class ContactShowModel {
    public int Title ;
    public int imageId ;

    public void setTitle(int title) {
        this.Title = title;
    }

    public int getTitle() {
        return Title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static ArrayList<TaskModel> getData(){

        ArrayList<TaskModel> dataList = new ArrayList<>();
        int[] images = getImages();
        int[] titles = getTitles();

        for (int i = 0 ; i <images.length ; i++){
            TaskModel task = new TaskModel();
            task.setImageId(images[i]);
            task.setTitle(titles[i]);

            dataList.add(task);
        }

        return dataList;
    }

    public static int[] getTitles() {

        int[] getTitles = {
                R.string.invitations  ,R.string.present  ,R.string.followup  ,
                R.string.enroll      ,R.string.training ,R.string.buildtean,
                R.string.buy       ,R.string.promotingevent,R.string.other
        };
        return getTitles;
    }

    public static int[] getImages() {

        int[] getImages = {
                R.drawable.invitation , R.drawable.presentation ,
                R.drawable.follow_up , R.drawable.enroll ,
                R.drawable.training , R.drawable.team_building ,
                R.drawable.buy , R.drawable.promoting_events ,
                R.drawable.other
        };

        return getImages;
    }

}
