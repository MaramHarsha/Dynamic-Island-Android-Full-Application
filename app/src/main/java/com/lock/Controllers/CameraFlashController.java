package com.lock.Controllers;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class CameraFlashController {
    public boolean aBoolean1;
    public String cameraId;
    public final CameraManager cameraManager;
    public final Context context;
    public Handler handler;
    public boolean isEnabled = false;
    public final CameraManager.TorchCallback torchCallback = new TorchCallBack();

    public class TorchCallBack extends CameraManager.TorchCallback {
        public void onTorchModeChanged(String str, boolean z) {
        }

        public void onTorchModeUnavailable(String str) {
        }

        public TorchCallBack() {
        }
    }

    public CameraFlashController(Context context2) {
        this.context = context2;
        this.cameraManager = (CameraManager) context2.getSystemService("camera");
        initTorch();
    }

    public void setTorchMode(boolean z) {
        synchronized (this) {
            String str = this.cameraId;
            if (!(str == null || this.isEnabled == z)) {
                this.isEnabled = z;
                try {
                    this.cameraManager.setTorchMode(str, z);
                } catch (CameraAccessException e) {
                    Toast.makeText(this.context, "FlashlightController: Couldn't set torch mode", 0).show();
                    Log.e("FlashlightController", "Couldn't set torch mode", e);
                    this.isEnabled = false;
                }
            }
        }
    }

    public final void initTorch() {
        String str;
        try {
            String[] cameraIdList = this.cameraManager.getCameraIdList();
            int length = cameraIdList.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    String str2 = cameraIdList[i];
                    CameraCharacteristics cameraCharacteristics = this.cameraManager.getCameraCharacteristics(str2);
                    Boolean bool = (Boolean) cameraCharacteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                    if (bool != null && bool.booleanValue() && num != null && num.intValue() == 1) {
                        str = str2;
                        break;
                    }
                    i++;
                } else {
                    str = null;
                    break;
                }
            }
            this.cameraId = str;
            if (str != null) {
                synchronized (this) {
                    if (this.handler == null) {
                        this.handler = new Handler(new Handler.Callback() {
                            public boolean handleMessage(Message message) {
                                return false;
                            }
                        });
                    }
                }
                this.cameraManager.registerTorchCallback(this.torchCallback, this.handler);
            }
        } catch (Throwable th) {
            Toast.makeText(this.context, "FlashlightController: Couldn't initialize.", 0).show();
            Log.e("FlashlightController", "Couldn't initialize.", th);
        }
    }

    public synchronized boolean isEnabled() {
        return this.isEnabled;
    }
}
