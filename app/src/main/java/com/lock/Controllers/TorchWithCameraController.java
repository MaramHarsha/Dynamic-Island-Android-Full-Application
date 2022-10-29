package com.lock.Controllers;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import java.io.IOException;
import kotlinx.coroutines.DebugKt;

public class TorchWithCameraController {
    public Camera camera = null;
    private Context context;
    public boolean isEnabled = false;

    public TorchWithCameraController(Context context2) {
        this.context = context2;
    }

    public void setTorchMode(boolean z) {
        this.isEnabled = z;
        if (z) {
            Camera open = Camera.open();
            this.camera = open;
            Camera.Parameters parameters = open.getParameters();
            parameters.setFlashMode("torch");
            this.camera.setParameters(parameters);
            try {
                this.camera.setPreviewTexture(new SurfaceTexture(0));
            } catch (IOException unused) {
            }
            this.camera.startPreview();
            return;
        }
        Camera camera2 = this.camera;
        if (camera2 != null) {
            try {
                Camera.Parameters parameters2 = camera2.getParameters();
                parameters2.setFlashMode(DebugKt.DEBUG_PROPERTY_VALUE_OFF);
                this.camera.setParameters(parameters2);
            } catch (Exception unused2) {
            }
            this.camera.stopPreview();
            this.camera.release();
            this.camera = null;
        }
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }
}
