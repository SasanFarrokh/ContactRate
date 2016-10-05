package ir.cdesign.contactrate.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.NewsAdapter;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.homeScreen.NewsActivity;

/**
 * Created by Sasan on 2016-09-09.
 */
public class AsyncGetNews extends AsyncTask<Void, Void, List<HashMap>> {

    public static final String DOMAIN_NAME = "http://cdesign.ir/mlm/news.php";
    public static List<HashMap> cacheData;

    private Context context;
    private RecyclerView recyclerView;
    int limit;

    public AsyncGetNews(Context context, RecyclerView recyclerView, int limit) {
        this.limit = limit;
        this.context = context;
        this.recyclerView = recyclerView;
        try {
            if (recyclerView.getAdapter().getItemCount() == 0)
                ((ViewGroup) recyclerView.getParent().getParent()).findViewById(R.id.progress).setVisibility(View.VISIBLE);
        } catch (Exception ignore) {
        }
        if (cacheData != null) {
            recyclerView.setAdapter(new NewsAdapter(context, cacheData, limit));
            GridLayoutManager glm = new GridLayoutManager(context, 2) {
                @Override
                public boolean canScrollVertically() {
                    return AsyncGetNews.this.limit >= 4 && super.canScrollVertically();
                }
            };
            glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    try {
                        return (Integer.parseInt((String) cacheData.get(position).get("type")) == 0) ? 1 : 2;
                    } catch (Exception e) {
                        return 1;
                    }
                }
            });
            recyclerView.setLayoutManager(glm);
        }
    }

    public AsyncGetNews(Context context, RecyclerView recyclerView) {
        this(context, recyclerView, 10);
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
            while ((response = bufferedReader.readLine()) != null) {
                stringBuilder.append(response);
            }
            JSONArray jsonArray = new JSONArray(stringBuilder.toString().trim());
            List<HashMap> data = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newsObj = jsonArray.getJSONObject(i);
                HashMap<String, Object> newsMap = new HashMap<>();
                newsMap.put("image", newsObj.getString("image"));
                newsMap.put("title", newsObj.getString("title"));
                newsMap.put("text", newsObj.getString("text"));
                newsMap.put("type", newsObj.getString("type"));
                newsMap.put("id", newsObj.getInt("id"));
                newsMap.put("category", newsObj.getString("category"));
                newsMap.put("viewed", newsObj.getString("viewed"));
                data.add(newsMap);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (context instanceof LoadingFail)
                    ((LoadingFail) context).fail();
            } catch (Exception ignore) {
            }
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(final List<HashMap> data) {
        super.onPostExecute(data);
        cacheData = data;
        GridLayoutManager glm = new GridLayoutManager(context, 2) {
            @Override
            public boolean canScrollVertically() {
                return limit >= 4 && super.canScrollVertically();
            }
        };
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
        recyclerView.setAdapter(new NewsAdapter(context, data, limit));
        recyclerView.setLayoutManager(glm);
        try {
            if (recyclerView.getParent() instanceof SwipeRefreshLayout)
                ((SwipeRefreshLayout) recyclerView.getParent()).setRefreshing(false);
            ((ViewGroup) recyclerView.getParent().getParent()).findViewById(R.id.progress).setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Bitmap downloadImage(String url, Context context) {
        Bitmap finalImage = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            finalImage = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
            finalImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.news_land);
        }
        return finalImage;
    }


    public interface LoadingFail {
        void fail();
    }
}
