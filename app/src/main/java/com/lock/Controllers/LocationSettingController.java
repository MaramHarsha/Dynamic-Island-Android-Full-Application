package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class LocationSettingController extends ButtonState {
    private Context context;

    public boolean hasSystemFeature() {
        return true;
    }

    public LocationSettingController(Context context2) {
        super(context2);
        this.context = context2;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        int i;
        if (((LocationManager) this.context.getSystemService("location")).isProviderEnabled("gps")) {
            lottieAnimationView.setImageResource(R.drawable.on);
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
        }
        if (this.context.getPackageManager().checkPermission("android.permission.WRITE_SECURE_SETTINGS", this.context.getPackageName()) == 0 && Build.VERSION.SDK_INT < 28) {
            try {
                i = Settings.Secure.getInt(this.context.getContentResolver(), "location_previous_mode");
            } catch (Settings.SettingNotFoundException unused) {
                i = 2;
            }
            Settings.Secure.putInt(this.context.getContentResolver(), "location_mode", i);
        }
    }

    public boolean getState() {
        try {
            return Settings.Secure.getInt(this.context.getContentResolver(), "location_mode") != 0;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    public Intent getIntent() {
        Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        intent.setFlags(268435456);
        return intent;
    }

    public String getName() {
        try {
            int identifier = this.context.getResources().getIdentifier("com.android.systemui:string/quick_settings_location_label", (String) null, (String) null);
            Resources resources = this.context.getResources();
            if (identifier == 0) {
                identifier = this.context.getResources().getIdentifier("com.android.systemui:string/accessibility_quick_settings_location_changed_on", (String) null, (String) null);
            }
            String string = resources.getString(identifier);
            int indexOf = string.indexOf(" ");
            if (indexOf > 0) {
                return string.substring(0, indexOf);
            }
            return string;
        } catch (Exception unused) {
            return this.context.getResources().getString(R.string.quick_settings_location_label);
        }
    }
}
