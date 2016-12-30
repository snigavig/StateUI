package com.goodcodeforfun.stateuidemo;

import android.util.Log;

import com.goodcodeforfun.stateui.StateUIApplication;

/**
 * Created by snigavig on 31.12.16.
 */

public class StateUIdemoApplication extends StateUIApplication {
    private static final String TAG = StateUIdemoApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Application started");
    }
}
