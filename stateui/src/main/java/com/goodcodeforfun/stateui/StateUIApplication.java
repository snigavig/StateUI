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

    private static StateUIApplication getInstance() {
        return mInstance;
    }

    public static StateUIActivity getContext() throws NoActivityAttachedException {
        if (getInstance().mActivityWeakReference != null) {
            return getInstance().mActivityWeakReference.get();
        } else {
            throw new NoActivityAttachedException(getInstance().getString(R.string.no_activity_exception_message));
        }
    }

    public static void setContext(StateUIActivity activity) {
        mInstance.mActivityWeakReference = new WeakReference<>(activity);
        initialised = true;
    }

    public static boolean isInitialised() {
        return initialised;
    }

    public static void clearContext() {
        if (getInstance().mActivityWeakReference != null) {
            getInstance().mActivityWeakReference.clear();
        }
        initialised = false;
    }

    public static void onError() {
        if(isInitialised()) {
            try {
                getContext().onError();
            } catch (NoActivityAttachedException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }

    public static void onSuccess() {
        if (isInitialised()) {
            try {
                getContext().onSuccess();
            } catch (NoActivityAttachedException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
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

    public static class NoActivityAttachedException extends Exception {
        NoActivityAttachedException(String message) {
            super(message);
        }
    }
}
