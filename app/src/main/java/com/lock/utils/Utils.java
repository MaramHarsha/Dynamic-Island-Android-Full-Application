package com.lock.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.constraintlayout.motion.widget.Key;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.lock.Controllers.AirPlaneModeController;
import com.lock.Controllers.AlarmsController;
import com.lock.Controllers.AudioSettingController;
import com.lock.Controllers.BatterySaverController;
import com.lock.Controllers.BlueToothSettingController;
import com.lock.Controllers.BrightnessModeController;
import com.lock.Controllers.DarkModeController;
import com.lock.Controllers.DataSyncController;
import com.lock.Controllers.DataUsageController;
import com.lock.Controllers.DisplaySettingIntent;
import com.lock.Controllers.ExpandNotificationPanel;
import com.lock.Controllers.HotSpotController;
import com.lock.Controllers.KeyBoardController;
import com.lock.Controllers.LocationSettingController;
import com.lock.Controllers.LockScreenController;
import com.lock.Controllers.MobileDataController;
import com.lock.Controllers.NFCController;
import com.lock.Controllers.NightModeController;
import com.lock.Controllers.ScreenCastController;
import com.lock.Controllers.ScreenRecordController;
import com.lock.Controllers.ScreenShotController;
import com.lock.Controllers.ScreenTimeController;
import com.lock.Controllers.ScreenTimoutController;
import com.lock.Controllers.SimSelectController;
import com.lock.Controllers.StillCameraController;
import com.lock.Controllers.TorchMainController;
import com.lock.Controllers.WifiController;
import com.lock.Controllers.ZenModeController;
import com.lock.background.PrefManager;
import com.lock.entity.AppDetail;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.CharCompanionObject;

public class Utils {
    public static final String COLOR_UPDATE = "COLOR_UPDATE";
    private static final int DAY_MILLIS = 86400000;
    public static final String FROM_NOTIFICATION_SERVICE = "from_notification_service";
    private static final int HOUR_MILLIS = 3600000;
    private static final int MINUTE_MILLIS = 60000;
    private static final int SECOND_MILLIS = 1000;
    public Context context;
    boolean isLocationEnabled = false;
    private int titleTextColor;

    public void setLottiViewState(LottieAnimationView lottieAnimationView, String str, String str2, boolean z) {
    }

    public Utils(Context context2) {
        this.context = context2;
        this.titleTextColor = new PrefManager(context2).getDefaultColor();
    }

    public int getTitleTextColor() {
        return this.titleTextColor;
    }

    public void startAppNotificationSetting(Context context2) {
        Intent intent = new Intent("android.settings.ALL_APPS_NOTIFICATION_SETTINGS");
        if (context2.getPackageManager().resolveActivity(intent, 0) == null) {
            intent.setAction("android.settings.APPLICATION_SETTINGS");
        }
        intent.setFlags(268435456);
        try {
            this.context.startActivity(intent);
        } catch (Exception unused) {
            Toast.makeText(context2, "Setting not found!", 1).show();
        }
    }

    public int getTileColor() {
        return this.titleTextColor;
    }

    public void applyRoundCorner(View view) {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(new ShapeAppearanceModel().toBuilder().setAllCorners(0, convertDpToPixel(10.0f, this.context)).build());
        materialShapeDrawable.setFillColor(ContextCompat.getColorStateList(this.context, R.color.transparentFullWhite));
        ViewCompat.setBackground(view, materialShapeDrawable);
    }

    public void setTileColor(int i) {
        this.titleTextColor = i;
    }

    public boolean isSupportHardwareCamera(Context context2) {
        return context2.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }

    public static boolean checkIfActivityFound(Context context2, Intent intent) {
        return context2.getPackageManager().resolveActivity(intent, 0) != null;
    }

