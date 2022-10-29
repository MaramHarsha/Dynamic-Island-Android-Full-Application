package com.lock.adaptar;

import android.widget.LinearLayout;
import com.lock.entity.Notification;

public final  class CustomNotificationAdapter$$ExternalSyntheticLambda1 implements Runnable {
    public final  CustomNotificationAdapter f$0;
    public final  Notification f$1;
    public final  int f$2;
    public final  LinearLayout f$3;

    public  CustomNotificationAdapter$$ExternalSyntheticLambda1(CustomNotificationAdapter customNotificationAdapter, Notification notification, int i, LinearLayout linearLayout) {
        this.f$0 = customNotificationAdapter;
        this.f$1 = notification;
        this.f$2 = i;
        this.f$3 = linearLayout;
    }

    public final void run() {
        this.f$0.m86x9ccd14b3(this.f$1, this.f$2, this.f$3);
    }
}
