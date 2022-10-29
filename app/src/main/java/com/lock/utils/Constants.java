package com.lock.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;


import com.lock.services.MAccessibilityService;
import com.dynamic.island.harsha.notification.R;
import pub.devrel.easypermissions.EasyPermissions;

public class Constants {
    public static final String APP_ICON_URL = "http://centsolapps.com/api/AppThemes/2021_app/computer_launcher/small_image/";
    public static final String APP_IMG_URL = "http://centsolapps.com/api/AppThemes/2021_app/computer_launcher/big_image/";
    private static final String AUTO_CLOSE_NOTI = "AUTO_CLOSE_NOTI";
    public static final String BACKGROUND_TIME = "BACKGROUND_TIME";
    public static String[] BLUETOOTH_PERMISSION = {"android.permission.BLUETOOTH_CONNECT"};
    public static final int BLUETOOTH_PERMISSION_REQUEST = 16;
    public static final String CAMERA_PKG = "CAMERA_PKG";
    public static final String CONTROL_GESTURE = "CONTROL_GESTURE";
    public static final String CREDENTIALS = "ali:uraan1234";
    public static final int DrawOverlay_REQUEST_CODE = 9;
    public static final String GETTING_APP = "http://centsolapps.com/api/AppThemes/2021_app/computer_launcher/get_computer_launcher_app.php";
    public static final String GETTING_LOCK = "http://centsolapps.com/api/AppThemes/2021_lock/compuer_launcher/get_computer_launcher_lock.php";
    public static final String GETTING_THEME = "http://centsolapps.com/api/AppThemes/2021_themes/computer_launcher/get_computer_launcher_themes.php";
    private static final String IS_FIRST_TIME = "IS_FIRST_TIME";
    public static final String LOCK_ICON_URL = "http://centsolapps.com/api/AppThemes/2021_lock/compuer_launcher/small_image/";
    public static final String LOCK_IMG_URL = "http://centsolapps.com/api/AppThemes/2021_lock/compuer_launcher/big_image/";
    public static final String LOCK_SCREEN = "com.system.request.lock.screen";
    public static final int MAX_RETRY = 2;
    public static final int MY_SOCKET_TIMEOUT_MS = 10000;
    public static final String NOTIFICATION = "NOTIFICATION";
    public static final String NOTIFICATION_GESTURE = "NOTIFICATION_GESTURE";
    public static final String ONETIME = "ONETIME";
    public static final String PAGE_COUNT = "page_count";
    public static final String PHONE_PKG = "PHONE_PKG";
    public static String[] PHONE_STATE_PERMISSION = {"android.permission.READ_PHONE_STATE"};
    public static final String POWER_DIALOG = "com.system.request.power.dialog";
    public static final String RATING_DIALOUGE_ONETIME = "RATING_DIALOUGE_ONETIME";
    public static final int RC_LOCATION_PERMISSION_REQUEST = 10;
    public static final int RC_PHONE_STATE_PERMISSION_REQUEST = 15;
    public static final String SCREEN_SHOT = "com.system.request.screen.shot";
    public static final String SHOW_IN_FULL_SCREEN = "SHOW_IN_FULL_SCREEN";
    public static final String SHOW_IN_LOCK = "SHOW_IN_LOCK";
    public static final int STORAGE_PERMISSION_REQUEST = 2252;
    public static final String TAG = "LOCKSCREEN";
    public static final String THEME_ICON_URL = "http://centsolapps.com/api/AppThemes/2021_themes/computer_launcher/small_image/";
    public static final String THEME_IMG_URL = "http://centsolapps.com/api/AppThemes/2021_themes/computer_launcher/big_image/";
    public static final String TILE_I_PKG = "TILE_I_PKG";
    public static final String TILE_J_PKG = "TILE_J_PKG";
    public static final String TILE_K_PKG = "TILE_K_PKG";
    public static final String TILE_L_PKG = "TILE_L_PKG";
    public static final String TYPE_AIRBUDS = "TYPE_AIRBUDS";
    public static final String TYPE_CHARGING = "CAT_CHARGING";
    public static final String TYPE_SILENT = "CAT_SILENT";
    public static final String USE_MATERIAL_COLOR = "USE_MATERIAL_COLOR";
    private static final String WALLPAPER_COLOR = "WALLPAPER_COLOR";
    public static final String WALLPAPER_UPDATED = "WALLPAPER_UPDATED";
    public static final int WRITE_SETTINGS_REQUEST = 11;

