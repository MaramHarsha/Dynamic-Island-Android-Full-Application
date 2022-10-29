package com.lock.widgets;

import android.animation.ValueAnimator;
import android.view.animation.Interpolator;
import com.google.android.material.math.MathUtils;
import java.util.Objects;

public final class MyAnimatorUpdateListner implements ValueAnimator.AnimatorUpdateListener {
    public final ExpandingItemLayout a;
    public final Interpolator b;
    public final int f10532c;
    public final int f10533d;

    public MyAnimatorUpdateListner(ExpandingItemLayout expandingItemLayout, Interpolator interpolator, int i, int i2) {
        this.a = expandingItemLayout;
        this.b = interpolator;
        this.f10532c = i;
        this.f10533d = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ExpandingItemLayout expandingItemLayout = this.a;
        Interpolator interpolator = this.b;
        int i = this.f10532c;
        int i2 = this.f10533d;
        Objects.requireNonNull(expandingItemLayout);
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float interpolation = interpolator.getInterpolation(expandingItemLayout.isVisible ? floatValue : 1.0f - floatValue);
        for (int i3 = 1; i3 < expandingItemLayout.getChildCount(); i3++) {
            expandingItemLayout.getChildAt(i3).setAlpha(interpolation);
        }
        expandingItemLayout.getLayoutParams().height = (int) MathUtils.lerp((float) i, (float) i2, floatValue);
        expandingItemLayout.requestLayout();
    }
}
