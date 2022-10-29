package com.lock.Controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.lock.utils.Utils;
import com.dynamic.island.harsha.notification.R;

public class MobileDataController extends ButtonState {
    private final Context mContext;
    private Intent mobileDataIntent = null;
    private String networkName;
    private final TelephonyManager telephonyManager;

    public boolean hasSystemFeature() {
        return true;
    }

    public MobileDataController(Context context) {
        super(context);
        Resources resources;
        this.mContext = context;
        String lowerCase = Build.MANUFACTURER.toLowerCase();
        try {
            resources = context.getPackageManager().getResourcesForApplication("com.android.systemui");
        } catch (Exception unused) {
            resources = this.mContext.getResources();
        }
        int i = 0;
        Intent component = new Intent().setComponent(new ComponentName("com.android.phone", "com.android.phone.settings.MobileNetworkSettings"));
        if (Utils.checkIfActivityFound(this.mContext, component)) {
            this.mobileDataIntent = component;
        }
        Intent component2 = new Intent().setComponent(new ComponentName("com.android.phone", "com.android.phone.MobileNetworkSettings"));
        if (this.mobileDataIntent == null && Utils.checkIfActivityFound(this.mContext, component2)) {
            this.mobileDataIntent = component2;
        }
        Intent component3 = new Intent().setComponent(new ComponentName("com.android.phone", "com.android.phone.MSimMobileNetworkSettings"));
        if (this.mobileDataIntent == null && Utils.checkIfActivityFound(this.mContext, component3)) {
            this.mobileDataIntent = component3;
        }
        Intent component4 = new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity"));
        if (this.mobileDataIntent == null && Utils.checkIfActivityFound(this.mContext, component4) && Build.VERSION.SDK_INT < 28) {
            this.mobileDataIntent = component4;
        }
        Intent component5 = new Intent().setComponent(new ComponentName("com.lge.networksettings", "com.lge.networksettings.msim.DualSim"));
        if (this.mobileDataIntent == null && Utils.checkIfActivityFound(this.mContext, component5) && lowerCase.equalsIgnoreCase("lge")) {
            this.mobileDataIntent = component5;
        }
        Intent component6 = new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$NetworkDashboardActivity"));
        if (this.mobileDataIntent == null && Utils.checkIfActivityFound(this.mContext, component6) && lowerCase.equalsIgnoreCase("lge")) {
            this.mobileDataIntent = component6;
        }
        if (this.mobileDataIntent == null) {
            this.mobileDataIntent = new Intent("android.settings.DATA_USAGE_SETTINGS");
        }
        this.telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        try {
            if (lowerCase.equals("vivo")) {
                Resources resourcesForApplication = this.mContext.getPackageManager().getResourcesForApplication("com.vivo.upslide");
                i = resourcesForApplication.getIdentifier("com.vivo.upslide:string/vivo_switcher_apn", (String) null, (String) null);
                if (i != 0) {
                    this.networkName = resourcesForApplication.getString(i);
                    return;
                }
            } else if (lowerCase.equals("huawei")) {
                i = resources.getIdentifier("com.android.systemui:string/data_widget_name", (String) null, (String) null);
            } else if (lowerCase.contains("lg")) {
                i = resources.getIdentifier("com.android.systemui:string/quicksettings_data_NORMAL", (String) null, (String) null);
            } else if (!lowerCase.equals("infinix")) {
                i = resources.getIdentifier("com.android.systemui:string/mobile", (String) null, (String) null);
                if (!lowerCase.contains("tecno")) {
                    if (lowerCase.equals("samsung")) {
                        i = resources.getIdentifier("com.android.systemui:string/mobile_data_title", (String) null, (String) null);
                    } else if (lowerCase.equals("zte")) {
                        i = resources.getIdentifier("com.android.systemui:string/qs_data_label", (String) null, (String) null);
                    }
                }
            }
            if (i != 0) {
                this.networkName = resources.getString(i);
                return;
            }
            int identifier = resources.getIdentifier("com.android.systemui:string/accessibility_cell_data", (String) null, (String) null);
            this.networkName = resources.getString(identifier == 0 ? resources.getIdentifier("com.android.systemui:string/mobile_data", (String) null, (String) null) : identifier);
            this.networkName = "~" + this.networkName;
            setButtonState();
        } catch (Exception unused2) {
            StringBuilder buildString = buildString("~");
            buildString.append(((TelephonyManager) this.mContext.getSystemService("phone")).getNetworkOperatorName());
            this.networkName = buildString.toString();
        }
    }

    public StringBuilder buildString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public Intent getIntent() {
        return this.mobileDataIntent;
    }

    public String getName() {
        return this.networkName;
    }

    public void setButtonState() {
        this.title = this.mContext.getResources().getString(R.string.quick_settings_cellular_detail_title);
        this.iconOff = AppCompatResources.getDrawable(this.mContext, R.drawable.off);
        this.iconOn = AppCompatResources.getDrawable(this.mContext, R.drawable.on);
        this.isStateOn = Boolean.valueOf(isDataConnected(this.mContext));
        this.launchIntent = this.mobileDataIntent;
    }

    public boolean isDataConnected(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.READ_PHONE_STATE") != 0) {
                    return false;
                }
                return this.telephonyManager.isDataEnabled();
            } else if (Settings.Global.getInt(context.getContentResolver(), "mobile_data", 0) != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception unused) {
            if (Settings.Global.getInt(context.getContentResolver(), "mobile_data", 0) != 0) {
                return true;
            }
            return false;
        }
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageDrawable(this.iconOn);
        } else {
            lottieAnimationView.setImageDrawable(this.iconOff);
        }
        textView.setText(this.title);
        textView.setText("");
    }

    public boolean getState() {
        return isDataConnected(this.mContext);
    }
}
