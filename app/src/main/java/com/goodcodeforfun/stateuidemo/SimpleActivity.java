package com.goodcodeforfun.stateuidemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.goodcodeforfun.stateui.StateUIActivity;

public class SimpleActivity extends StateUIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DownloadJSONTask task = new DownloadJSONTask(mRecyclerView);
        task.execute(DownloadJSONTask.BASE_URL);
    }

    @Override
    public void onNoDataUI() {
    }

    @Override
    public void onSuccessUI() {
        showSimpleToast("Success");
    }

    @Override
    public void onErrorUI() {
        showSimpleToast("Error");
    }
}
