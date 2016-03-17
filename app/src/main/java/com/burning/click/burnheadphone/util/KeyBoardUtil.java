package com.burning.click.burnheadphone.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/** 打开关闭键盘
 * Created by click on 16-3-10.
 */
public class KeyBoardUtil {

    public static void hideKeyBoard(Context context,View focus){
        if (focus!=null){
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }

    public static  void getKeyBoard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
