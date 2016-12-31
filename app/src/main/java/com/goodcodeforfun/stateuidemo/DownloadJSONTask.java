package com.goodcodeforfun.stateuidemo;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.goodcodeforfun.stateui.StateUIApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by snigavig on 30.05.16.
 */
class DownloadJSONTask extends AsyncTask<String, Void, ArrayList<String>> {
    static final String BASE_URL =
            "http://jsonplaceholder.typicode.com/photos/";
    private static final String LOG_TAG = DownloadJSONTask.class.getSimpleName();
    private final RecyclerView mRecyclerView;

    DownloadJSONTask(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String result = "";
        for (String url : urls) {
            URL obj;
            HttpURLConnection con;
            try {
                obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) { //success
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    result = response.toString();
                } else {
                    Log.i(LOG_TAG, "request did not work.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getDataFromJson(result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        StateUIApplication.getContext().onProgress();
    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
        super.onPostExecute(s);
        RecyclerView.Adapter adapter = new RecyclerViewAdapter(s);
        mRecyclerView.setAdapter(adapter);
    }

    private ArrayList<String> getDataFromJson(String jsonStr) {
        final String TITLE = "title";
        ArrayList<String> titles = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                String title;
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                title = jsonObj.getString(TITLE);
                titles.add(title);
            }
            StateUIApplication.onSuccess();
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            StateUIApplication.onError();
        }
        return titles;
    }
}
