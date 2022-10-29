package com.lock.Controllers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.activites.ScreenshotCaptureActivity;
import com.lock.entity.ButtonState;
import com.lock.services.MAccessibilityService;
import com.dynamic.island.harsha.notification.R;

public class ScreenShotController extends ButtonState {
    private Context context;
    Handler handler = new Handler();

    public Intent getIntent() {
        return null;
    }

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public ScreenShotController(Context context2) {
        super(context2);
        this.context = context2;
    }

    public boolean takeScreenShot(final MAccessibilityService mAccessibilityService) {
        if (Build.VERSION.SDK_INT < 28) {
            return false;
        }
        this.context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        this.handler.postDelayed(new Runnable() {
            public void run() {
                mAccessibilityService.performGlobalAction(9);
            }
        }, 500);
        return true;
    }

    public boolean startScreenCapture() {
        try {
            Intent intent = new Intent(this.context, ScreenshotCaptureActivity.class);
            intent.addFlags(335544320);
            PendingIntent.getActivity(this.context, 0, intent, 0).send();
            return true;
        } catch (Exception unused) {
            Toast.makeText(this.context, "No Activity found to handle this feature", 0).show();
            return false;
        }
    }

    public String getName() {
        return this.context.getResources().getString(R.string.screenshot);
    }
}
