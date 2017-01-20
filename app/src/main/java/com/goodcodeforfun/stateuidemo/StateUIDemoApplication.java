package com.goodcodeforfun.stateuidemo;

import android.util.Log;

import com.goodcodeforfun.stateui.StateUIApplication;

/**
 * Created by snigavig on 31.12.16.
 */

public class StateUIDemoApplication extends StateUIApplication {
    private static final String TAG = StateUIDemoApplication.class.getSimpleName();

    private static StateUIDemoApplication mInstance;

    public static StateUIDemoApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Log.d(TAG, "Application started");
    }
}
