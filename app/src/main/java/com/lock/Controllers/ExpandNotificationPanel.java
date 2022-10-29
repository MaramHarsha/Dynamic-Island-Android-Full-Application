package com.lock.Controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.lock.entity.ButtonState;
import com.dynamic.island.harsha.notification.R;
import java.lang.reflect.InvocationTargetException;

public class ExpandNotificationPanel extends ButtonState {
    private Context context;

    public Intent getIntent() {
        return null;
    }

    public boolean getState() {
        return false;
    }

    public boolean hasSystemFeature() {
        return true;
    }

    public void setState(boolean z, LottieAnimationView lottieAnimationView, TextView textView, TextView textView2) {
    }

    public ExpandNotificationPanel(Context context2) {
        super(context2);
        this.context = context2;
    }

    public void openStatusBar() {
        try {
            Class.forName("android.app.StatusBarManager").getMethod("expandNotificationsPanel", new Class[0]).invoke(this.context.getSystemService("statusbar"), new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void closeStatusBar() {
        this.context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS").putExtra("noRespond", true));
    }

    public String getName() {
        return this.context.getResources().getString(R.string.open_system_shade);
    }
}
