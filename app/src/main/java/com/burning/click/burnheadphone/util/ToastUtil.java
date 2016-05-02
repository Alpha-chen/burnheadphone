package com.burning.click.burnheadphone.util;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * 显示类
 * Created by click on 16-3-10.
 */
public class ToastUtil {
    /**
     * 文字
     *
     * @param message 内容
     */
    public static void makeText(Context context, String message) {
        makeText(context, message, 0, 0, 0);
    }

    /***
     * 带图片的
     *
     * @param message
     * @param resId
     */
    public static void makeImaText(Context context, String message, int resId) {
        makeText(context, message, resId, 0, Gravity.CENTER);
    }

    /**
     * 带图片，时长
     *
     * @param message  信息
     * @param resId    图片ID
     * @param duration 时长
     */
    public static void makeImaText(Context context, String message, int resId, int duration) {
        makeText(context, message, resId, duration, Gravity.CENTER);
    }


    /**
     * 自定义Toast
     *
     * @param message  信息
     * @param resId    图片的id
     * @param duration 时长
     * @param position 位置
     */
    private static void makeText(Context context, String message, int resId, int duration, int position) {
        Toast toast = Toast.makeText(context, message, LENGTH_LONG);
        if (position != 0) {
            toast.setGravity(position, 0, 0);
        }
        LinearLayout toastView = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;

        ImageView bg = new ImageView(context);
        if (resId != 0) {
            bg.setImageResource(resId);
        }
        toastView.removeAllViews();
        toastView.addView(toastView, params);
        if (duration != 0) {
            toast.setDuration(duration);
        }
        toast.show();
    }
}
