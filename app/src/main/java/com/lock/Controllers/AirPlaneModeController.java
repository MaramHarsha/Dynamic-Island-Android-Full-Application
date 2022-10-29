package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class AirPlaneModeController extends ButtonState {
    private final Context context;
    private final Intent intent;
    private String name;

    public boolean hasSystemFeature() {
        return true;
    }

    public AirPlaneModeController(Context context2) {
        super(context2);
        this.context = context2;
        if (Build.VERSION.SDK_INT >= 29) {
            this.intent = new Intent("android.settings.panel.action.INTERNET_CONNECTIVITY");
        } else {
            this.intent = new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
        }
        try {
            int identifier = Build.MANUFACTURER.equalsIgnoreCase("samsung") ? context2.getResources().getIdentifier("com.android.systemui:string/airplane_mode_plmn", (String) null, (String) null) : 0;
            identifier = identifier == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/airplane_mode", (String) null, (String) null) : identifier;
            this.name = context2.getResources().getString(identifier == 0 ? context2.getResources().getIdentifier("com.android.systemui:string/accessibility_airplane_mode", (String) null, (String) null) : identifier);
        } catch (Exception unused) {
            this.name = null;
        }
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getName() {
        return this.name;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
        }
    }

    public boolean getState() {
        return Settings.Global.getInt(this.context.getContentResolver(), "airplane_mode_on", 0) != 0;
    }
}
