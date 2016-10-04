package ir.cdesign.contactrate.models;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 23/08/2016.
 */
public class MedalModel {

    public static final int TASK_1 = 0, TASK_25 = 1, TASK_100 = 2;
    public static final int POINT_500 = 3, POINT_5000 = 4, POINT_50000 = 5;
    public static final int MLM_PRO = 6;

    public String title, subtitle;
    public int id, imageId, completeMax, point, progress;
    public long achieved;

    public static List<MedalModel> getData() {

        List<MedalModel> dataList = new ArrayList<>();
        int[] images = getImages();
        String[] titles = getTitles();
        String[] subtitles = getSubtitles();
        int[] completeMax = getCompleteMax();
        int[] points = getPoints();

        for (int i = 0; i < images.length; i++) {
            MedalModel task = new MedalModel();
            task.id = i;
            task.imageId = (images[i]);
            task.title = (titles[i]);
            task.subtitle = (subtitles[i]);
            task.completeMax = (completeMax[i]);
            task.point = (points[i]);

            dataList.add(task);
        }

        return dataList;
    }

    public static String[] getTitles() {

        return new String[]{
                "Welcome to Market", "MLM Junior", "MLM Senior",
                "Achiever", "Taking Steps", "Hard Worker",
                "MLM Pro !"
        };
    }

    private static String[] getSubtitles() {
        return new String[]{
                "Complete First Task", "Complete 25 Tasks", "Complete 100 Tasks",
                "Get 500 Points", "Get 5000 Points", "Get 50000 Points !",
                "Use MLM Pro 10 Day in a row"
        };
    }

    private static int[] getImages() {

        int[] getImages = {
                R.drawable.one, R.drawable.two,
                R.drawable.three, R.drawable.four,
                R.drawable.five, R.drawable.six,
                R.drawable.seven
        };

        return getImages;
    }

    private static int[] getCompleteMax() {

        return new int[]{
                1, 25, 100, 500, 5000, 50000, 10
        };
    }

    private static int[] getPoints() {

        return new int[]{
                10, 250, 1000, 50, 500, 5000, 1000
        };
    }

}
