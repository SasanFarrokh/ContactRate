package ir.cdesign.contactrate.lessons;

import android.graphics.Bitmap;
import android.net.Uri;

import java.net.URI;

/**
 * Created by amin pc on 15/09/2016.
 */
public class LessonModel {
    int ID;
    LessonPartModel[] parts;
    int seenCount;
    int award;
    int unlock_point;

    Uri internalImageUrl;
    String title , showCase , author ;

    String imageUrl;

    public int getProgress() {
        return 50;
    }

    //lesson all constructor
    public LessonModel(String title , String author , int seenCount , int unlock_point , String imageUrl){
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.seenCount = seenCount ;
        this.unlock_point = unlock_point;
    }

    // empty constructor
    public LessonModel(){

    }
}
