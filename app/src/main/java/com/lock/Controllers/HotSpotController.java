package com.lock.Controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.lock.utils.Utils;
import java.lang.reflect.InvocationTargetException;

public class HotSpotController extends ButtonState {
    private Context context;
    public WifiManager wifiManager;

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public HotSpotController(Context context2) {
        super(context2);
        this.context = context2;
        this.wifiManager = (WifiManager) context2.getApplicationContext().getSystemService("wifi");
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        try {
            this.wifiManager.getClass().getMethod("setWifiApEnabled", new Class[]{WifiConfiguration.class, Boolean.TYPE}).invoke(this.wifiManager, new Object[]{null, Boolean.valueOf(z)});
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Intent getIntent() {
        Intent component = new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.TetherSettings"));
        if (Utils.checkIfActivityFound(this.context, component)) {
            return component;
        }
        return null;
    }

    public String getName() {
        try {
            int identifier = this.context.getResources().getIdentifier("com.android.systemui:string/mobileap", (String) null, (String) null);
            Resources resources = this.context.getResources();
            if (identifier == 0) {
                identifier = this.context.getResources().getIdentifier("com.android.systemui:string/quick_settings_hotspot_label", (String) null, (String) null);
            }
            return resources.getString(identifier);
        } catch (Exception unused) {
            return "User";
        }
    }
}