    public ArrayList<ButtonState> getButtonsList() {
        ArrayList arrayList = new ArrayList(Arrays.asList((this.context.getString(R.string.quick_settings_tiles_default) + this.context.getResources().getString(R.string.quick_settings_tiles_more)).split(",")));
        ArrayList<ButtonState> arrayList2 = new ArrayList<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ButtonState buttonSate = getButtonSate((String) it.next());
            if (buttonSate != null) {
                arrayList2.add(buttonSate);
            }
        }
        return arrayList2;
    }

    public ButtonState getButtonSate(String str) {
        str.hashCode();
        int hashCode = str.hashCode();
        char c = CharCompanionObject.MAX_VALUE;
        switch (hashCode) {
            case -1367751899:
                if (str.equals("camera")) {
                    c = 0;
                    break;
                }
                break;
            case -1313911455:
                if (str.equals("timeout")) {
                    c = 1;
                    break;
                }
                break;
            case -1183073498:
                if (str.equals("flashlight")) {
                    c = 2;
                    break;
                }
                break;
            case -930895139:
                if (str.equals("ringer")) {
                    c = 3;
                    break;
                }
                break;
            case -907689876:
                if (str.equals("screen")) {
                    c = 4;
                    break;
                }
                break;
            case -677011630:
                if (str.equals("airplane")) {
                    c = 5;
                    break;
                }
                break;
            case -331239923:
                if (str.equals("battery")) {
                    c = 6;
                    break;
                }
                break;
            case -40300674:
                if (str.equals(Key.ROTATION)) {
                    c = 7;
                    break;
                }
                break;
            case 3154:
                if (str.equals("bt")) {
                    c = 8;
                    break;
                }
                break;
            case 3680:
                if (str.equals("ss")) {
                    c = 9;
                    break;
                }
                break;
            case 99610:
                if (str.equals("dnd")) {
                    c = 10;
                    break;
                }
                break;
            case 108971:
                if (str.equals("nfc")) {
                    c = 11;
                    break;
                }
                break;
            case 112784:
                if (str.equals("rec")) {
                    c = 12;
                    break;
                }
                break;
            case 113879:
                if (str.equals("sim")) {
                    c = 13;
                    break;
                }
                break;
            case 3046207:
                if (str.equals("cast")) {
                    c = 14;
                    break;
                }
                break;
            case 3049826:
                if (str.equals("cell")) {
                    c = 15;
                    break;
                }
                break;
            case 3075958:
                if (str.equals("dark")) {
                    c = 16;
                    break;
                }
                break;
            case 3327275:
                if (str.equals("lock")) {
                    c = 17;
                    break;
                }
                break;
            case 3545755:
                if (str.equals("sync")) {
                    c = 18;
                    break;
                }
                break;
            case 3649301:
                if (str.equals("wifi")) {
                    c = 19;
                    break;
                }
                break;
            case 94755854:
                if (str.equals("clock")) {
                    c = 20;
                    break;
                }
                break;
            case 104817688:
                if (str.equals("night")) {
                    c = 21;
                    break;
                }
                break;
            case 109211285:
                if (str.equals("saver")) {
                    c = 22;
                    break;
                }
                break;
            case 109770518:
                if (str.equals("stock")) {
                    c = 23;
                    break;
                }
                break;
            case 503739367:
                if (str.equals("keyboard")) {
                    c = 24;
                    break;
                }
                break;
            case 648162385:
                if (str.equals("brightness")) {
                    c = 25;
                    break;
                }
                break;
            case 1099603663:
                if (str.equals("hotspot")) {
                    c = 26;
                    break;
                }
                break;
            case 1901043637:
                if (str.equals("location")) {
                    c = 27;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return new StillCameraController(this.context);
            case 1:
                return new ScreenTimoutController(this.context);
            case 2:
                return new TorchMainController(this.context);
            case 3:
                return new AudioSettingController(this.context);
            case 4:
                return new ScreenTimeController(this.context);
            case 5:
                return new AirPlaneModeController(this.context);
            case 6:
                return new BatterySaverController(this.context);
            case 7:
                return new DisplaySettingIntent(this.context);
            case 8:
                return new BlueToothSettingController(this.context);
            case 9:
                return new ScreenRecordController(this.context);
            case 10:
                return new ZenModeController(this.context);
            case 11:
                return new NFCController(this.context);
            case 12:
                return new ScreenShotController(this.context);
            case 13:
                try {
                    SubscriptionManager subscriptionManager = (SubscriptionManager) this.context.getSystemService("telephony_subscription_service");
                    if (ActivityCompat.checkSelfPermission(this.context, "android.permission.READ_PHONE_STATE") == 0 && subscriptionManager.getActiveSubscriptionInfoList().size() == 2) {
                        return new SimSelectController(this.context, subscriptionManager);
                    }
                } catch (Throwable unused) {
                }
                return null;
            case 14:
                return new ScreenCastController(this.context);
            case 15:
                return new MobileDataController(this.context);
            case 16:
                return new DarkModeController(this.context);
            case 17:
                if (Build.VERSION.SDK_INT < 28) {
                    return null;
                }
                return new LockScreenController(this.context);
            case 18:
                return new DataSyncController(this.context);
            case 19:
                return new WifiController(this.context);
            case 20:
                return new AlarmsController(this.context);
            case 21:
                return new NightModeController(this.context);
            case 22:
                if (Build.VERSION.SDK_INT < 24) {
                    return null;
                }
                return new DataUsageController(this.context);
            case 23:
                return new ExpandNotificationPanel(this.context);
            case 24:
                return new KeyBoardController(this.context);
            case 25:
                return new BrightnessModeController(this.context);
            case 26:
                return new HotSpotController(this.context);
            case 27:
                return new LocationSettingController(this.context);
            default:
                return null;
        }
    }

    public void setBatteryImage(LottieAnimationView lottieAnimationView, boolean z) {
        if (!z) {
            lottieAnimationView.setMinAndMaxFrame("" + (getBatteryLevel() / 10));
            setLottieAnimColor(lottieAnimationView, this.context.getResources().getColor(R.color.on_text));
            return;
        }
        lottieAnimationView.setMinAndMaxFrame("charging");
        setLottieAnimColor(lottieAnimationView, this.context.getResources().getColor(R.color.on_text));
    }

    public int getAirpodsBattery() {
        int airPodLevel = getAirPodLevel();
        if (airPodLevel == -1) {
            return -1;
        }
        int i = airPodLevel / 10;
        int i2 = R.drawable.airbug_00;
        if (i == 1) {
            i2 = R.drawable.airbug_01;
        }
        if (i == 2) {
            i2 = R.drawable.airbug_02;
        }
        if (i == 3) {
            i2 = R.drawable.airbug_03;
        }
        if (i == 4) {
            i2 = R.drawable.airbug_04;
        }
        if (i == 5) {
            i2 = R.drawable.airbug_05;
        }
        if (i == 6) {
            i2 = R.drawable.airbug_06;
        }
        if (i == 7) {
            i2 = R.drawable.airbug_07;
        }
        if (i == 8) {
            i2 = R.drawable.airbug_08;
        }
        if (i == 9) {
            i2 = R.drawable.airbug_09;
        }
        return i == 10 ? R.drawable.airbug_10 : i2;
    }

    public int getBatteryImage() {
        int batteryLevel = getBatteryLevel() / 10;
        int i = R.drawable.battery_00;
        if (batteryLevel == 1) {
            i = R.drawable.battery_01;
        }
        if (batteryLevel == 2) {
            i = R.drawable.battery_02;
        }
        if (batteryLevel == 3) {
            i = R.drawable.battery_03;
        }
        if (batteryLevel == 4) {
            i = R.drawable.battery_04;
        }
        if (batteryLevel == 5) {
            i = R.drawable.battery_05;
        }
        if (batteryLevel == 6) {
            i = R.drawable.battery_06;
        }
        if (batteryLevel == 7) {
            i = R.drawable.battery_07;
        }
        if (batteryLevel == 8) {
            i = R.drawable.battery_08;
        }
        if (batteryLevel == 9) {
            i = R.drawable.battery_09;
        }
        return batteryLevel == 10 ? R.drawable.battery_10 : i;
    }

    public int getAirPodLevel() {
        try {
            if (BluetoothAdapter.getDefaultAdapter() == null || ActivityCompat.checkSelfPermission(this.context, "android.permission.BLUETOOTH_CONNECT") != 0) {
                return -1;
            }
            int i = -1;
            for (BluetoothDevice address : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
                try {
                    BluetoothDevice remoteDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address.getAddress());
                    i = ((Integer) remoteDevice.getClass().getMethod("getBatteryLevel", new Class[0]).invoke(remoteDevice, new Object[0])).intValue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return i;
        } catch (Exception unused) {
        }
        return -1;
    }

    public int getBatteryLevel() {
        return ((BatteryManager) this.context.getSystemService("batterymanager")).getIntProperty(4);
    }

    public int getHeight(Context context2) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context2.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public int getWidth(Context context2) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context2.getSystemService("window")).getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public float convertDpToPixel(float f, Context context2) {
        return f * (((float) context2.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public float convertPixelsToDp(float f, Context context2) {
        return f / (((float) context2.getResources().getDisplayMetrics().densityDpi) / 160.0f);
    }

    public int getBrightMode(Context context2) {
        try {
            return Settings.System.getInt(context2.getContentResolver(), "screen_brightness_mode");
        } catch (Exception e) {
            Log.d("tag", e.toString());
            return 0;
        }
    }

    public boolean isWifiOn(Context context2) {
        WifiManager wifiManager = (WifiManager) context2.getApplicationContext().getSystemService("wifi");
        try {
            Method declaredMethod = Class.forName(wifiManager.getClass().getName()).getDeclaredMethod("isWifiEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(wifiManager, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isHotspotOn(Context context2) {
        WifiManager wifiManager = (WifiManager) context2.getApplicationContext().getSystemService("wifi");
        try {
            Method declaredMethod = Class.forName(wifiManager.getClass().getName()).getDeclaredMethod("getWifiApState", new Class[0]);
            declaredMethod.setAccessible(true);
            Object[] objArr = null;
            int intValue = ((Integer) declaredMethod.invoke(wifiManager, (Object[]) null)).intValue();
            if (intValue != 11 && intValue == 13) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getBoxBrightness(AppCompatSeekBar appCompatSeekBar, Context context2) {
        float f;
        try {
            f = (float) Settings.System.getInt(context2.getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            f = 0.0f;
        }
        appCompatSeekBar.setProgress((int) f);
    }

    public void setBrightness(int i, Context context2) {
        if (getBrightMode(context2) == 1) {
            Settings.System.putInt(context2.getContentResolver(), "screen_brightness_mode", 0);
        }
        Settings.System.putInt(context2.getContentResolver(), "screen_brightness", i);
    }

    public void modifyAirplanemode(Context context2) {
        try {
            Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
            intent.setFlags(268435456);
            context2.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.e("exception", e + "");
        }
    }

    public void playRingTone() {
        RingtoneManager.getRingtone(this.context, RingtoneManager.getActualDefaultRingtoneUri(this.context.getApplicationContext(), 1)).play();
    }

    public void stopRingTone() {
        RingtoneManager.getRingtone(this.context, RingtoneManager.getActualDefaultRingtoneUri(this.context.getApplicationContext(), 1)).play();
    }

    public int getDndState() {
        AudioManager audioManager = (AudioManager) this.context.getSystemService("audio");
        if (audioManager != null) {
            return audioManager.getRingerMode();
        }
        return 0;
    }

    public void changeSoundMode(Context context2, boolean z, LottieAnimationView lottieAnimationView) {
        TextView textView = (TextView) ((RelativeLayout) lottieAnimationView.getParent()).getChildAt(1);
        AudioManager audioManager = (AudioManager) context2.getSystemService("audio");
        int ringerMode = audioManager != null ? audioManager.getRingerMode() : 0;
        Vibrator vibrator = (Vibrator) context2.getSystemService("vibrator");
        boolean hasVibrator = vibrator != null ? vibrator.hasVibrator() : false;
        if (audioManager == null) {
            return;
        }
        if (hasVibrator) {
            if (ringerMode == 2) {
                if (z) {
                    audioManager.setRingerMode(0);
                    textView.setText("Mute");
                    setLottiViewState(lottieAnimationView, "sound", "mute", false);
                    return;
                }
                textView.setText("Sound");
                setLottiViewState(lottieAnimationView, "start", "sound", true);
            } else if (ringerMode == 0) {
                if (z) {
                    audioManager.setRingerMode(1);
                    textView.setText("Vibrate");
                    setLottiViewState(lottieAnimationView, "mute", "viberate", false);
                    return;
                }
                textView.setText("Mute");
                setLottiViewState(lottieAnimationView, "sound", "mute", false);
            } else if (ringerMode != 1) {
            } else {
                if (z) {
                    audioManager.setRingerMode(2);
                    textView.setText("Sound");
                    setLottiViewState(lottieAnimationView, "start", "sound", true);
                    return;
                }
                textView.setText("Vibrate");
                setLottiViewState(lottieAnimationView, "mute", "viberate", false);
            }
        } else if (ringerMode == 2) {
            if (z) {
                audioManager.setRingerMode(0);
                textView.setText("Mute");
                setLottiViewState(lottieAnimationView, "sound", "mute", false);
                return;
            }
            textView.setText("Sound");
            setLottiViewState(lottieAnimationView, "start", "sound", true);
        } else if (ringerMode != 0) {
        } else {
            if (z) {
                audioManager.setRingerMode(2);
                textView.setText("Sound");
                setLottiViewState(lottieAnimationView, "start", "sound", true);
                return;
            }
            textView.setText("Mute");
            setLottiViewState(lottieAnimationView, "sound", "mute", false);
        }
    }

    public void isAutoRotateOn(Context context2, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2) {
        if (!(Settings.System.getInt(context2.getContentResolver(), "accelerometer_rotation", 0) == 1)) {
            Constants.setAutoOrientationEnabled(context2, true);
            setLottiViewState(lottieAnimationView, "start", "mid", true);
            setLottiViewState(lottieAnimationView2, "start", "mid", true);
            return;
        }
        Constants.setAutoOrientationEnabled(context2, false);
        setLottiViewState(lottieAnimationView, "mid", "end", false);
        setLottiViewState(lottieAnimationView2, "mid", "end", false);
    }

    public void openDirectory(Uri uri) {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT_TREE");
        intent.addFlags(1);
        intent.putExtra("android.provider.extra.INITIAL_URI", uri);
        this.context.startActivity(intent);
    }

    public boolean isAirplaneModeOn(Context context2) {
        return Settings.Global.getInt(context2.getContentResolver(), "airplane_mode_on", 0) != 0;
    }

    public void isAirplaneModeOn(Context context2, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2) {
        if (Settings.Global.getInt(context2.getContentResolver(), "airplane_mode_on", 0) != 0) {
            setLottiViewState(lottieAnimationView, "start", "mid", true);
            setLottiViewState(lottieAnimationView2, "start", "mid", true);
            return;
        }
        setLottiViewState(lottieAnimationView, "mid", "end", false);
        setLottiViewState(lottieAnimationView2, "mid", "end", false);
    }

    public void isBluetoothOn(LottieAnimationView lottieAnimationView) {
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            setLottiViewState(lottieAnimationView, "mid", "end", false);
        } else if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            setLottiViewState(lottieAnimationView, "start", "mid", true);
        } else {
            setLottiViewState(lottieAnimationView, "mid", "end", false);
        }
    }

    public void mobilecheack(Context context2, LottieAnimationView lottieAnimationView) {
        if (getMobileDataState(context2)) {
            setLottiViewState(lottieAnimationView, "start", "mid", true);
        } else {
            setLottiViewState(lottieAnimationView, "start", "mid", false);
        }
    }

    public boolean isSimPresent() {
        return ((TelephonyManager) this.context.getSystemService("phone")).getSimState() == 5;
    }

    public boolean isConnectedToNetwork() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo.isConnected() && activeNetworkInfo.getType() == 0;
    }

    public boolean isMobileDataEnable(Context context2) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context2.getSystemService("connectivity");
        try {
            Method declaredMethod = Class.forName(connectivityManager.getClass().getName()).getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public void setMobileDataState(boolean z, Context context2) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context2.getSystemService("phone");
            ((TelephonyManager) Objects.requireNonNull(telephonyManager)).getClass().getDeclaredMethod("setDataEnabled", new Class[]{Boolean.TYPE}).invoke(telephonyManager, new Object[]{Boolean.valueOf(z)});
        } catch (Exception e) {
            Log.e("MainActivity", "Error setting mobile data state", e);
        }
    }

    public boolean getMobileDataState(Context context2) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context2.getSystemService("phone");
            return ((Boolean) telephonyManager.getClass().getDeclaredMethod("getDataEnabled", new Class[0]).invoke(telephonyManager, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void gpsstate(Context context2, LottieAnimationView lottieAnimationView) {
        if (((LocationManager) context2.getSystemService("location")).isProviderEnabled("gps")) {
            setLottiViewState(lottieAnimationView, "start", "mid", true);
            this.isLocationEnabled = true;
            return;
        }
        setLottiViewState(lottieAnimationView, "mid", "end", false);
        this.isLocationEnabled = false;
    }

    public void setWifiState(boolean z, LottieAnimationView lottieAnimationView) {
        if (z) {
            setLottiViewState(lottieAnimationView, "start", "mid", true);
        } else {
            setLottiViewState(lottieAnimationView, "mid", "end", false);
        }
    }

    public boolean isWifiOn(Intent intent, LottieAnimationView lottieAnimationView) {
        if (((NetworkInfo) intent.getParcelableExtra("networkInfo")).isConnected()) {
            setLottiViewState(lottieAnimationView, "start", "mid", true);
            return true;
        }
        setLottiViewState(lottieAnimationView, "mid", "end", false);
        return false;
    }

    public void animateViewHeight(final View view, int i) {
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{view.getMeasuredHeight(), i});
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = intValue;
                view.setLayoutParams(layoutParams);
            }
        });
        ofInt.setDuration(250);
        ofInt.start();
    }

    public void animateViewWeightSum(final LinearLayout linearLayout, float f) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{linearLayout.getWeightSum(), f});
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                linearLayout.setWeightSum(((Float) valueAnimator.getAnimatedValue()).floatValue());
                linearLayout.requestLayout();
            }
        });
        ofFloat.setDuration(250);
        ofFloat.start();
    }

    public void animateViewAlpha(final View view, final float f) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{view.getAlpha(), f});
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                if (f == 1.0f) {
                    view.setAlpha(0.0f);
                    view.setVisibility(0);
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (f == 0.0f) {
                    view.setVisibility(8);
                }
            }
        });
        ofFloat.setDuration(250);
        ofFloat.start();
    }

    public void setLottieAnimColor(LottieAnimationView lottieAnimationView, final int i) {
        lottieAnimationView.addValueCallback(new KeyPath("**"), LottieProperty.COLOR_FILTER, new SimpleLottieValueCallback<ColorFilter>() {
            public ColorFilter getValue(LottieFrameInfo<ColorFilter> lottieFrameInfo) {
                return new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_ATOP);
            }
        });
    }

    public Bitmap getBitmapFromByteArray(byte[] bArr) {
        if (bArr != null) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        return null;
    }

    public void stopForegroundService(Context context2) {
        if (Build.VERSION.SDK_INT >= 29) {
            ((Service) context2).stopForeground(true);
        }
    }

    public void startForegroundService(Context context2) {
        if (Build.VERSION.SDK_INT >= 29) {
            Service service = (Service) context2;
            NotificationManager notificationManager = (NotificationManager) context2.getSystemService(NotificationManager.class);
            if (notificationManager.getNotificationChannel("FOREGROUND_INFO") == null) {
                NotificationChannel notificationChannel = new NotificationChannel("FOREGROUND_INFO", "Keep Alive", 2);
                notificationChannel.setSound((Uri) null, (AudioAttributes) null);
                notificationChannel.setVibrationPattern((long[]) null);
                notificationChannel.setShowBadge(false);
                notificationChannel.setDescription("Useful to keep the app running in the background");
                notificationManager.createNotificationChannel(notificationChannel);
            }
            Notification.BigTextStyle summaryText = new Notification.BigTextStyle().setSummaryText("Keep Alive");
            Notification.Builder builder = new Notification.Builder(context2, "FOREGROUND_INFO");
            builder.setSmallIcon(R.drawable.info);
            builder.setColor(context2.getResources().getColor(R.color.colorAccent));
            builder.setStyle(summaryText).setContentTitle("Running").setContentText("This notification helps to keep the app running");
            builder.setPriority(-1);
            service.startForeground(99, builder.build());
        }
    }

    public int getStatusBarHeight(Resources resources) {
        int identifier = resources.getIdentifier("android:dimen/status_bar_height", (String) null, (String) null);
        if (identifier > 0) {
            return resources.getDimensionPixelOffset(identifier);
        }
        return (int) Math.ceil((double) (((float) (Build.VERSION.SDK_INT >= 23 ? 24 : 25)) * resources.getDisplayMetrics().density));
    }

    public String getFormatedDate(long j) {
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        if (j > timeInMillis || j <= 0) {
            return null;
        }
        long j2 = timeInMillis - j;
        if (j2 < 60000) {
            return "now";
        }
        if (j2 < 120000) {
            return "1m";
        }
        if (j2 < 3000000) {
            return (j2 / 60000) + " m";
        }
        if (j2 < 5400000) {
            return "1h";
        }
        if (j2 < 86400000) {
            return (j2 / 3600000) + " h";
        }
        if (j2 < 172800000) {
            return "1d";
        }
        return (j2 / 86400000) + " d";
    }

    public String getFormatedDate1(long j) {
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(j);
        if (instance2.get(1) != instance.get(1)) {
            return DateFormat.format("MMMM dd yyyy, HH:mm", instance2).toString();
        }
        if (instance2.get(2) != instance.get(2)) {
            return DateFormat.format("MMMM d, HH:mm", instance2).toString();
        }
        if (instance2.get(5) - instance.get(5) == 1) {
            return "Tomorrow at " + DateFormat.format("HH:mm", instance2);
        }
        if (instance.get(5) == instance2.get(5)) {
            return "Today at " + DateFormat.format("HH:mm", instance2);
        }
        if (instance.get(5) - instance2.get(5) == 1) {
            return "Yesterday at " + DateFormat.format("HH:mm", instance2);
        }
        return DateFormat.format("MMMM d, HH:mm", instance2).toString();
    }

    public static ActivityInfo getActivityInfo(Context context2, String str, String str2) {
        Intent intent = new Intent();
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setAction("android.intent.action.MAIN");
        intent.setPackage(str);
        intent.setComponent(new ComponentName(str, str2));
        ResolveInfo resolveActivity = context2.getPackageManager().resolveActivity(intent, 0);
        if (resolveActivity != null) {
            return resolveActivity.activityInfo;
        }
        return null;
    }

    public static ArrayList<AppDetail> sortAppsAlphabetically(ArrayList<AppDetail> arrayList) {
        ArrayList<AppDetail> arrayList2 = new ArrayList<>(arrayList);
        arrayList.clear();
        arrayList2.sort(new Comparator<AppDetail>() {
            public int compare(AppDetail appDetail, AppDetail appDetail2) {
                return appDetail.label.compareToIgnoreCase(appDetail2.label);
            }
        });
        return arrayList2;
    }
}
