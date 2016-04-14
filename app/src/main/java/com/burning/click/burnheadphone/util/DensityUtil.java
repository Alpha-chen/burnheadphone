package com.burning.click.burnheadphone.util;

import android.content.Context;

/**
 * 像素转换
 * Created by click on 16-4-14.
 */
public class DensityUtil {

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int dx2dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }


}
