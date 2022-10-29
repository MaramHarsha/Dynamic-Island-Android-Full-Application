package com.lock.Controllers;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.UserManager;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class BlueToothSettingController extends ButtonState {
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private final Context context;
    private final Intent intent = new Intent("android.settings.BLUETOOTH_SETTINGS");
    private String name;
    private final UserManager userManager;

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public BlueToothSettingController(Context context2) {
        super(context2);
        this.context = context2;
        UserManager userManager2 = (UserManager) context2.getSystemService("user");
        this.userManager = userManager2;
        this.name = userManager2.getUserName();
    }

    public boolean isAllowed() {
        return !this.userManager.hasUserRestriction("no_config_bluetooth") && !this.userManager.hasUserRestriction("no_bluetooth");
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        BluetoothAdapter bluetoothAdapter2 = this.bluetoothAdapter;
        if (bluetoothAdapter2 == null) {
            return;
        }
        if (z) {
            bluetoothAdapter2.enable();
            lottieAnimationView.setImageResource(R.drawable.on);
            return;
        }
        bluetoothAdapter2.disable();
        lottieAnimationView.setImageResource(R.drawable.off);
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getName() {
        return this.name;
    }
}
