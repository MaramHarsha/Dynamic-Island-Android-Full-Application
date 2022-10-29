package com.lock.Controllers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class ScreenCastController extends ButtonState {
    private Context context;

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public ScreenCastController(Context context2) {
        super(context2);
        this.context = context2;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
        }
    }

    public boolean startScreenCast() {
        try {
            Intent intent = new Intent("android.settings.CAST_SETTINGS");
            intent.addFlags(335544320);
            PendingIntent.getActivity(this.context, 0, intent, 0).send();
            return true;
        } catch (Exception unused) {
            Toast.makeText(this.context, "No Activity found to handle this feature", 0).show();
            return false;
        }
    }

    public Intent getIntent() {
        return new Intent("android.settings.CAST_SETTINGS");
    }

    public String getName() {
        return this.context.getResources().getString(R.string.screenshot);
    }
}
