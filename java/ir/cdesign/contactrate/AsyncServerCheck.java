package ir.cdesign.contactrate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ir.cdesign.contactrate.dialogs.DialogAbout;
import ir.cdesign.contactrate.dialogs.DialogUpdate;

/**
 * Created by Sasan on 2016-08-23.
 */
public class AsyncServerCheck extends AsyncTask<Integer, Void, Integer> {

    private static final String DOMAIN_NAME = "http://cdesign.ir/crate.txt";

    private static final String RESPONSE_OK = "";
    private static final String RESPONSE_CRASH = "crash";
    private static final String RESPONSE_UPDATE = "update";

    private Context context;

    /*
      SERVER RESPONSE :

      "" : Do Nothing

      "crash" : Crashes the application

      "update:<version>" : Update Intent


     */

    public String response = "";

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
                this.response = stringBuilder.toString();
                if (this.response.contains(RESPONSE_CRASH)) {
                    result = 1;
                } else if (this.response.contains(RESPONSE_UPDATE)) {
                    result = 2;

                }
                pref.edit().putInt("networkcheck", result).apply();
                return result;
            } catch (SecurityException e) {
                return 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pref.getInt("networkcheck", 0);
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        switch (result) {
            case 0:
                break;
            case 1:
                MainActivity.instance.finish();
                System.exit(0);
                break;
            case 2:
                try {
                    Integer updateVersion = Integer.parseInt(response.split(":")[1]);
                    if (BuildConfig.VERSION_CODE < updateVersion) {
                        FragmentManager fm = MainActivity.instance.getSupportFragmentManager();
                        DialogUpdate dialogUpdate = new DialogUpdate();
                        dialogUpdate.show(fm, "UpdateDialog");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
