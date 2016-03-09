package com.burning.click.burnheadphone;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 全局application
 * Created by click on 16-3-9.
 */
public class BHPApplication extends Application {
    public static BHPApplication mApplication;
    public static Context appContext;
    private static boolean DEBUG;
    @Override
    public void onCreate() {
        super.onCreate();
        if (mApplication==null){
            mApplication = (BHPApplication) getApplicationContext();
        }
        if (appContext==null){
            appContext= getApplicationContext();
        }
        // 初始化fresco
        Fresco.initialize(mApplication);
        // 判断当前是什么模式
        initMode();
    }

    private void initMode() {
        DEBUG=isApkDebugable(mApplication);
    }

    public static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info= context.getApplicationInfo();
            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        } catch (Exception e) {
        e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
