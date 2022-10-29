package com.lock.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import com.dynamic.island.harsha.notification.R;

public class ExpandingItemLayout extends LinearLayout {
    public static final int f11403m = 0;
    public boolean isVisible;

    public class onItemClickListner implements OnClickListener {
        public onItemClickListner() {
        }

        public void onClick(View view) {
            view.setEnabled(false);
            ExpandingItemLayout.this.expandItem(true);
        }
    }

    public class AnimationEndListener extends AnimatorListenerAdapter {
        public AnimationEndListener() {
        }

        public void onAnimationEnd(Animator animator) {
            ExpandingItemLayout.this.b();
            ExpandingItemLayout.this.getLayoutParams().height = -2;
        }
    }

    public ExpandingItemLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void expandItem(boolean z) {
        int i;
        if (z) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt != this && (childAt instanceof ExpandingItemLayout)) {
                    ExpandingItemLayout expandingItemLayout = (ExpandingItemLayout) childAt;
                    if (expandingItemLayout.isVisible) {
                        expandingItemLayout.expandItem(false);
                    }
                }
            }
        }
        this.isVisible = !this.isVisible;
        findViewById(R.id.expand).animate().rotationBy(this.isVisible ? -180.0f : 180.0f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(200).withEndAction(new MyExpendRunable(this));
        int height = getHeight();
        if (this.isVisible) {
            b();
            i = getChildAt(0).getHeight();
            int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getChildAt(0).getWidth(), 1073741824);
            for (int i3 = 1; i3 < getChildCount(); i3++) {
                getChildAt(i3).measure(makeMeasureSpec, 0);
                i += getChildAt(i3).getMeasuredHeight();
            }
        } else {
            i = getChildAt(0).getHeight();
        }
        getLayoutParams().height = height;
        AccelerateInterpolator accelerateInterpolator = new AccelerateInterpolator(this.isVisible ? 8.0f : 1.0f);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.addUpdateListener(new MyAnimatorUpdateListner(this, accelerateInterpolator, height, i));
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.setDuration(250);
        if (!this.isVisible) {
            ofFloat.addListener(new AnimationEndListener());
        }
        ofFloat.start();
    }

    public final void b() {
        int i = this.isVisible ? 0 : 8;
        for (int i2 = 1; i2 < getChildCount(); i2++) {
            getChildAt(i2).setVisibility(i);
        }
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        getChildAt(0).setOnClickListener(new onItemClickListner());
    }

    public static final class MyExpendRunable implements Runnable {
        public final ExpandingItemLayout expandingItemLayout;

        public MyExpendRunable(ExpandingItemLayout expandingItemLayout2) {
            this.expandingItemLayout = expandingItemLayout2;
        }

        public final void run() {
            this.expandingItemLayout.getChildAt(0).setEnabled(true);
        }
    }
}
