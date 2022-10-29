package com.lock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MySettings {
    public static final String USING = "USING";

    public static boolean getLockscreen(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("Lockscreen", false);
    }

    public static void setLockscreen(boolean z, Context context) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("Lockscreen", z);
        edit.commit();
    }

    public static SharedPreferences setDemo(Context context, boolean z) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putBoolean(USING, z).apply();
        return defaultSharedPreferences;
    }

    public static boolean getDemo(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(USING, false);
    }
}
