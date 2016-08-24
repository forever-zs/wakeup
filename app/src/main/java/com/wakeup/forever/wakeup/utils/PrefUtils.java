package com.wakeup.forever.wakeup.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Wesly186 on 2016/8/17.
 */
public class PrefUtils {

    private static final String PREF_NAME = "config";


    public static boolean getBoolean(Context ctx, String key, boolean defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defValue);
        return value;
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value).commit();
    }

}