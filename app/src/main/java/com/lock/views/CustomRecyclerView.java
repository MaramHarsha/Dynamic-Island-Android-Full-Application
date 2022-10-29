package com.lock.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerView extends RecyclerView {
    int firstCompleteElementPosition = 0;
    int lastCompleteElementPosition = 0;
    public int overScrollDirection;
    int preDirections = 0;

    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CustomRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.preDirections = 0;
    }

    public void onScrolled(int i, int i2) {
        super.onScrolled(i, i2);
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
        if (linearLayoutManager != null) {
            this.firstCompleteElementPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            this.lastCompleteElementPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        }
        this.preDirections = 0;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            return super.onTouchEvent(motionEvent);
        }
        if (this.preDirections == this.overScrollDirection || (this.lastCompleteElementPosition == getChildCount() - 1 && this.firstCompleteElementPosition == 0)) {
            this.preDirections = 0;
        } else {
            this.preDirections = this.overScrollDirection;
        }
        return super.onTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }
}
