package ir.cdesign.contactrate.dialogs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.adapters.NewsAdapter;
import ir.cdesign.contactrate.homeScreen.NewsActivity;
import ir.cdesign.contactrate.utilities.AsyncGetNews;

/**
 * Created by amin pc on 05/09/2016.
 */
public class DialogNews extends DialogFragment {

    HashMap data;
    Bitmap bitmap;

    int id;

    TextView title,body,counter;
    ImageView image, closeBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_dialog, container, false);
        if (getArguments().getSerializable("data") != null)
            data = (HashMap) getArguments().getSerializable("data");
        if (getArguments().getParcelable("image") != null)
            bitmap = getArguments().getParcelable("image");

        id = (int) data.get("id");
        title = (TextView) view.findViewById(R.id.news_title_text);
        body = (TextView) view.findViewById(R.id.news_body_text);
        counter = (TextView) view.findViewById(R.id.news_counter);
        image = (ImageView) view.findViewById(R.id.news_image);
        closeBtn = (ImageView) view.findViewById(R.id.news_close);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        title.setText((String) data.get("title"));
        body.setText((String) data.get("text"));
        counter.setText((String) data.get("viewed"));
        if (bitmap != null)
            image.setImageBitmap(bitmap);
        else {
            new NewsAdapter.BitmapWorkerTask(image, (int) data.get("id")).execute((String) data.get("image"));
        }

        (new AsyncViewNews()).execute(id);

        return view;
    }


    private class AsyncViewNews extends AsyncTask<Integer,Void,Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {

            try {
                URL url = new URL(AsyncGetNews.DOMAIN_NAME + "?view=" + params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                bufferedReader.readLine();
                return true;
            } catch (Exception ignored) {}
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                try {
                    counter.setText(String.valueOf(Integer.parseInt(counter.getText().toString()) + 1));
                } catch (Exception ignored) {
                }
            }
        }
    }
}
