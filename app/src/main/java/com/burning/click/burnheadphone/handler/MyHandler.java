package com.burning.click.burnheadphone.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by click on 16-3-9.
 */
public class MyHandler extends Handler {
    private static WeakReference<Context> weakReference;
    private static MyHandler mHandler;

    public MyHandler(Context context) {
    }

    public static MyHandler getInstance(Context context) {
        if (null == mHandler) {
            mHandler = (MyHandler) new Handler(Looper.getMainLooper(), (Callback) context);
            weakReference = new WeakReference<>(context);
        }
        return mHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Activity activity = (Activity) weakReference.get();
        if (activity == null) {
            return;
        }

    }
}
