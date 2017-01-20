package com.goodcodeforfun.stateuidemo;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
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
    private static final String TAG = DownloadJSONTask.class.getSimpleName();
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

        String result = null;
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
                    Log.i(TAG, "request did not work.");
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
        try {
            if (result != null) {
                return getDataFromJson(result);
            }
        } catch (StateUIApplication.NoActivityAttachedException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            StateUIApplication.getContext().onProgress();
        } catch (StateUIApplication.NoActivityAttachedException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    @Override
    protected void onPostExecute(ArrayList<String> s) {
        super.onPostExecute(s);
        if (s != null && s.size() > 0) {
            RecyclerView.Adapter adapter = new RecyclerViewAdapter(s);
            mRecyclerView.setAdapter(adapter);
        } else {
            StateUIApplication.onError(StateUIDemoApplication.getInstance().getString(R.string.no_results_error_message));
        }
    }

    private ArrayList<String> getDataFromJson(@NonNull String jsonStr) throws StateUIApplication.NoActivityAttachedException {
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
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
            StateUIApplication.onError(StateUIDemoApplication.getInstance().getString(R.string.parse_error_message));
        }
        return titles;
    }
}
