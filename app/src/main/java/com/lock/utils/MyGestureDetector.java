package com.lock.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.lock.services.OnPanelItemClickListner;

public class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    public OnPanelItemClickListner onPanelItemClickListner;

    public void setOnPanelItemClickListner(OnPanelItemClickListner onPanelItemClickListner2) {
        this.onPanelItemClickListner = onPanelItemClickListner2;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        try {
            float y = motionEvent2.getY() - motionEvent.getY();
            if (Math.abs(y) > 100.0f && Math.abs(f2) > 100.0f) {
                if (y > 0.0f) {
                    this.onPanelItemClickListner.onItemClicked(true, false);
                } else {
                    this.onPanelItemClickListner.onItemClicked(false, false);
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
