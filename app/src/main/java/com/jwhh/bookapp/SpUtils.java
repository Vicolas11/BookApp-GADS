package com.jwhh.bookapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

    private SpUtils() {}

    public static final String PREF_NAME = "BooksPreference";
    public static final String POSITION = "position";
    public static final String QUERY = "query";

    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static String getSharedPreferencesString(Context context, String key) {
        return getPref(context).getString(key, "");
    }

    public static int getSharedPreferencesInt(Context context, String key) {
        return getPref(context).getInt(key, 0);
    }

    public static void setSharedPreferencesString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setSharedPreferencesInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
