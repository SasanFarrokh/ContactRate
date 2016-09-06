package ir.cdesign.contactrate.models;

import java.util.ArrayList;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 06/09/2016.
 */
public class ImageDisplayModel {
    public int imageId ;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public static ArrayList<ImageDisplayModel> getData(){

        ArrayList<ImageDisplayModel> dataList = new ArrayList<>();
        int[] images = getImages();

        for (int i = 0 ; i <images.length ; i++){
            ImageDisplayModel imageDisplayModel = new ImageDisplayModel();
            imageDisplayModel.setImageId(images[i]);

            dataList.add(imageDisplayModel);
        }

        return dataList;
    }

    public static int[] getImages(){
        int[] getImages = new int[]{
                R.drawable.background , R.drawable.background ,R.drawable.background ,R.drawable.background ,
                R.drawable.background ,R.drawable.background ,R.drawable.background ,R.drawable.background
        };

        return getImages;
    }

}
