package com.lock.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class StatusBarParentView extends FrameLayout {
    public StatusBarParentView(Context context) {
        super(context);
    }

    public StatusBarParentView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StatusBarParentView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public StatusBarParentView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }
}
