package com.lock.background;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.lock.entity.AppPackageList;

public class PrefManager {
    public static final String DEFAULT_SELECTED_COLOR = "#ffffff";
    public static final String CAM_COUNT = "CAM_COUNT";
    public static final String CAM_POS = "CAM_POS";
    private static final String DATA_DEFAULT_COLOR = "DATA_DEFAULT_COLOR";
    public static final String FILTER_PKG = "FILTER_PKG";
    public static final String IPHONE_CALL = "IPHONE_CALL";
    private static final String KEY_ALBUMS = "albums";
    public static final String KEY_DEFAULT_COLOR = "default_color";
    private static final String KEY_GALLERY_NAME = "gallery_name";
    private static final String KEY_GOOGLE_USERNAME = "google_username";
    private static final String KEY_NO_OF_COLUMNS = "no_of_columns";
    private static final String KEY_NO_OF_COLUMNS_CATEGORY = "no_of_columns_category";
    private static final String PREF_NAME = "AwesomeWallpapers";
    public static final String SELECTED_PKG = "SELECTED_PKG";
    private static final String TAG = "PrefManager";
    private static final String TORCH_DEFAULT_COLOR = "TORCH_DEFAULT_COLOR";
    private static final String WIFI_DEFAULT_COLOR = "WIFI_DEFAULT_COLOR";
    public static final String Y_HEIGHT = "Y_HEIGHT";
    public static final String Y_POS = "Y_POS";
    int PRIVATE_MODE = 0;
    Context _context;
    SharedPreferences.Editor editor;
    SharedPreferences pref;

    public PrefManager(Context context) {
        this._context = context;
        this.pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setCallPkg(Context context, AppPackageList appPackageList) {
        SharedPreferences.Editor edit = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
        edit.putString(SELECTED_PKG, new Gson().toJson((Object) appPackageList));
        edit.apply();
    }

    public static AppPackageList getCallPkg(Context context) {
        return (AppPackageList) new Gson().fromJson(androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(SELECTED_PKG, ""), AppPackageList.class);
    }

    public static void setFilterPkg(Context context, AppPackageList appPackageList) {
        SharedPreferences.Editor edit = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
        edit.putString(FILTER_PKG, new Gson().toJson((Object) appPackageList));
        edit.apply();
    }

    public static AppPackageList getSelectedFilterPkg(Context context) {
        return (AppPackageList) new Gson().fromJson(androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(FILTER_PKG, ""), AppPackageList.class);
    }

    public void setKeyDefaultColor(int i) {
        this.pref.edit().putInt(KEY_DEFAULT_COLOR, i).apply();
    }

    public int getDefaultColor() {
        return this.pref.getInt(KEY_DEFAULT_COLOR, Color.parseColor(DEFAULT_SELECTED_COLOR));
    }

    public void setWifiDefaultColor(int i) {
        this.pref.edit().putInt(WIFI_DEFAULT_COLOR, i).apply();
    }

    public int getWifiDefaultColor() {
        return this.pref.getInt(WIFI_DEFAULT_COLOR, Color.parseColor("#1690fe"));
    }

    public void setTorchDefaultColor(int i) {
        this.pref.edit().putInt(TORCH_DEFAULT_COLOR, i).apply();
    }

    public int getTorchDefaultColor() {
        return this.pref.getInt(TORCH_DEFAULT_COLOR, Color.parseColor("#FF9A1E"));
    }

    public void setDataDefaultColor(int i) {
        this.pref.edit().putInt(DATA_DEFAULT_COLOR, i).apply();
    }

    public int getDataDefaultColor() {
        return this.pref.getInt(DATA_DEFAULT_COLOR, Color.parseColor("#04CB69"));
    }

    public int getNoOfGridColumns() {
        return this.pref.getInt(KEY_NO_OF_COLUMNS, 3);
    }

    public int getNoOfGridColumnsCategory() {
        return this.pref.getInt(KEY_NO_OF_COLUMNS_CATEGORY, 3);
    }

    public String getGalleryName() {
        return this.pref.getString(KEY_GALLERY_NAME, AppConst.SDCARD_DIR_NAME);
    }

    public void setCameraCount(int i) {
        this.pref.edit().putInt(CAM_COUNT, i).apply();
    }

    public int getCameraCount() {
        return this.pref.getInt(CAM_COUNT, 1);
    }

    public void setCameraPos(int i) {
        this.pref.edit().putInt(CAM_POS, i).apply();
    }

    public int getCameraPos() {
        return this.pref.getInt(CAM_POS, 2);
    }

    public void setYPosOfIsland(int i) {
        this.pref.edit().putInt(Y_POS, i).apply();
    }

    public int getYPosOfIsland() {
        return this.pref.getInt(Y_POS, 5);
    }

    public void setHeightOfIsland(int i) {
        this.pref.edit().putInt(Y_HEIGHT, i).apply();
    }

    public int getHeightOfIsland() {
        return this.pref.getInt(Y_HEIGHT, 30);
    }

    public boolean getIphoneCall(Context context) {
        return this.pref.getBoolean(IPHONE_CALL, false);
    }

    public void setIphoneCall(Context context, boolean z) {
        this.pref.edit().putBoolean(IPHONE_CALL, z).apply();
    }
}
