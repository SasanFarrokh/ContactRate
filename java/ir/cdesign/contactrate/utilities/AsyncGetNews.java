package ir.cdesign.contactrate.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.adapters.NewsAdapter;

/**
 * Created by Sasan on 2016-09-09.
 */
public class AsyncGetNews extends AsyncTask<Void,Void,List<HashMap>> {

    private static final String DOMAIN_NAME = "http://cdesign.ir/news.php";

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
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String response = null;
            while ( (response = bufferedReader.readLine()) != null ) {
                stringBuilder.append(response);
            }
            JSONArray jsonArray = new JSONArray(stringBuilder.toString().trim());
            List<HashMap> data = new ArrayList<>();
            for ( int i = 0 ; i < jsonArray.length() ; i++) {
                JSONObject newsObj = jsonArray.getJSONObject(i);
                HashMap<String,String> newsMap = new HashMap<>();
                newsMap.put("image",newsObj.getString("image"));
                newsMap.put("title",newsObj.getString("title"));
                newsMap.put("text",newsObj.getString("text"));
                newsMap.put("type",newsObj.getString("type"));
                data.add(newsMap);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<HashMap> data) {
        super.onPostExecute(data);
        recyclerView.setAdapter(new NewsAdapter(context,data,5));
    }
}
