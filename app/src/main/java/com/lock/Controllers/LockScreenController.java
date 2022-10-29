package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class LockScreenController extends ButtonState {
    private Context context;

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public LockScreenController(Context context2) {
        super(context2);
        this.context = context2;
    }

    public Intent getIntent() {
        return new Intent("android.app.action.SET_NEW_PASSWORD");
    }

    public String getName() {
        return this.context.getResources().getString(R.string.screen_lock);
    }
}
