package com.goodcodeforfun.stateui;

import android.app.Application;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by snigavig on 14.08.16.
 */

public class StateUIApplication extends Application {
    private static final String TAG = StateUIApplication.class.getSimpleName();

    private static boolean initialised = false;
    private static StateUIApplication mInstance;
    private WeakReference<StateUIActivity> mActivityWeakReference;

    public static StateUIApplication getInstance() {
        return mInstance;
    }

    public static StateUIActivity getContext() {
        return getInstance().mActivityWeakReference.get();
    }

    public static void setContext(StateUIActivity activity) {
        mInstance.mActivityWeakReference = new WeakReference<>(activity);
        initialised = true;
    }

    public static boolean isInitialised() {
        return initialised;
    }

    public static void clearContext() {
        getInstance().mActivityWeakReference.clear();
        initialised = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "StatUI Application started");
        init(this);
    }

    private void init(Application application) {
        mInstance = (StateUIApplication) application;
    }

    public static void onError() {
        if(isInitialised()) {
            getContext().onError();
        }
    }

    public static void onSuccess() {
        if (isInitialised()) {
            getContext().onSuccess();
        }
    }
}
