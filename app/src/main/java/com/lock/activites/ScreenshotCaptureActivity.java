package com.lock.activites;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.lock.utils.Constants;
import com.lock.utils.screenShotRunable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Objects;
import pub.devrel.easypermissions.EasyPermissions;

public class ScreenshotCaptureActivity extends Activity {
    public static MediaProjection mediaProjection;
    public static String message;
    public String[] STORAGE_PERMISSION = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    public MediaProjectionManager f11371o;
    public Handler handler;
    public ImageReader imageReader;
    public int mHeightPixels;
    public int mWidthPixels;
    public VirtualDisplay virtualDisplay;

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        EasyPermissions.onRequestPermissionsResult(i, strArr, iArr, this);
    }

    public class a extends Thread {
        public a() {
        }

        public void run() {
            Looper.prepare();
            ScreenshotCaptureActivity.this.handler = new Handler();
            Looper.loop();
        }
    }

    public void mOnImageAvailable(ImageReader imageReader2) {
        this.handler.post(new screenShotRunable());
        try {
            Image acquireLatestImage = imageReader2.acquireLatestImage();
            Image.Plane[] planes = acquireLatestImage.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int i = this.mWidthPixels;
            int i2 = rowStride - (pixelStride * i);
            Bitmap createBitmap = Bitmap.createBitmap(i + (i2 / pixelStride), this.mHeightPixels, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(buffer);
            acquireLatestImage.close();
            imageReader2.close();
            if (i2 / pixelStride == 0) {
                a(this, createBitmap);
            } else {
                Bitmap createBitmap2 = Bitmap.createBitmap(createBitmap, 0, 0, this.mWidthPixels, this.mHeightPixels);
                createBitmap.recycle();
                a(this, createBitmap2);
            }
            Objects.requireNonNull(this);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class c extends MediaProjection.Callback {

        public class a implements Runnable {
            public a() {
            }

            public void run() {
                VirtualDisplay virtualDisplay = ScreenshotCaptureActivity.this.virtualDisplay;
                if (virtualDisplay != null) {
                    virtualDisplay.release();
                }
                ImageReader imageReader = ScreenshotCaptureActivity.this.imageReader;
                if (imageReader != null) {
                    imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                        public void onImageAvailable(ImageReader imageReader) {
                            ScreenshotCaptureActivity.this.mOnImageAvailable(imageReader);
                        }
                    }, ScreenshotCaptureActivity.this.handler);
                }
                ScreenshotCaptureActivity.mediaProjection.unregisterCallback(c.this);
            }
        }

        public c(a aVar) {
        }

        public void onStop() {
            ScreenshotCaptureActivity.this.handler.post(new a());
        }
    }

    public static void a(ScreenshotCaptureActivity screenshotCaptureActivity, Bitmap bitmap) {
        Objects.requireNonNull(screenshotCaptureActivity);
        String str = message + "Screenshot_" + DateFormat.format("yyyyMMdd-HHmmss", new Date()) + ".jpg";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.parse("file://" + str));
            screenshotCaptureActivity.sendBroadcast(intent);
            fileOutputStream.close();
        } catch (FileNotFoundException unused) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            MediaProjection mediaProjection2 = this.f11371o.getMediaProjection(i2, intent);
            mediaProjection = mediaProjection2;
            if (mediaProjection2 != null) {
                message = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Screenshots/";
                File file = new File(message);
                if (!file.exists()) {
                    file.mkdirs();
                }
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
                int i3 = displayMetrics.widthPixels;
                this.mWidthPixels = i3;
                int i4 = displayMetrics.heightPixels;
                this.mHeightPixels = i4;
                ImageReader newInstance = ImageReader.newInstance(i3, i4, 256, 1);
                this.imageReader = newInstance;
                this.virtualDisplay = mediaProjection.createVirtualDisplay("screencap", this.mWidthPixels, this.mHeightPixels, displayMetrics.densityDpi, 9, newInstance.getSurface(), (VirtualDisplay.Callback) null, this.handler);
                this.imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                    public void onImageAvailable(ImageReader imageReader) {
                        ScreenshotCaptureActivity.this.mOnImageAvailable(imageReader);
                    }
                }, this.handler);
                mediaProjection.registerCallback(new c((c.a) null), this.handler);
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!Constants.hasPermissions(this, this.STORAGE_PERMISSION)) {
            finish();
        } else {
            startCapture();
        }
    }

    private void startCapture() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService("media_projection");
        this.f11371o = mediaProjectionManager;
        try {
            startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 1);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "Unfortunately, screen recording isn't supported on your device.", 1).show();
        }
        new a().start();
    }
}
