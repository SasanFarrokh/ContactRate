package ir.cdesign.contactrate.lessons;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

/**
 * Created by Sasan on 2016-10-06.
 */
public class AsyncLessonDownload extends AsyncTask<Long, Void, LessonModel> {

    private final Context context;
    private final Runnable runnable;

    public AsyncLessonDownload(Context context,Runnable runnable) {
        this.context = context;
        this.runnable = runnable;
    }

    @Override
    protected LessonModel doInBackground(Long... params) {
        LessonModel model = null;
        try {
            final URL url = new URL("http://cdesign.ir/mlm/lessons.php?lesson=" + params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String response = null;
            while ((response = bufferedReader.readLine()) != null) {
                stringBuilder.append(response);
            }
            JSONObject jsonObject = new JSONObject(stringBuilder.toString().trim());
            model = new LessonModel();
            model.id = jsonObject.getLong("id");
            model.title = jsonObject.getString("title");

            model.internalImageUrl = saveImage(jsonObject.getString("image"),"lesson_"+model.id+"_lesson.jpg");
            model.showCase = jsonObject.getString("showcase");
            model.author = jsonObject.getString("author");
            model.award = jsonObject.getInt("award");
            model.unlock_point = jsonObject.getInt("unlock_point");
            JSONArray jsonPartsArray = jsonObject.getJSONArray("parts");
            model.parts = new LessonPartModel[jsonPartsArray.length()];
            for (int i = 0 ; i < jsonPartsArray.length() ; i++) {
                JSONObject jsonPart = jsonPartsArray.getJSONObject(i);
                LessonPartModel part = new LessonPartModel();
                part.id = jsonPart.getLong("id");
                part.title = jsonPart.getString("title");
                part.body = jsonPart.getString("body");
                part.image = saveImage(jsonPart.getString("image"),"lesson_"+model.id+"_part_"+part.id+".jpg");
                model.parts[i] = part;
                Log.i("sasan", "set  | Json Part : " + part.title);
            }
            Log.i("sasan", "lesson added "+jsonPartsArray.length()+" : " + model.title);
            DatabaseCommands.getInstance(context).addLesson(model);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @Override
    protected void onPostExecute(LessonModel lessonModel) {
        super.onPostExecute(lessonModel);
        runnable.run();
    }

    private Uri saveImage(String url, String dir) {

        Bitmap imageBitmap = AsyncGetNews.downloadImage(url,context);
        File file = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Uri.fromFile(file);
    }
}
