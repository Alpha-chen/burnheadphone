package com.burning.click.burnheadphone;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.burning.click.burnheadphone.node.UserNode;
import com.burning.click.burnheadphone.sp.SpUtils;
import com.burning.click.burnheadphone.util.SpkeyName;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 全局application
 * Created by click on 16-3-9.
 */
public class BHPApplication extends Application {
    public static BHPApplication mApplication;
    public static Context appContext;
    public static boolean DEBUG = true;
    public static UserNode userNode;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mApplication == null) {
            mApplication = (BHPApplication) getApplicationContext();
        }
        if (appContext == null) {
            appContext = getApplicationContext();
        }
        initMode();
        if (null == userNode) {
            String tempJson = SpUtils.getString(this, SpUtils.BHP_SHARF, SpkeyName.USER_NODE);
            try {
                userNode = new UserNode(new JSONObject(tempJson));
                UserNode.setmUserNode(userNode);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // 初始化fresco
        Fresco.initialize(mApplication);
        // 判断当前是什么模式
        if (DEBUG) {

//            LeakCanary.install(this);
//            BlockCanary.install(this, new AppBlockCanaryContext());
        }
    }

    private void initMode() {
        DEBUG = isApkDebugable(mApplication);
    }

    private static boolean isApkDebugable(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
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
