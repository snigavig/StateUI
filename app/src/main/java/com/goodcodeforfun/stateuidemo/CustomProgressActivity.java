package com.goodcodeforfun.stateuidemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.goodcodeforfun.stateui.StateUIActivity;

public class CustomProgressActivity extends StateUIActivity {
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        DownloadJSONTask task = new DownloadJSONTask(mRecyclerView);
        task.execute(DownloadJSONTask.BASE_URL);
    }

    @Override
    public void onProgressUI() {
        this.mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProgressUI() {
        this.mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onNoDataUI() {
        showSimpleToast("No data loaded");
    }

    @Override
    public void onSuccessUI() {
        showSimpleToast("Success");
    }

    @Override
    public void onErrorUI(String message) {
        showSimpleToast("Error");
    }
}
