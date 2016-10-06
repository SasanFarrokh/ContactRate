package ir.cdesign.contactrate.lessons;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class LessonAllActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressDialog pDialog;
    LessonAllAdapter adapter;
    List<LessonModel> lessonAllList = new ArrayList<>();

    //private static final String url = "http://api.androidhive.info/json/movies.json";
    private static final String url = "http://cdesign.ir/mlm/lessons.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_mark_activity);

        init();

        // set wallpaper
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setBackgroundResource(drawable);

        setRecyclerView();

    }

    private void init() {
        toolbar = new Toolbar(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        setSupportActionBar(toolbar);
    }

    private void setRecyclerView() {

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        adapter = new LessonAllAdapter(this, lessonAllList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        StringRequest movieReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidePDialog();
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject obj = jsonArray.getJSONObject(i);
                        LessonModel lesson = new LessonModel();
                        lesson.setTitle(obj.getString("title"));
                        lesson.setImageUrl(obj.getString("image"));
                        /*lesson.setUnlock(obj.getInt("unlock_point"));
                        lesson.setAuthor(obj.getString("author"));
                        lesson.setAward(obj.getInt("award"));
                        lesson.setID(obj.getInt("id"));
                        lesson.setShowCase(obj.getString("showcase"));*/

                        lessonAllList.add(lesson);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("amin", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidePDialog();
            }
        });
        // Adding request to request queue
        ir.cdesign.contactrate.utilities.Application.getInstance().addToRequestQueue(movieReq);

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


}
