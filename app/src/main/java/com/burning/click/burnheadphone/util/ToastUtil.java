package com.burning.click.burnheadphone.util;

import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.burning.click.burnheadphone.BHPApplication;

import static android.widget.Toast.LENGTH_LONG;

/** 显示类
 * Created by click on 16-3-10.
 */
public class ToastUtil {
    /**
     * 文字
     * @param message 内容
     */
    public static  void makeText(String message){
        ToastUtil.makeText(message,0,0,Gravity.CENTER);
    }

    /***
     * 带图片的
     * @param message
     * @param resId
     */
    public static  void makeImaText(String message,int resId){
        makeText(message,resId,0,Gravity.CENTER);
    }

    /**
     * 带图片，时长
     * @param message 信息
     * @param resId 图片ID
     * @param duration 时长
     */
    public static  void makeImaText(String message,int resId,int duration){
        makeText(message,resId,duration,Gravity.CENTER);
    }


    /**
     *自定义Toast
     * @param message 信息
     * @param resId 图片的id
     * @param duration 时长
     * @param position 位置
     */
    private static void makeText(String message ,int resId,int duration,int position){
        Toast toast =Toast.makeText(BHPApplication.mApplication,message, LENGTH_LONG);
        if (position!=0){
            toast.setGravity(position,0,0);
        }
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView bg= new ImageView(BHPApplication.mApplication);
        if (resId!=0){
            bg.setImageResource(resId);
        }
        toastView.addView(toastView);
        if (duration==0){
            toast.setDuration(Toast.LENGTH_SHORT);
        }else {
            toast.setDuration(duration);
        }
        toast.show();
    }
}
