package com.burning.click.burnheadphone.handler;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by click on 16-3-9.
 */
public class MyHandler extends Handler {
        private WeakReference<Context> weakReference ;

    public MyHandler(Context context){
        weakReference = new WeakReference<>(context);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Activity activity = (Activity) weakReference.get();
            if (activity==null){
                return;
            }

    }
}
