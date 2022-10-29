package com.lock.adaptar;

import android.view.View;
import android.widget.LinearLayout;
import com.lock.entity.Notification;

public final  class CustomNotificationAdapter$$ExternalSyntheticLambda2 implements View.OnClickListener {
    public final  CustomNotificationAdapter f$0;
    public final  Notification f$1;
    public final  int f$2;
    public final  LinearLayout f$3;

    public  CustomNotificationAdapter$$ExternalSyntheticLambda2(CustomNotificationAdapter customNotificationAdapter, Notification notification, int i, LinearLayout linearLayout) {
        this.f$0 = customNotificationAdapter;
        this.f$1 = notification;
        this.f$2 = i;
        this.f$3 = linearLayout;
    }

    public final void onClick(View view) {
        this.f$0.m87x5fb97e12(this.f$1, this.f$2, this.f$3, view);
    }
}
