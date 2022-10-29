package com.lock.entity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;

public abstract class ButtonState {
    Context context;
    public Drawable iconOff;
    public Drawable iconOn;
    public Boolean isStateOn;
    public Intent launchIntent;
    public String title;

    public abstract Intent getIntent();

    public abstract String getName();

    public abstract boolean getState();

    public abstract boolean hasSystemFeature();

    public abstract void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2);

    public ButtonState(Context context2) {
        this.context = context2;
    }
}
