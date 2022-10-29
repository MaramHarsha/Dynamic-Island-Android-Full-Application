package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class BrightnessModeController extends ButtonState {
    private Context context;
    private Intent intent = new Intent("android.settings.DISPLAY_SETTINGS");
    private String name = this.context.getString(R.string.brightness_mode);

    public boolean hasSystemFeature() {
        return true;
    }

    public BrightnessModeController(Context context2) {
        super(context2);
        this.context = context2;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            Settings.System.putInt(this.context.getContentResolver(), "screen_brightness_mode", 1);
            lottieAnimationView.setImageResource(R.drawable.on);
            return;
        }
        Settings.System.putInt(this.context.getContentResolver(), "screen_brightness_mode", 0);
        lottieAnimationView.setImageResource(R.drawable.off);
    }

    public boolean getState() {
        try {
            return Settings.System.getInt(this.context.getContentResolver(), "screen_brightness_mode", 0) == 1;
        } catch (Exception unused) {
            return false;
        }
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getName() {
        return this.name;
    }
}
