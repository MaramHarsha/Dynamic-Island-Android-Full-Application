package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class NFCController extends ButtonState {
    private Context context;
    public NfcAdapter nfcAdapter;

    public NFCController(Context context2) {
        super(context2);
        this.context = context2;
        if (this.nfcAdapter == null) {
            try {
                this.nfcAdapter = NfcAdapter.getDefaultAdapter(context2);
            } catch (UnsupportedOperationException unused) {
                this.nfcAdapter = null;
            }
        }
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
        }
    }

    public boolean getState() {
        if (!hasSystemFeature() || this.nfcAdapter == null) {
            return false;
        }
        return NfcAdapter.getDefaultAdapter(this.context).isEnabled();
    }

    public Intent getIntent() {
        if (Build.VERSION.SDK_INT >= 29) {
            return new Intent("android.settings.panel.action.NFC");
        }
        return new Intent("android.settings.NFC_SETTINGS");
    }

    public String getName() {
        return this.context.getString(R.string.quick_settings_nfc_label);
    }

    public boolean hasSystemFeature() {
        return this.context.getPackageManager().hasSystemFeature("android.hardware.nfc");
    }
}
