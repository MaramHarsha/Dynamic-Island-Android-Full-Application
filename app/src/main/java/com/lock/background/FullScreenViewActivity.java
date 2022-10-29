package com.lock.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dynamic.island.harsha.notification.R;
import java.io.ByteArrayOutputStream;

public class FullScreenViewActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "FullScreenViewActivity";
    
    public Bitmap bitmap;
    private ImageView fullImageView;
    
    public LinearLayout llDownloadWallpaper;
    
    public LinearLayout llSetWallpaper;
    
    public LinearLayout llshare;
    Context mContxt;
    
    public ProgressBar pbLoader;
    
    public Utils utils;

    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_full_screen_view);
        this.mContxt = this;
        getWindow().setFlags(512, 512);
        this.fullImageView = (ImageView) findViewById(R.id.imgFullscreen);
        this.llSetWallpaper = (LinearLayout) findViewById(R.id.llSetWallpaper);
        this.llDownloadWallpaper = (LinearLayout) findViewById(R.id.llDownloadWallpaper);
        this.llshare = (LinearLayout) findViewById(R.id.llShare);
        this.pbLoader = (ProgressBar) findViewById(R.id.pbLoader);
        this.utils = new Utils(this.mContxt);
        this.llSetWallpaper.setOnClickListener(this);
        this.llDownloadWallpaper.setOnClickListener(this);
        this.llshare.setOnClickListener(this);
        this.llSetWallpaper.getBackground().setAlpha(70);
        this.llDownloadWallpaper.getBackground().setAlpha(70);
        this.llshare.getBackground().setAlpha(70);
        getIntent();
        String stringExtra = getIntent().getStringExtra("image_name");
        if (stringExtra != null) {
            fetchFullResolutionImage(stringExtra);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_unknown_error), 0).show();
        }
    }

    private void fetchFullResolutionImage(String str) {
        this.pbLoader.setVisibility(0);
        this.llSetWallpaper.setVisibility(8);
        this.llDownloadWallpaper.setVisibility(8);
        this.llshare.setVisibility(8);
        Glide.with(this.mContxt).asBitmap().load(AppConst.IMAGE_URL + str).listener(new RequestListener<Bitmap>() {
            public boolean onLoadFailed(GlideException glideException, Object obj, Target<Bitmap> target, boolean z) {
                FullScreenViewActivity.this.pbLoader.setVisibility(8);
                Toast.makeText(FullScreenViewActivity.this.mContxt, glideException.toString(), 1).show();
                return false;
            }

            public boolean onResourceReady(Bitmap bitmap, Object obj, Target<Bitmap> target, DataSource dataSource, boolean z) {
                Bitmap unused = FullScreenViewActivity.this.bitmap = bitmap;
                FullScreenViewActivity.this.pbLoader.setVisibility(8);
                FullScreenViewActivity.this.llSetWallpaper.setVisibility(0);
                FullScreenViewActivity.this.llDownloadWallpaper.setVisibility(0);
                FullScreenViewActivity.this.llshare.setVisibility(0);
                return false;
            }
        }).into(this.fullImageView);
    }

    private void adjustImageAspect(int i, int i2) {
        int i3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        if (i != 0 && i2 != 0) {
            if (Build.VERSION.SDK_INT >= 13) {
                Display defaultDisplay = getWindowManager().getDefaultDisplay();
                Point point = new Point();
                defaultDisplay.getSize(point);
                i3 = point.y;
            } else {
                i3 = getWindowManager().getDefaultDisplay().getHeight();
            }
            int floor = (int) Math.floor((((double) i) * ((double) i3)) / ((double) i2));
            layoutParams.width = floor;
            layoutParams.height = i3;
            Log.d(TAG, "Fullscreen image new dimensions: w = " + floor + ", h = " + i3);
            this.fullImageView.setLayoutParams(layoutParams);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (iArr.length > 0) {
            if (i == 100) {
                if (iArr[0] == 0) {
                    downloadWallpaer();
                } else {
                    Toast.makeText(this, "Until you grant the permission, we can't save wallpaper", 0).show();
                }
            }
            if (i == 200) {
                if (iArr[0] == 0) {
                    setWallpaper();
                } else {
                    Toast.makeText(this, "Until you grant the permission, we can't set wallpaper", 0).show();
                }
            }
            if (i != 300) {
                return;
            }
            if (iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(this, "Until you grant the permission, we can't share wallpaper", 0).show();
            } else {
                shareWallpaper();
            }
        } else {
            Toast.makeText(this, "Until you grant the permission, we can't set or share wallpaper", 0).show();
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llDownloadWallpaper:
                if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
                    return;
                } else {
                    downloadWallpaer();
                    return;
                }
            case R.id.llSetWallpaper:
                final ProgressDialog progressDialog = new ProgressDialog(this, 5);
                progressDialog.setTitle("Setting Wallpaper");
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Thread(new Runnable() {
                    public void run() {
                        FullScreenViewActivity.this.utils.setAsWallpaper(FullScreenViewActivity.this.bitmap);
                        FullScreenViewActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                if (!FullScreenViewActivity.this.isFinishing()) {
                                    try {
                                        progressDialog.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                }).start();
                return;
            case R.id.llShare:
                if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0) {
                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, Utils.PERMISSIONS_REQUEST_READ_EXT);
                    return;
                } else {
                    shareWallpaper();
                    return;
                }
            default:
                return;
        }
    }

    public void shareWallpaper() {
        final ProgressDialog progressDialog = new ProgressDialog(this, 5);
        progressDialog.setTitle("Sharing Wallpaper");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    FullScreenViewActivity.this.bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                    String insertImage = MediaStore.Images.Media.insertImage(FullScreenViewActivity.this.getContentResolver(), FullScreenViewActivity.this.bitmap, "Title", (String) null);
                    if (insertImage != null) {
                        final Uri parse = Uri.parse(insertImage);
                        FullScreenViewActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Intent intent = new Intent("android.intent.action.SEND");
                                intent.setType("image/jpeg");
                                intent.putExtra("android.intent.extra.STREAM", parse);
                                FullScreenViewActivity.this.startActivity(Intent.createChooser(intent, "Select"));
                                progressDialog.dismiss();
                            }
                        });
                        return;
                    }
                    FullScreenViewActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(FullScreenViewActivity.this.mContxt, "File not found! try again.", 1).show();
                        }
                    });
                } catch (Exception unused) {
                    FullScreenViewActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(FullScreenViewActivity.this.mContxt, "Some error occurred try again.", 1).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void setWallpaper() {
        final ProgressDialog progressDialog = new ProgressDialog(this, 5);
        progressDialog.setTitle("Setting Wallpaper");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                Utils.saveImage(FullScreenViewActivity.this.bitmap);
                FullScreenViewActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

    public void downloadWallpaer() {
        final ProgressDialog progressDialog = new ProgressDialog(this, 5);
        progressDialog.setTitle("Downloading Wallpaper");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                FullScreenViewActivity.this.utils.saveImageToSDCard(FullScreenViewActivity.this.bitmap);
                FullScreenViewActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }
}
