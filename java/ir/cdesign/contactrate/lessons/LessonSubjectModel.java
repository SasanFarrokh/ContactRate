package ir.cdesign.contactrate.lessons;

import android.graphics.Bitmap;

/**
 * Created by amin pc on 15/09/2016.
 */
public class LessonSubjectModel {
    int ID;
    int IDPart;
    int seenCount;
    int award;
    int unlock;

    String imageUrl;
    String title , showCase , author ;
    Bitmap image ;

    //lesson all constructor
    public LessonSubjectModel(String title , String author , int seenCount , int unlock , String imageUrl){
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
        this.seenCount = seenCount ;
        this.unlock = unlock;
    }

    // empty constructor
    public LessonSubjectModel(){

    }

    public String getImageUrl(){
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDPart() {
        return IDPart;
    }

    public void setIDPart(int IDPart) {
        this.IDPart = IDPart;
    }

    public int getSeenCount() {
        return seenCount;
    }

    public void setSeenCount(int seenCount) {
        this.seenCount = seenCount;
    }

    public int getAward() {
        return award;
    }

    public void setAward(int award) {
        this.award = award;
    }

    public int getUnlock() {
        return unlock;
    }

    public void setUnlock(int unlock) {
        this.unlock = unlock;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShowCase() {
        return showCase;
    }

    public void setShowCase(String showCase) {
        this.showCase = showCase;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
