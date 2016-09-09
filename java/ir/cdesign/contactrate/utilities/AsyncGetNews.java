package ir.cdesign.contactrate.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.adapters.NewsAdapter;

/**
 * Created by Sasan on 2016-09-09.
 */
public class AsyncGetNews extends AsyncTask<Void, Void, List<HashMap>> {

    private static final String DOMAIN_NAME = "http://cdesign.ir/mlm/news.php";

    private Context context;
    private RecyclerView recyclerView;

    public AsyncGetNews(RecyclerView recyclerView, Context context) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected List<HashMap> doInBackground(Void... params) {

        try {
            URL url = new URL(DOMAIN_NAME);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String response = null;
            while ((response = bufferedReader.readLine()) != null) {
                stringBuilder.append(response);
            }
            JSONArray jsonArray = new JSONArray(stringBuilder.toString().trim());
            List<HashMap> data = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newsObj = jsonArray.getJSONObject(i);
                HashMap<String, Object> newsMap = new HashMap<>();
                newsMap.put("image", downloadImage(newsObj.getString("image")));
                newsMap.put("title", newsObj.getString("title"));
                newsMap.put("text", newsObj.getString("text"));
                newsMap.put("type", newsObj.getString("type"));
                newsMap.put("id", newsObj.getString("id"));
                newsMap.put("category", newsObj.getString("category"));
                newsMap.put("viewed", newsObj.getString("viewed"));
                data.add(newsMap);
                Log.d("sasan", "news : " + newsObj.getString("text"));
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(final List<HashMap> data) {
        super.onPostExecute(data);
        Log.d("sasan", "newsCount : " + data.size());
        GridLayoutManager glm = new GridLayoutManager(context, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                try {
                    return (Integer.parseInt((String) data.get(position).get("type")) == 0) ? 1 : 2;
                } catch (Exception e) {
                    return 1;
                }
            }
        });
        recyclerView.setAdapter(new NewsAdapter(context, data, 5));
        recyclerView.setLayoutManager(glm);
    }

    public Bitmap downloadImage(String... urls) {
        String urldisplay = urls[0];
        Bitmap finalImage = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            finalImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return finalImage;
    }
}
