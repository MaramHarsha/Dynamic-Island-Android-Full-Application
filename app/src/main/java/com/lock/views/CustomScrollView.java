package com.lock.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
    
    public long lastScrollUpdate = -1;
    private boolean mScrollable = true;

    
    public void onScrollEnd() {
    }

    private void onScrollStart() {
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public CustomScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private class ScrollStateHandler implements Runnable {
        private ScrollStateHandler() {
        }

        public void run() {
            if (System.currentTimeMillis() - CustomScrollView.this.lastScrollUpdate > 100) {
                long unused = CustomScrollView.this.lastScrollUpdate = -1;
                CustomScrollView.this.onScrollEnd();
                return;
            }
            CustomScrollView.this.postDelayed(this, 100);
        }
    }

    
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.lastScrollUpdate == -1) {
            onScrollStart();
            postDelayed(new ScrollStateHandler(), 100);
        }
        this.lastScrollUpdate = System.currentTimeMillis();
    }

    public void setScrollingEnabled(boolean z) {
        this.mScrollable = z;
    }

    public boolean isScrollable() {
        return this.mScrollable;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0 || action == 2) {
            return this.mScrollable && super.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mScrollable && super.onInterceptTouchEvent(motionEvent);
    }
}
