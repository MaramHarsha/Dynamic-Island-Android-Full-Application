package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class TorchMainController extends ButtonState {
    CameraFlashController cameraFlashController;
    public final CameraManager cameraManager;
    private final Context context;
    public String stateName;
    TorchWithCameraController torchWithCameraController;

    public TorchMainController(Context context2) {
        super(context2);
        this.context = context2;
        this.cameraManager = (CameraManager) context2.getSystemService("camera");
        if (Build.VERSION.SDK_INT >= 23) {
            this.cameraFlashController = new CameraFlashController(context2);
        } else {
            this.torchWithCameraController = new TorchWithCameraController(context2);
        }
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
            textView2.setText(this.context.getResources().getString(R.string.text_on));
            CameraFlashController cameraFlashController2 = this.cameraFlashController;
            if (cameraFlashController2 != null) {
                cameraFlashController2.setTorchMode(true);
            } else {
                this.torchWithCameraController.setTorchMode(true);
            }
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
            textView2.setText(this.context.getResources().getString(R.string.text_off));
            CameraFlashController cameraFlashController3 = this.cameraFlashController;
            if (cameraFlashController3 != null) {
                cameraFlashController3.setTorchMode(false);
            } else {
                this.torchWithCameraController.setTorchMode(false);
            }
        }
        textView.setText(this.context.getResources().getString(R.string.quick_settings_flashlight_label));
    }

    public boolean getState() {
        CameraFlashController cameraFlashController2 = this.cameraFlashController;
        if (cameraFlashController2 != null) {
            return cameraFlashController2.isEnabled();
        }
        return this.torchWithCameraController.isEnabled();
    }

    public Intent getIntent() {
        return new Intent("android.media.action.STILL_IMAGE_CAMERA");
    }

    public String getName() {
        return this.context.getResources().getString(R.string.quick_settings_flashlight_label);
    }

    public boolean hasSystemFeature() {
        return this.context.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
    }
}
