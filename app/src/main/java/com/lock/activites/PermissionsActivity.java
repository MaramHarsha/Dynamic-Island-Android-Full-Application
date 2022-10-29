package com.lock.activites;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.lock.SplashLaunchActivity;
import com.lock.services.MAccessibilityService;
import com.lock.services.NotificationService;
import com.lock.utils.Constants;
import com.nordan.dialog.Animation;
import com.nordan.dialog.NordanAlertDialog;
import com.nordan.dialog.NordanAlertDialogListener;
import com.dynamic.island.harsha.notification.R;
import java.util.List;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionsActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    private boolean bluetoothPermissionGranted;
    TextView lockScreen;
    Context mContxt;
    
    public Button next_btn;
    NotificationManager notificationManager;
    private ToggleButton toggle_enable;
    private ToggleButton toggle_notification;
    TextView tv_enable_notification;
    Typeface typefaceBold;

    public void onRationaleAccepted(int i) {
    }

    public void onRationaleDenied(int i) {
    }

    
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_permission);


        //fb ads call
        SplashLaunchActivity.FBInterstitialAdCall(this);


        //Mix Banner Ads Call
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.btm10);
        RelativeLayout adContainer2 = (RelativeLayout) findViewById(R.id.ads2);
        ImageView OwnBannerAds = (ImageView) findViewById(R.id.bannerads);
        SplashLaunchActivity.MixBannerAdsCall(this, adContainer, adContainer2, OwnBannerAds);


        this.mContxt = this;
        setFullScreen();
        this.notificationManager = (NotificationManager) this.mContxt.getSystemService("notification");
        this.lockScreen = (TextView) findViewById(R.id.textViewLockscreen);
        this.tv_enable_notification = (TextView) findViewById(R.id.tv_enable_notification);
        this.next_btn = (Button) findViewById(R.id.next_btn);
        this.toggle_enable = (ToggleButton) findViewById(R.id.toggle_enable);
        this.toggle_notification = (ToggleButton) findViewById(R.id.toggle_notification_access);
        Typeface createFromAsset = Typeface.createFromAsset(this.mContxt.getAssets(), "roboto_medium.ttf");
        this.typefaceBold = createFromAsset;
        this.lockScreen.setTypeface(createFromAsset);
        this.tv_enable_notification.setTypeface(this.typefaceBold);
        ((TextView) findViewById(R.id.tv_enable__bluetooth_access)).setTypeface(this.typefaceBold);
        if (Constants.checkAccessibilityEnabled(this.mContxt)) {
            this.toggle_enable.setChecked(true);
        } else {
            this.toggle_enable.setChecked(false);
        }
        this.toggle_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    new NordanAlertDialog.Builder(PermissionsActivity.this).setAnimation(Animation.SLIDE).isCancellable(false).setTitle("Accessibility Permission Disclosure & Consent").setMessage("This app needs to be activated in accessibility service to show dynamic island view on top of mobile screen.\n\n1- This application do not collect or share any user data.\n\n2- This application do not store any sort of user data.").setPositiveBtnText("Agree").setNegativeBtnText("Cancel").setIcon((int) R.drawable.allert, false).onPositiveClicked(new PermissionsActivityExternalSynthetic1(this)).onNegativeClicked(new PermissionsActivityExternalSynthetic2(this)).build().show();
                    return;
                }
                PermissionsActivity.stopService(PermissionsActivity.this.mContxt, 0);
                PermissionsActivity.this.next_btn.setVisibility(8);
            }

            public  void m82x9e5f95c4() {
                PermissionsActivity.this.enableLock();
            }

            public  void m83xa5c4cae3() {
                PermissionsActivity.this.finish();
            }
        });
        if (Constants.getNotif(this.mContxt)) {
            this.toggle_notification.setChecked(true);
        } else {
            this.toggle_notification.setChecked(false);
        }
        this.toggle_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    new NordanAlertDialog.Builder(PermissionsActivity.this).setAnimation(Animation.SLIDE).isCancellable(false).setTitle("Notification Permission Disclosure & Consent").setMessage("You need to allow this app read notification permission to show media controls or notifications on Dynamic island view.\n\n1- This application do not collect or share any user data.\n\n2- This application do not store any sort of user data.").setPositiveBtnText("Agree").setNegativeBtnText("Cancel").setIcon((int) R.drawable.allert, false).onPositiveClicked(new PermissionsActivity2ExternalSynthetic1(this, z)).onNegativeClicked(new PermissionsActivity2ExternalSynthetic2(this)).build().show();
                }
            }

            public  void m84x9e5f95c5(boolean z) {
                try {
                    Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                    Bundle bundle = new Bundle();
                    String str = PermissionsActivity.this.mContxt.getPackageName() + "/" + NotificationService.class.getName();
                    bundle.putString(":settings:fragment_args_key", str);
                    intent.putExtra(":settings:fragment_args_key", str);
                    intent.putExtra(":settings:show_fragment_args", bundle);
                    PermissionsActivity permissionsActivity = PermissionsActivity.this;
                    permissionsActivity.startActivity(intent, ActivityOptions.makeCustomAnimation(permissionsActivity.mContxt, R.anim.fade_in, R.anim.fade_out).toBundle());
                } catch (Exception unused) {
                    Toast.makeText(PermissionsActivity.this.mContxt, "Notification service activity not found.\nPlease grant permission manually", 1).show();
                }
                Constants.setNotif(PermissionsActivity.this.mContxt, z);
            }

            public  void m85xa5c4cae4() {
                PermissionsActivity.this.finish();
            }
        });
        this.next_btn.setOnClickListener(new PermissionsActivity$$ExternalSyntheticLambda0(this));
        if (Build.VERSION.SDK_INT >= 31) {
            findViewById(R.id.enable__bluetooth_access_rl).setVisibility(0);
            if (ActivityCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_CONNECT") != 0) {
                ((ToggleButton) findViewById(R.id.toggle_bluetooth_access)).setChecked(false);
                this.bluetoothPermissionGranted = false;
            } else {
                ((ToggleButton) findViewById(R.id.toggle_bluetooth_access)).setChecked(true);
                this.bluetoothPermissionGranted = true;
            }
            ((ToggleButton) findViewById(R.id.toggle_bluetooth_access)).setOnCheckedChangeListener(new PermissionsActivity$$ExternalSyntheticLambda1(this));
            return;
        }
        this.bluetoothPermissionGranted = true;
    }

    public  void m80lambda$onCreate$0$comlockactivitesPermissionsActivity(View view) {
        startActivity(new Intent(this.mContxt, HomeActivity.class));
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
        finish();
    }

    public  void m81lambda$onCreate$1$comlockactivitesPermissionsActivity(CompoundButton compoundButton, boolean z) {
        EasyPermissions.requestPermissions((Activity) this, getString(R.string.blutooth_permission_txt), 16, Constants.BLUETOOTH_PERMISSION);
    }

    private void setFullScreen() {
        getWindow().getDecorView().setSystemUiVisibility(8192);
    }

    @AfterPermissionGranted(15)
    public void accessPhoneStatePermision() {
        if (!Constants.hasPermissions(this.mContxt, Constants.PHONE_STATE_PERMISSION)) {
            EasyPermissions.requestPermissions((Activity) this, getString(R.string.permission_txt), 15, Constants.PHONE_STATE_PERMISSION);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        EasyPermissions.onRequestPermissionsResult(i, strArr, iArr, this);
    }

    public void onPermissionsGranted(int i, List<String> list) {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.BLUETOOTH_CONNECT") == 0) {
            this.bluetoothPermissionGranted = true;
        }
    }

    public void onPermissionsDenied(int i, List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied((Activity) this, list)) {
            new AppSettingsDialog.Builder((Activity) this).build().show();
        } else {
            Toast.makeText(this.mContxt, "Required permission is not granted.!", 1).show();
        }
    }

    public void onResume() {
        if (!checkNotificationEnabled(this.mContxt)) {
            this.toggle_notification.setChecked(false);
            Constants.setNotif(this.mContxt, false);
        }
        boolean checkAccessibilityEnabled = Constants.checkAccessibilityEnabled(this.mContxt);
        if (checkAccessibilityEnabled) {
            Constants.setControlEnabled(this.mContxt, true);
            this.toggle_enable.setChecked(true);
        } else {
            Constants.setControlEnabled(this.mContxt, false);
            this.toggle_enable.setChecked(false);
        }
        if (this.bluetoothPermissionGranted && checkAccessibilityEnabled && checkNotificationEnabled(this.mContxt)) {
            this.next_btn.setVisibility(0);
        }
        super.onResume();
    }

    
    public void enableLock() {
        Intent intent = new Intent("com.samsung.accessibility.installed_service");
        if (intent.resolveActivity(this.mContxt.getPackageManager()) == null) {
            intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
        }
        Bundle bundle = new Bundle();
        String str = this.mContxt.getPackageName() + "/" + MAccessibilityService.class.getName();
        bundle.putString(":settings:fragment_args_key", str);
        intent.putExtra(":settings:fragment_args_key", str);
        intent.putExtra(":settings:show_fragment_args", bundle);
        StartPermissionActivity(this.mContxt, intent);
    }

    public void StartPermissionActivity(Context context, Intent intent) {
        context.startActivity(intent, ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, R.anim.fade_out).toBundle());
    }

    public static void stopService(Context context, int i) {
        try {
            context.startService(new Intent(context, MAccessibilityService.class).putExtra("com.control.center.intent.MESSAGE", i));
        } catch (Throwable unused) {
        }
    }

    public boolean checkNotificationEnabled(Context context) {
        try {
            return Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners").contains(context.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private class PermissionsActivityExternalSynthetic1 implements NordanAlertDialogListener {
        public PermissionsActivityExternalSynthetic1(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {

        }

        @Override
        public void onClick() {
            PermissionsActivity.this.enableLock();
        }
    }

    private class PermissionsActivityExternalSynthetic2 implements NordanAlertDialogListener {
        public PermissionsActivityExternalSynthetic2(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {

        }

        @Override
        public void onClick() {
            PermissionsActivity.this.finish();
        }
    }

    private class PermissionsActivity2ExternalSynthetic1 implements NordanAlertDialogListener {

        boolean zzz;

        public PermissionsActivity2ExternalSynthetic1(CompoundButton.OnCheckedChangeListener onCheckedChangeListener, boolean z) {

            zzz = z;

        }

        @Override
        public void onClick() {
            try {
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                Bundle bundle = new Bundle();
                String str = PermissionsActivity.this.mContxt.getPackageName() + "/" + NotificationService.class.getName();
                bundle.putString(":settings:fragment_args_key", str);
                intent.putExtra(":settings:fragment_args_key", str);
                intent.putExtra(":settings:show_fragment_args", bundle);
                PermissionsActivity permissionsActivity = PermissionsActivity.this;
                permissionsActivity.startActivity(intent, ActivityOptions.makeCustomAnimation(permissionsActivity.mContxt, R.anim.fade_in, R.anim.fade_out).toBundle());
            } catch (Exception unused) {
                Toast.makeText(PermissionsActivity.this.mContxt, "Notification service activity not found.\nPlease grant permission manually", 1).show();
            }
            Constants.setNotif(PermissionsActivity.this.mContxt, zzz);
        }
    }

    private class PermissionsActivity2ExternalSynthetic2 implements NordanAlertDialogListener {
        public PermissionsActivity2ExternalSynthetic2(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        }

        @Override
        public void onClick() {
            PermissionsActivity.this.finish();
        }
    }
}
