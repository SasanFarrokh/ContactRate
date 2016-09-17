package ir.cdesign.contactrate.models;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 06/09/2016.
 */
public class ImageDisplayModel {
    public int imageId ;
    public int TagInt;

    public int getTagInt() {
        return TagInt;
    }

    public void setTagInt(int tagInt) {
        TagInt = tagInt;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static ArrayList<ImageDisplayModel> getData(){

        ArrayList<ImageDisplayModel> dataList = new ArrayList<>();
        int[] images = getImages();
        int[] tags = getTags();

        for (int i = 0 ; i <images.length ; i++){
            ImageDisplayModel imageDisplayModel = new ImageDisplayModel();
            imageDisplayModel.setImageId(images[i]);
            imageDisplayModel.setTagInt(tags[i]);
            dataList.add(imageDisplayModel);
        }

        return dataList;
    }

    public static int[] getImages(){
        int[] getImages = new int[]{
                 R.drawable.bg_2 ,R.drawable.bg_3 ,R.drawable.bg_4 ,
                R.drawable.bg_5 ,R.drawable.bg_6 ,R.drawable.bg_7 ,R.drawable.bg_8,
                R.drawable.bg_9 ,R.drawable.bg_10 ,R.drawable.bg_11 ,R.drawable.bg_12,
                R.drawable.bg_13 ,R.drawable.bg_14 ,R.drawable.bg_15 ,R.drawable.bg_16,
                R.drawable.bg_17 ,R.drawable.bg_18 ,R.drawable.bg_19 ,R.drawable.bg_20,
                R.drawable.bg_21
        };

        return getImages;
    }

    public static int[] getTags(){
        int[] getTags = new int[]{
              1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20
        };
        return getTags;
    }

}
