package com.lock.Controllers;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class ZenModeController extends ButtonState {
    public static final Intent intent = new Intent("android.settings.ZEN_MODE_SETTINGS");
    
    public final Context context;
    private int mode = 0;
    MyContentObserver myContentObserver = new MyContentObserver(new Handler(new Handler.Callback() {
        public boolean handleMessage(Message message) {
            return false;
        }
    }));
    public NotificationManager notificationManager;

    public boolean hasSystemFeature() {
        return true;
    }

    public ZenModeController(Context context2) {
        super(context2);
        this.context = context2;
        this.notificationManager = (NotificationManager) context2.getSystemService("notification");
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
        }
    }

    public final class MyContentObserver extends ContentObserver {
        private Handler myHandle;

        public MyContentObserver(Handler handler) {
            super(handler);
            this.myHandle = handler;
        }

        public void onChange(boolean z, Uri uri) {
            boolean z2 = false;
            try {
                if (Settings.Global.getInt(ZenModeController.this.context.getContentResolver(), "zen_mode") != 0) {
                    z2 = true;
                }
            } catch (Settings.SettingNotFoundException unused) {
            }
            this.myHandle.obtainMessage(5, Boolean.valueOf(z2)).sendToTarget();
        }
    }

    public void registerContentObserver(boolean z) {
        if (z) {
            this.context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("zen_mode"), false, this.myContentObserver);
            return;
        }
        this.context.getContentResolver().unregisterContentObserver(this.myContentObserver);
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                this.notificationManager.setInterruptionFilter(0);
            }
        } catch (SecurityException unused) {
            Toast.makeText(this.context, "Permission denied", 1).show();
        }
    }

    public boolean getState() {
        try {
            return Settings.Global.getInt(this.context.getContentResolver(), "zen_mode") != 0;
        } catch (Settings.SettingNotFoundException unused) {
            return false;
        }
    }

    public Intent getIntent() {
        return intent;
    }

    public String getName() {
        String string = this.context.getString(R.string.quick_settings_dnd_label);
        int i = this.mode;
        if (i == 3) {
            return this.context.getString(R.string.quick_settings_dnd_none_label);
        }
        if (i == 4) {
            return this.context.getString(R.string.quick_settings_dnd_alarms_label);
        }
        if (i == 2) {
            return this.context.getString(R.string.quick_settings_dnd_priority_label);
        }
        return i == 0 ? this.context.getString(R.string.quick_settings_dnd_label) : string;
    }
}
