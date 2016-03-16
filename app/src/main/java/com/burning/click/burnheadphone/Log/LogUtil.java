package com.burning.click.burnheadphone.Log;

import android.util.Log;

import com.burning.click.burnheadphone.BHPApplication;

/**
 * Created by click on 16-3-14.
 */
public class LogUtil {

    public static void d(String msg){
        printLog("###==",msg);

    }

    public static void d(int num){
        printLog("####==",num+"");
    }

    public static void d(String tag ,String msg){
        printLog(tag,msg);
    }
    private static void  printLog(String tag, String msg){
        if (BHPApplication.DEBUG){
            Log.d(tag, msg);
        }
    }

}
