package com.goodcodeforfun.stateui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class StateUILifecycleActivity extends AppCompatActivity {

    private UIState mCurrentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onIdle();
    }

    void onIdle() {
        changeState(UIState.IDLE);
    }

    void onNoData() {
        changeState(UIState.NO_DATA);
    }

    protected void onProgress() {
        changeState(UIState.PROGRESS);
    }

    void onStopProgress() {
        changeState(UIState.STOP_PROGRESS);
    }

    void onSuccess() {
        changeState(UIState.SUCCESS);
    }

    void onError() {
        changeState(UIState.ERROR);
    }

    private void changeState(UIState state) {
        if (getCurrentState() != state)
            setCurrentState(state);
    }

    private UIState getCurrentState() {
        return mCurrentState;
    }

    private void setCurrentState(UIState mCurrentState) {
        this.mCurrentState = mCurrentState;
    }

    public enum UIState {
        IDLE,
        NO_DATA,
        PROGRESS,
        STOP_PROGRESS,
        SUCCESS,
        ERROR,
    }
}
