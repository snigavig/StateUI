package com.goodcodeforfun.stateui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public abstract class StateUIActivity extends StateUILifecycleActivity {
    private static final String TAG = StateUIActivity.class.getSimpleName();
    private static final int TOAST_MESSAGE_HANDLER_KEY = 1;
    private static final String TOAST_MESSAGE_TEXT_KEY = "TOAST_MESSAGE_TEXT";
    private static final String TOAST_MESSAGE_DURATION_KEY = "TOAST_MESSAGE_DURATION";
    private static final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case TOAST_MESSAGE_HANDLER_KEY:
                    try {
                        Toast.makeText(
                                StateUIApplication.getContext(),
                                message.getData().getString(TOAST_MESSAGE_TEXT_KEY),
                                message.getData().getBoolean(TOAST_MESSAGE_DURATION_KEY)
                                        ? Toast.LENGTH_LONG
                                        : Toast.LENGTH_SHORT)
                                .show();
                    } catch (StateUIApplication.NoActivityAttachedException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                    break;
                default:
                    break;
            }
            super.handleMessage(message);
        }
    };
    private ProgressDialog mProgressDialog = null;

    public static void showSimpleToast(String messageText) {
        showSimpleToast(messageText, true);
    }

    public static void showSimpleToast(String messageText, boolean longDuration) {
        Bundle bundle = new Bundle();
        bundle.putString(TOAST_MESSAGE_TEXT_KEY, messageText);
        bundle.putBoolean(TOAST_MESSAGE_DURATION_KEY, longDuration);
        Message message = mHandler.obtainMessage(TOAST_MESSAGE_HANDLER_KEY);
        message.setData(bundle);
        message.sendToTarget();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StateUIApplication.setContext(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        if (!StateUIApplication.isInitialised()) {
            StateUIApplication.setContext(this);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StateUIApplication.clearContext();
    }

    @Override
    protected void onStop() {
        super.onStop();
        onIdle();
    }

    @Override
    protected void onIdle() {
        super.onIdle();
        runOnUiThread(new Runnable() {
            public void run() {
                onIdleUI();
            }
        });
    }

    @Override
    public void onNoData() {
        super.onNoData();
        runOnUiThread(new Runnable() {
            public void run() {
                onNoDataUI();
                onStopProgress();
            }
        });
    }

    @Override
    public void onProgress() {
        super.onProgress();
        runOnUiThread(new Runnable() {
            public void run() {
                onProgressUI();
            }
        });
    }

    @Override
    protected void onStopProgress() {
        super.onStopProgress();
        runOnUiThread(new Runnable() {
            public void run() {
                onStopProgressUI();
                onIdle();
            }
        });
    }

    @Override
    protected void onSuccess() {
        super.onSuccess();
        runOnUiThread(new Runnable() {
            public void run() {
                onSuccessUI();
                onStopProgress();
            }
        });
    }

    @Override
    protected void onError() {
        super.onError();
        runOnUiThread(new Runnable() {
            public void run() {
                onErrorUI();
                onStopProgress();
            }
        });
    }

    private void onIdleUI() {
        //not implemented
    }

    protected abstract void onNoDataUI();

    //@CallSuper
    protected void onProgressUI() {
        try {
            mProgressDialog = ProgressDialog
                    .show(StateUIApplication.getContext(),
                            getString(R.string.stateui_default_progress_dialog_title),
                            getString(R.string.stateui_default_progress_dialog_message),
                            true);
        } catch (StateUIApplication.NoActivityAttachedException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
    }

    //@CallSuper
    protected void onStopProgressUI() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected abstract void onSuccessUI();

    protected abstract void onErrorUI();
}
