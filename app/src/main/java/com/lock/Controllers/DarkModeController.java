package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class DarkModeController extends ButtonState {
    private final Context context;
    private final Intent intent = new Intent("android.settings.DISPLAY_SETTINGS");
    private String name;

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public DarkModeController(Context context2) {
        super(context2);
        this.context = context2;
        this.name = context2.getString(R.string.quick_settings_ui_mode_night_label);
    }

    public boolean getState() {
        this.context.getApplicationContext().getResources().updateConfiguration(this.context.getApplicationContext().getResources().getConfiguration(), this.context.getApplicationContext().getResources().getDisplayMetrics());
        return (this.context.getApplicationContext().getResources().getConfiguration().uiMode & 48) == 32;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getName() {
        return this.name;
    }
}
