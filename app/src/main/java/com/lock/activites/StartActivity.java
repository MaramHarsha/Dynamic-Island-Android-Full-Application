package com.lock.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.lock.utils.Constants;
import com.dynamic.island.harsha.notification.R;

public class StartActivity extends Activity {
    Context mContxt;

    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_start);
        this.mContxt = this;
        Window window = getWindow();
        window.setFlags(512, 512);
        try {
            window.addFlags(Integer.MIN_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            window.clearFlags(67108864);
        } catch (Resources.NotFoundException e2) {
            e2.printStackTrace();
        }
        try {
            window.setStatusBarColor(getResources().getColor(17170445));
        } catch (Resources.NotFoundException e3) {
            e3.printStackTrace();
        }
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setStatusBarColor(0);




        ((ImageView) findViewById(R.id.imageView)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoon_in));
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (Constants.checkAccessibilityEnabled(StartActivity.this.mContxt)) {
                    StartActivity startActivity = StartActivity.this;
                    if (startActivity.checkNotificationEnabled(startActivity.mContxt)) {
                        StartActivity.this.startActivity(new Intent(StartActivity.this.mContxt, HomeActivity.class));
                        StartActivity.this.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
                        StartActivity.this.finish();
                        return;
                    }
                }
                StartActivity.this.startActivity(new Intent(StartActivity.this.mContxt, PermissionsActivity.class));
                StartActivity.this.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
                StartActivity.this.finish();
            }
        }, 2000);
    }

    public boolean checkNotificationEnabled(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners").contains(context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
