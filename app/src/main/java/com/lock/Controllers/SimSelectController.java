package com.lock.Controllers;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;

public class SimSelectController extends ButtonState {
    int INVALID_SUBSCRIPTION_ID = -1;
    private final Context context;
    public final Intent intent;
    public final boolean isOnePlus = Build.MANUFACTURER.toLowerCase().contains("plus");
    public final SubscriptionManager subscriptionManager;

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public SimSelectController(Context context2, SubscriptionManager subscriptionManager2) {
        super(context2);
        this.context = context2;
        Intent intent2 = new Intent();
        this.intent = intent2;
        this.subscriptionManager = subscriptionManager2;
        PackageManager packageManager = context2.getPackageManager();
        intent2.setComponent(new ComponentName("com.qualcomm.qti.simsettings", "com.qualcomm.qti.simsettings.SimSettingsActivity"));
        if (packageManager.resolveActivity(intent2, 0) == null) {
            intent2.setComponent(new ComponentName("com.sec.android.app.simsettingmgr", "com.sec.android.app.simsettingmgr.NetworkManagement"));
            if (packageManager.resolveActivity(intent2, 0) == null) {
                intent2.setComponent(new ComponentName("com.samsung.android.app.telephonyui", "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.SimCardMgrActivity"));
                if (packageManager.resolveActivity(intent2, 0) == null) {
                    intent2.setComponent(new ComponentName("com.android.settings", "com.android.settings.DualCardSettings"));
                    if (packageManager.resolveActivity(intent2, 0) == null) {
                        intent2.setComponent((ComponentName) null);
                        if (Build.VERSION.SDK_INT >= 29) {
                            intent2.setAction("android.settings.AIRPLANE_MODE_SETTINGS");
                        } else {
                            intent2.setAction("android.settings.DATA_ROAMING_SETTINGS");
                        }
                    }
                }
            }
        }
    }

    
    public int getDefaultDataSubscriptionId() {
        int defaultDataSubscriptionId = 0;
        if (Build.VERSION.SDK_INT >= 24 && (defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId()) != -1) {
            return defaultDataSubscriptionId;
        }
        try {
            return ((Integer) Class.forName(this.subscriptionManager.getClass().getName()).getMethod("getDefaultDataSubId", new Class[0]).invoke(this.subscriptionManager, new Object[0])).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return this.INVALID_SUBSCRIPTION_ID;
        }
    }

    public String getActiveSim() {
        SubscriptionInfo activeSubscriptionInfo;
        int defaultDataSubscriptionId = getDefaultDataSubscriptionId();
        if (defaultDataSubscriptionId == this.INVALID_SUBSCRIPTION_ID) {
            return "Sim 1";
        }
        if (ActivityCompat.checkSelfPermission(this.context, "android.permission.READ_PHONE_STATE") != 0 || (activeSubscriptionInfo = this.subscriptionManager.getActiveSubscriptionInfo(defaultDataSubscriptionId)) == null || activeSubscriptionInfo.getCarrierName() == null) {
            return "No Permission";
        }
        return activeSubscriptionInfo.getCarrierName().toString();
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getName() {
        return this.context.getString(R.string.switch_sim);
    }
}
