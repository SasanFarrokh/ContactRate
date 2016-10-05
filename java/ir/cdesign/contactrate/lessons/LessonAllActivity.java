package ir.cdesign.contactrate.lessons;

import android.app.Application;
import android.app.ProgressDialog;
import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.homeScreen.HomeScreen;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class LessonAllActivity extends AppCompatActivity {

    Toolbar toolbar ;
    RecyclerView recyclerView ;
    ProgressDialog pDialog;
    LessonAllAdapter adapter;
    List<LessonSubjectModel> lessonAllList = new ArrayList<>();

    private static final String url = "http://api.androidhive.info/json/movies.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_mark_activity);

        init();

        // set wallpaper
        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        int drawable = wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setBackgroundResource(drawable);

        setRecyclerView();

    }

    private void init(){
        toolbar = new Toolbar(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        setSupportActionBar(toolbar);
    }

    private void setRecyclerView(){

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        adapter = new LessonAllAdapter(this , lessonAllList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hidePDialog();
                    Log.d("amin", "doing shit");
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                LessonSubjectModel lesson = new LessonSubjectModel();
                                lesson.setTitle(obj.getString("title"));
                                lesson.setImageUrl(obj.getString("image"));
                                lesson.setUnlock(((Number) obj.get("rating"))
                                        .intValue());
                                lesson.setAuthor(String.valueOf(obj.getInt("releaseYear")));

                                // Genre is json array
//                                JSONArray genreArry = obj.getJSONArray("genre");
//                                ArrayList<String> genre = new ArrayList<String>();
//                                for (int j = 0; j < genreArry.length(); j++) {
//                                    genre.add((String) genreArry.get(j));
//                                }

                                // adding movie to movies array
                                lessonAllList.add(lesson);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("amin", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
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
