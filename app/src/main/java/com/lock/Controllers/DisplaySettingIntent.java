package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class DisplaySettingIntent extends ButtonState {
    private Context context;

    public boolean hasSystemFeature() {
        return true;
    }

    public DisplaySettingIntent(Context context2) {
        super(context2);
        this.context = context2;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
            Settings.System.putInt(this.context.getContentResolver(), "accelerometer_rotation", 1);
            return;
        }
        lottieAnimationView.setImageResource(R.drawable.off);
        Settings.System.putInt(this.context.getContentResolver(), "accelerometer_rotation", 0);
    }

    public boolean getState() {
        return Settings.System.getInt(this.context.getContentResolver(), "accelerometer_rotation", 0) == 0;
    }

    public Intent getIntent() {
        return new Intent("android.settings.DISPLAY_SETTINGS");
    }

    public String getName() {
        return this.context.getString(R.string.quick_settings_rotation_unlocked_label);
    }
}
