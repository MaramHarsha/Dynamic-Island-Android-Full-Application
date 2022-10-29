package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;

public class BatterySaverController extends ButtonState {
    private final Context context;
    private final Intent intent = null;
    private String name;

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public BatterySaverController(Context context2) {
        super(context2);
        this.context = context2;
        try {
            int identifier = context2.getResources().getIdentifier("com.android.systemui:string/state_button_powersavingmode", (String) null, (String) null);
            identifier = identifier == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/super_power_widget_name", (String) null, (String) null) : identifier;
            this.name = context2.getResources().getString(identifier == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/battery_detail_switch_title", (String) null, (String) null) : identifier);
        } catch (Exception unused) {
            this.name = null;
        }
    }

    public boolean setMode() {
        if (this.context.getPackageManager().checkPermission("android.permission.WRITE_SECURE_SETTINGS", this.context.getPackageName()) != 0) {
            Toast.makeText(this.context, "No permission to write settings", 1).show();
            return false;
        }
        boolean z = !getState();
        Settings.Global.putInt(this.context.getContentResolver(), "low_power", z ? 1 : 0);
        Settings.Global.putString(this.context.getContentResolver(), "low_power", String.valueOf(z));
        return true;
    }

    public boolean getState() {
        try {
            String string = Settings.Global.getString(this.context.getContentResolver(), "low_power");
            if (string != null) {
                return string.equals("1");
            }
            if (Settings.Global.getInt(this.context.getContentResolver(), "low_power") == 1 || Settings.System.getInt(this.context.getContentResolver(), "POWER_SAVE_MODE_OPEN") == 1) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
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
