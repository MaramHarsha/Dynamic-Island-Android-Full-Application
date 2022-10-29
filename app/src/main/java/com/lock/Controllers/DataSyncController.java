package com.lock.Controllers;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;

public class DataSyncController extends ButtonState {
    private Context context;
    private Intent intent;
    private String name;

    public String getName() {
        return null;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public DataSyncController(Context context2) {
        super(context2);
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (ContentResolver.getMasterSyncAutomatically()) {
            ContentResolver.setMasterSyncAutomatically(z);
        } else {
            ContentResolver.setMasterSyncAutomatically(z);
        }
    }

    public void setSyncState(boolean z) {
        ContentResolver.setMasterSyncAutomatically(z);
    }

    public boolean getState() {
        return ContentResolver.getMasterSyncAutomatically();
    }

    public Intent getIntent() {
        return new Intent("android.settings.SYNC_SETTINGS");
    }
}