    public static SharedPreferences setNotif(Context context, boolean z) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putBoolean(NOTIFICATION, z).apply();
        return defaultSharedPreferences;
    }

    public static boolean getNotif(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(NOTIFICATION, false);
    }

    public static SharedPreferences setControlEnabled(Context context, boolean z) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        defaultSharedPreferences.edit().putBoolean(CONTROL_GESTURE, z).apply();
        return defaultSharedPreferences;
    }

    public static boolean getControlEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(CONTROL_GESTURE, true);
    }

    public static void setAutoOrientationEnabled(Context context, boolean z) {
        Settings.System.putInt(context.getContentResolver(), "accelerometer_rotation", z ? 1 : 0);
    }

    public static void setCameraPkg(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(CAMERA_PKG, str).apply();
    }

    public static void setPhonePkg(Context context, String str) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PHONE_PKG, str).apply();
    }

    public static String getPhonePkg(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PHONE_PKG, "");
    }

    public static boolean isRotationOn(Context context) {
        if (Settings.System.getInt(context.getContentResolver(), "accelerometer_rotation", 0) == 1) {
            return true;
        }
        return false;
    }

    public static String getCameraPkg(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(CAMERA_PKG, "");
    }

    public static boolean checkSystemWritePermission(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Settings.System.canWrite(context);
        }
        return true;
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String[] strArr = {"_data"};
        try {
            Cursor query = context.getContentResolver().query(uri, strArr, (String) null, (String[]) null, (String) null);
            if (query == null) {
                return null;
            }
            query.moveToFirst();
            String string = query.getString(query.getColumnIndex(strArr[0]));
            query.close();
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap drawableToBmp(Context context, Drawable drawable) {
        Bitmap bitmap;
        int convertDpToPixel = (int) convertDpToPixel(20.0f, context);
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(convertDpToPixel, convertDpToPixel, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static float convertDpToPixel(float f, Context context) {
        Resources resources;
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return f * (((float) resources.getDisplayMetrics().densityDpi) / 160.0f);
    }

    public static boolean getEnable(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(ONETIME, true);
    }

    public static void setEnable(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(ONETIME, z).apply();
    }

    public static boolean isAppInstalled(Context context, String str) {
        try {
            context.getPackageManager().getApplicationInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean hasPermissions(Context context, String... strArr) {
        return EasyPermissions.hasPermissions(context, strArr);
    }

    public static Bitmap drawableToBmp(Activity activity, Drawable drawable, int i) {
        Bitmap bitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_4444);
        } else {
            int convertDpToPixel = (int) convertDpToPixel((float) i, activity);
            bitmap = Bitmap.createBitmap(convertDpToPixel, convertDpToPixel, Bitmap.Config.ARGB_4444);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static boolean isUseMaterialColor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(USE_MATERIAL_COLOR, false);
    }

    public static boolean setUseMaterialColor(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(USE_MATERIAL_COLOR, z).apply();
        return z;
    }

    public static boolean isWallpaperUpdated(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(WALLPAPER_UPDATED, false);
    }

    public static boolean isFirstTimeLoad(Context context) {
        boolean z = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(IS_FIRST_TIME, true);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(IS_FIRST_TIME, false).apply();
        return z;
    }

    public static void setWallpaperUpdated(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(WALLPAPER_UPDATED, z).apply();
    }

    public static int getWallpaperColor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(WALLPAPER_COLOR, context.getResources().getColor(R.color.selected));
    }

    public static void setWallpaperColor(Context context, int i) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(WALLPAPER_COLOR, i).apply();
    }

    public static boolean getRatingDailoge(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(RATING_DIALOUGE_ONETIME, true);
    }

    public static void setRatingDailoge(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(RATING_DIALOUGE_ONETIME, z).apply();
    }

    public static boolean checkAccessibilityEnabled(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            return false;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        simpleStringSplitter.setString(string);
        while (simpleStringSplitter.hasNext()) {
            if (simpleStringSplitter.next().equalsIgnoreCase(context.getPackageName() + "/" + MAccessibilityService.class.getName())) {
                return true;
            }
        }
        return false;
    }

    public static String getTileIPackage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TILE_I_PKG, "com.google.android.gm");
    }

    public static String getTileJPackage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TILE_J_PKG, "com.facebook.katana");
    }

    public static String getTileKPackage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TILE_K_PKG, "com.zhiliaoapp.musically");
    }

    public static String getTileLPackage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(TILE_L_PKG, "com.twitter.android");
    }

    public static boolean getShowOnLock(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SHOW_IN_LOCK, true);
    }

    public static void setShowOnLock(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(SHOW_IN_LOCK, z).apply();
    }

    public static boolean getShowInFullScreen(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SHOW_IN_FULL_SCREEN, false);
    }

    public static void SetShowInFullScreen(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(SHOW_IN_FULL_SCREEN, z).apply();
    }

    public static boolean getAutoCloseNoti(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(AUTO_CLOSE_NOTI, false);
    }

    public static void setAutoCloseNoti(Context context, boolean z) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(AUTO_CLOSE_NOTI, z).apply();
    }
}
