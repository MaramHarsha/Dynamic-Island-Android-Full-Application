package com.lock.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import com.dynamic.island.harsha.notification.R;

public class RipplePulseLayout extends FrameLayout {
    public AnimatorSet animatorSet;
    public Context mContext;
    public Paint paint;
    public RippleView rippleView;

    public class RippleView extends View {
        public float radius;

        public RippleView(Context context, Paint paint, float f) {
            super(context);
            RipplePulseLayout.this.mContext = context;
            RipplePulseLayout.this.paint = paint;
            this.radius = f;
        }

        public void onDraw(Canvas canvas) {
            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.radius, RipplePulseLayout.this.paint);
        }

        public void setRadius(float f) {
            this.radius = f;
            invalidate();
        }
    }

    public RipplePulseLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        setVisibility(0);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        if (isInEditMode() || getVisibility() == 8) {
            this.paint = null;
            this.rippleView = null;
            AnimatorSet animatorSet2 = this.animatorSet;
            if (animatorSet2 != null) {
                animatorSet2.cancel();
                this.animatorSet = null;
                return;
            }
            return;
        }
        float pixelToDp = (float) pixelToDp(this.mContext, 24);
        float pixelToDp2 = (float) pixelToDp(this.mContext, 30);
        int color = getResources().getColor(R.color.colorAccent);
        Paint paint2 = new Paint();
        this.paint = paint2;
        paint2.setColor(color);
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(4.0f);
        this.rippleView = new RippleView(this.mContext, this.paint, pixelToDp);
        int i2 = ((int) (4.0f + pixelToDp2)) * 2;
        LayoutParams layoutParams = new LayoutParams(i2, i2);
        layoutParams.gravity = 17;
        addView(this.rippleView, layoutParams);
        this.rippleView.setVisibility(8);
        this.animatorSet = new AnimatorSet();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.rippleView, "radius", new float[]{pixelToDp, pixelToDp2});
        ofFloat.setRepeatCount(-1);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.rippleView, "alpha", new float[]{0.0f, 1.0f, 0.0f});
        ofFloat2.setRepeatCount(-1);
        this.animatorSet.setDuration(2000);
        this.animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        this.animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
    }

    public static int pixelToDp(Context context, int i) {
        return (int) TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
    }
}
