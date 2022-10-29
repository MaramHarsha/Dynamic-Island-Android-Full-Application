package com.lock.Controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.lock.utils.Utils;
import com.dynamic.island.harsha.notification.R;

public class NightModeController extends ButtonState {
    private Context context;
    private Intent intent;
    private String name;

    public boolean hasSystemFeature() {
        return true;
    }

    public NightModeController(Context context2) {
        super(context2);
        this.context = context2;
        Intent component = new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$BlueLightFilterSettingsActivity"));
        this.intent = component;
        if (!Utils.checkIfActivityFound(context2, component)) {
            this.intent = new Intent("android.settings.NIGHT_DISPLAY_SETTINGS");
        }
        try {
            String str = Build.MANUFACTURER;
            int i = 0;
            if (str.equalsIgnoreCase("vivo")) {
                Resources resourcesForApplication = this.context.getPackageManager().getResourcesForApplication("com.vivo.upslide");
                int identifier = resourcesForApplication.getIdentifier("com.vivo.upslide:string/vivo_switcher_eye_protection", (String) null, (String) null);
                if (identifier != 0) {
                    this.name = resourcesForApplication.getString(identifier);
                    return;
                }
                i = identifier;
            }
            i = str.equalsIgnoreCase("huawei") ? context2.getResources().getIdentifier("com.android.systemui:string/eye_comfort_widget_name", (String) null, (String) null) : i;
            i = i == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/quick_settings_bluelightfilter_label", (String) null, (String) null) : i;
            i = i == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/quick_settings_night_mode", (String) null, (String) null) : i;
            i = i == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/quick_settings_papermode_label", (String) null, (String) null) : i;
            this.name = context2.getResources().getString(i == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/quick_settings_night_display_label", (String) null, (String) null) : i);
        } catch (Exception unused) {
            this.name = this.context.getString(R.string.quick_settings_night_display_label);
        }
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
        }
    }

    public boolean getState() {
        if (Build.BRAND.toLowerCase().equals("samsung")) {
            if (Settings.System.getInt(this.context.getContentResolver(), "blue_light_filter", 0) == 1) {
                return true;
            }
        } else if (Settings.Secure.getInt(this.context.getContentResolver(), "night_display_activated", 0) == 1) {
            return true;
        }
        return false;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getName() {
        return this.name;
    }
}
