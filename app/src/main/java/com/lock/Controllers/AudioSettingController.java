package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class AudioSettingController extends ButtonState {
    private final Context context;
    private final Intent intent;
    private String name;
    public final AudioManager w;

    public String getName() {
        return null;
    }

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public AudioSettingController(Context context2) {
        super(context2);
        this.context = context2;
        this.w = (AudioManager) context2.getSystemService("audio");
        if (Build.VERSION.SDK_INT >= 29) {
            this.intent = new Intent("android.settings.panel.action.VOLUME");
        } else {
            this.intent = new Intent("android.settings.SOUND_SETTINGS");
        }
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        this.w.getRingerMode();
    }

    public void setMode(ImageView imageView, TextView textView, TextView textView2) {
        int ringerMode = this.w.getRingerMode();
        if (ringerMode == 0) {
            this.w.setRingerMode(2);
            imageView.setImageResource(R.drawable.on);
            textView.setText("Volume");
            textView2.setText("Normal");
        } else if (ringerMode == 1) {
            this.w.setRingerMode(0);
            imageView.setImageResource(R.drawable.off);
            textView.setText("Volume");
            textView2.setText("Mute");
        } else if (ringerMode == 2) {
            this.w.setRingerMode(1);
            imageView.setImageResource(R.drawable.off);
            textView.setText("Volume");
            textView2.setText("Vibrate");
        }
    }

    public int getMode() {
        return this.w.getRingerMode();
    }

    public Intent getIntent() {
        return this.intent;
    }
}
