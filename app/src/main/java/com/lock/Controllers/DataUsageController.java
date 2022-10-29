package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class DataUsageController extends ButtonState {
    ConnectivityManager connectivity;
    private Context context;

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public DataUsageController(Context context2) {
        super(context2);
        this.context = context2;
        this.connectivity = (ConnectivityManager) context2.getSystemService("connectivity");
    }

    public boolean getState() {
        return Build.VERSION.SDK_INT >= 24 && this.connectivity.getRestrictBackgroundStatus() == 3;
    }

    public Intent getIntent() {
        return new Intent("android.settings.DATA_USAGE_SETTINGS");
    }

    public String getName() {
        return this.context.getString(R.string.data_saver);
    }
}
