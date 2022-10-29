package com.lock.Controllers;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;
import java.util.ArrayList;
import java.util.Locale;

public class ScreenTimeController extends ButtonState {
    private Context context;
    public final ArrayList<UsageEvents.Event> eventArrayList = new ArrayList<>();
    private Intent intent = new Intent();
    private final UsageStatsManager usageStatsManager;
    public long y;

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public ScreenTimeController(Context context2) {
        super(context2);
        this.context = context2;
        this.usageStatsManager = (UsageStatsManager) context2.getSystemService("usagestats");
        if (Build.MANUFACTURER.equals("samsung")) {
            this.intent.setComponent(new ComponentName("com.samsung.android.forest", "com.samsung.android.forest.home.DefaultActivity"));
        } else {
            this.intent.setComponent(new ComponentName("com.google.android.apps.wellbeing", "com.google.android.apps.wellbeing.settings.LauncherActivity"));
        }
        if (context2.getPackageManager().resolveActivity(this.intent, 0) == null) {
            this.intent.setComponent((ComponentName) null);
            this.intent.setAction("android.settings.DISPLAY_SETTINGS");
        }
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
        if (z) {
            lottieAnimationView.setImageResource(R.drawable.on);
        } else {
            lottieAnimationView.setImageResource(R.drawable.off);
        }
    }

    public Intent getIntent() {
        return this.intent;
    }

    public String getName() {
        return this.context.getString(R.string.screen_time);
    }

    public void getUsageStats() {
        UsageEvents usageEvents;
        int i;
        long currentTimeMillis = System.currentTimeMillis();
        long currentTimeMillis2 = System.currentTimeMillis() - 604800000;
        if (currentTimeMillis - this.y > 180000) {
            this.y = currentTimeMillis;
            try {
                usageEvents = this.usageStatsManager.queryEvents(currentTimeMillis2, currentTimeMillis);
            } catch (Throwable unused) {
                usageEvents = null;
            }
            if (usageEvents == null || !usageEvents.hasNextEvent()) {
                Context context2 = this.context;
                Toast.makeText(context2, context2.getString(R.string.screen_time_no_permission), 0).show();
                return;
            }
            long j = 0;
            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                if (usageEvents.getNextEvent(event) && (event.getEventType() == 1 || event.getEventType() == 2)) {
                    this.eventArrayList.add(event);
                }
            }
            int i2 = 0;
            while (i2 < this.eventArrayList.size() - 1) {
                UsageEvents.Event event2 = this.eventArrayList.get(i2);
                i2++;
                UsageEvents.Event event3 = this.eventArrayList.get(i2);
                if (event2.getEventType() == 1 && event3.getEventType() == 2 && event2.getPackageName().equals(event3.getPackageName())) {
                    j = (event3.getTimeStamp() - event2.getTimeStamp()) + j;
                }
            }
            this.eventArrayList.clear();
            int i3 = (int) (j / 1000);
            if (i3 >= 3600) {
                i = i3 / 3600;
                i3 -= i * 3600;
            } else {
                i = 0;
            }
            int i4 = i3 >= 60 ? i3 / 60 : 0;
            if (i > 0) {
                if (Build.VERSION.SDK_INT >= 24) {
                    MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.SHORT).formatMeasures(new Measure[]{new Measure(Integer.valueOf(i), MeasureUnit.HOUR), new Measure(Integer.valueOf(i4), MeasureUnit.MINUTE)});
                }
            } else if (Build.VERSION.SDK_INT >= 24) {
                MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.SHORT).format(new Measure(Integer.valueOf(i4), MeasureUnit.MINUTE));
            }
        }
    }
}
