package com.lock.widgets;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

public class AutoMarqueeTextView extends AppCompatTextView {
    public boolean isEnableMarquee = false;

    public AutoMarqueeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setSelected(true);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setSelected(false);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        onVisibilityAggregated(true);
    }

    public void onVisibilityAggregated(boolean z) {
        if (Build.VERSION.SDK_INT >= 24) {
            super.onVisibilityAggregated(z);
        }
        if (z != this.isEnableMarquee) {
            this.isEnableMarquee = z;
            post(new AutoMarqueeRunable(this));
        }
    }

    public static final class AutoMarqueeRunable implements Runnable {
        public final AutoMarqueeTextView autoMarqueeTextView;

        public AutoMarqueeRunable(AutoMarqueeTextView autoMarqueeTextView2) {
            this.autoMarqueeTextView = autoMarqueeTextView2;
        }

        public final void run() {
            AutoMarqueeTextView autoMarqueeTextView2 = this.autoMarqueeTextView;
            if (autoMarqueeTextView2.isEnableMarquee) {
                autoMarqueeTextView2.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            } else {
                autoMarqueeTextView2.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }
}
