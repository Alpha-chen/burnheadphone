package com.burning.click.burnheadphone.util;

import android.app.Activity;
import android.util.DisplayMetrics;

import com.burning.click.burnheadphone.Log.LogUtil;

/**
 * Created by click on 16-4-14.
 */
public class ScreenUtil {

    public static int[] getScreenPixels(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        LogUtil.d("ScreenUtil","width="+width+"  height="+height);
        int[] screen = new int[]{width, height};
        return screen;
    }

}
