package com.view;

import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

class SpringBackLayoutHelper {
    private int f6429a;
    float f6430b;
    float f6431c;
    int f6432d = -1;
    int f6433e;
    int f6434f;
    private ViewGroup g;

    public SpringBackLayoutHelper(ViewGroup viewGroup, int i) {
        this.g = viewGroup;
        this.f6434f = i;
        this.f6429a = ViewConfiguration.get(viewGroup.getContext()).getScaledTouchSlop();
    }

    public void a(MotionEvent motionEvent) {
        int findPointerIndex;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            int i = 1;
            if (actionMasked != 1 && actionMasked == 2) {
                int i2 = this.f6432d;
                if (i2 != -1 && (findPointerIndex = motionEvent.findPointerIndex(i2)) >= 0) {
                    float y = motionEvent.getY(findPointerIndex) - this.f6430b;
                    float x = motionEvent.getX(findPointerIndex) - this.f6431c;
                    if (Math.abs(x) > ((float) this.f6429a) || Math.abs(y) > ((float) this.f6429a)) {
                        if (Math.abs(x) <= Math.abs(y)) {
                            i = 2;
                        }
                        this.f6433e = i;
                    }
                } else {
                    return;
                }
            }
            this.f6433e = 0;
            this.g.requestDisallowInterceptTouchEvent(false);
            return;
        }
        int pointerId = motionEvent.getPointerId(0);
        this.f6432d = pointerId;
        int findPointerIndex2 = motionEvent.findPointerIndex(pointerId);
        if (findPointerIndex2 >= 0) {
            this.f6430b = motionEvent.getY(findPointerIndex2);
            this.f6431c = motionEvent.getX(findPointerIndex2);
            this.f6433e = 0;
        }
    }
}
