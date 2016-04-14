package com.burning.click.burnheadphone.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * SharedPreferences  工具类
 * Created by click on 16-4-14.
 */
public class SpUtils {

    public static String BHP_SHARF = "bhp_sharf";

    private static String NO_KEY_EXCEPTION = "Key 不存在";

    private static SharedPreferences sharedPreferences = null;

    private static SharedPreferences getSharePreference(Context context, String storeFile) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(storeFile, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    /**
     * 获取string
     *
     * @param context   上下文
     * @param storeFile 文件名
     * @param key       key
     * @return 相应key的值，
     */
    public static String getString(Context context, String storeFile, String key) {
        return getString(context, storeFile, key, "");
    }

    public static String getString(Context context, String storeFile, String key, String value) {
        if (null == storeFile) {
            storeFile = BHP_SHARF;
        }
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        if (!contain(context, BHP_SHARF, key)) {
            new Exception(NO_KEY_EXCEPTION).printStackTrace();
        }
        return sharedPreferences.getString(key, value);
    }

    private static boolean contain(Context context, String storeFile, String key) {
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        return sharedPreferences.contains(key);
    }


    public static int getInt(Context context, String storeFile, String key, int value) {
        if (TextUtils.isEmpty(storeFile)) {
            storeFile = BHP_SHARF;
        }
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        if (!sharedPreferences.contains(key)) {
            new Exception(NO_KEY_EXCEPTION).printStackTrace();
        }
        return sharedPreferences.getInt(key, value);

    }

    public static boolean getBoolean(Context context, String storeFile, String key, boolean value) {
        if (TextUtils.isEmpty(storeFile)) {
            storeFile = BHP_SHARF;
        }
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        if (!sharedPreferences.contains(key)) {
            new Exception(NO_KEY_EXCEPTION).printStackTrace();
        }
        return sharedPreferences.getBoolean(key, value);
    }


    public static float getFloat(Context context, String storeFile, String key, float value) {
        if (TextUtils.isEmpty(storeFile)) {
            storeFile = BHP_SHARF;
        }
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        if (!sharedPreferences.contains(key)) {
            new Exception(NO_KEY_EXCEPTION).printStackTrace();
        }
        return sharedPreferences.getFloat(key, value);
    }

    public static boolean putString(Context context, String storeFile, String key, String value) {
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        // 可以使用apply 当有多个 put的时候，apply只是提交到内存，后续的操作会覆盖
        return editor.commit();
    }

    public static boolean putInt(Context context, String storeFile, String key, int value) {
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        // 可以使用apply 当有多个 put的时候，apply只是提交到内存，后续的操作会覆盖
        return editor.commit();
    }

    public static boolean putBoolean(Context context, String storeFile, String key, boolean value) {
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        // 可以使用apply 当有多个 put的时候，apply只是提交到内存，后续的操作会覆盖
        return editor.commit();
    }

    public static boolean putFloat(Context context, String storeFile, String key, float value) {
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        // 可以使用apply 当有多个 put的时候，apply只是提交到内存，后续的操作会覆盖
        return editor.commit();
    }

    public static boolean putLong(Context context, String storeFile, String key, long value) {
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        // 可以使用apply 当有多个 put的时候，apply只是提交到内存，后续的操作会覆盖
        return editor.commit();
    }


    public static boolean remove(Context context, String storeFile, String key) {
        if (TextUtils.isEmpty(storeFile)) {
            storeFile = BHP_SHARF;
        }
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean removeAll(Context context, String storeFile) {
        if (TextUtils.isEmpty(storeFile)) {
            storeFile = BHP_SHARF;
        }
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }


}
