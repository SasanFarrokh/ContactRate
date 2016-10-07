package ir.cdesign.contactrate.lessons;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.File;

/**
 * Created by amin pc on 04/10/2016.
 */
public class LessonPartModel{

    public long id;
    public boolean seen;
    public String title , body ;
    public Uri image ;
    public long lessonId;

    public Bitmap getImage() {
        return BitmapFactory.decodeFile((new File(ir.cdesign.contactrate.utilities.Application.getInstance().getFilesDir(),
                "lesson_"+lessonId+"_part_"+id+".jpg")).getAbsolutePath());
    }

    public void deleteImageFiles() {
        File file = new File(ir.cdesign.contactrate.utilities.Application.getInstance().getFilesDir(),
                "lesson_"+lessonId+"_part_"+id+".jpg");
        if (file.exists()) file.delete();
    }
}
