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

    public static boolean put(Context context, String storeFile, String key, Object value) {
        if (null == value) {
            return false;
        }
        SharedPreferences sharedPreferences = SpUtils.getSharePreference(context, storeFile);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
            return editor.commit();
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
            return editor.commit();
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
            return editor.commit();
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
            return editor.commit();
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
            return editor.commit();
        }
        return false;
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
