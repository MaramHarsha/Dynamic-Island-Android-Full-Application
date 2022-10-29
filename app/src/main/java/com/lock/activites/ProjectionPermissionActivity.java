package com.lock.activites;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.widget.Toast;

public class ProjectionPermissionActivity extends Activity {
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1 && i2 != -1) {
            Toast.makeText(this, "Permission not granted for screen blur", 1).show();
        }
        finish();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            startActivityForResult(((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent(), 1);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "Unfortunately, screen recording isn't supported on your device.", 1).show();
        }
    }
}
