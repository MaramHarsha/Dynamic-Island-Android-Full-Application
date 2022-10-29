package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;

public class ScreenTimoutController extends ButtonState {
    private final Context context;
    public int currentScreenTimeout = 0;

    public String getName() {
        return "Screen Timeout";
    }

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public ScreenTimoutController(Context context2) {
        super(context2);
        this.context = context2;
    }

    public Intent getIntent() {
        return new Intent("android.settings.DISPLAY_SETTINGS");
    }

    public String setTimeout() {
        int i;
        String str;
        try {
            i = Settings.System.getInt(this.context.getContentResolver(), "screen_off_timeout");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            i = 0;
        }
        if (i >= 0 && i <= 15000) {
            str = "15s,30s";
            i = 30000;
        } else if (i >= 16000 && i <= 30000) {
            str = "30s,01m";
            i = 60000;
        } else if (i >= 31000 && i <= 60000) {
            str = "01m,02m";
            i = 120000;
        } else if (i >= 61000 && i <= 120000) {
            str = "02m,05m";
            i = 300000;
        } else if (i < 120000 || i > 300000) {
            str = "01m";
        } else {
            str = "0,15s";
            i = 15000;
        }
        Settings.System.putInt(this.context.getContentResolver(), "screen_off_timeout", i);
        return str;
    }

    public String getTimeoutString() {
        int i;
        try {
            i = Settings.System.getInt(this.context.getContentResolver(), "screen_off_timeout");
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            i = 0;
        }
        if (i >= 0 && i <= 15000) {
            return "15s";
        }
        if (i >= 16000 && i <= 30000) {
            return "30s";
        }
        if (i >= 31000 && i <= 60000) {
            return "01m";
        }
        if (i < 61000 || i > 120000) {
            return (i < 120000 || i > 300000) ? "01m" : "05m";
        }
        return "02m";
    }
}
