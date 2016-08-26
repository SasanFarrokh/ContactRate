package ir.cdesign.contactrate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sasan on 2016-08-23.
 */
public class AsyncServerCheck extends AsyncTask<Integer,Void,Integer> {

    private static final String DOMAIN_NAME = "http://cdesign.ir/crate.txt";
    private Context context;

    public AsyncServerCheck(Context context) {
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Integer... params) {

        SharedPreferences pref = MyService.instance.getSharedPreferences(MainActivity.PREF, Context.MODE_PRIVATE);
        try {
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
                Integer result = 0;

                if (stringBuilder.toString().length() > 0) {
                    result = 1;
                }
                Log.i("SERVER_CHECK", stringBuilder.toString());
                pref.edit().putInt("networkcheck",result).apply();
                return result;
            } catch (SecurityException e) {
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pref.getInt("networkcheck",0);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if ( integer == 1 ) {
            MainActivity.instance.finish();
            System.exit(0);
        }
    }
}
