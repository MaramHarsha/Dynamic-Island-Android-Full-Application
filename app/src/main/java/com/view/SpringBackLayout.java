package com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Scroller;

import com.dynamic.island.harsha.notification.R;

import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ListViewCompat;
import java.util.ArrayList;
import java.util.List;

public class SpringBackLayout extends ViewGroup implements NestedScrollingParent3, NestedScrollingChild {
    private float D;
    private float E;
    private boolean F;
    private int J;
    private float f6426d;
    private float f6427e;
    private float f6428f;
    private float g;
    private boolean h;
    private int i;
    private int j;
    private final int[] m;
    private final int mDisplayHeightPixels;
    private final int mDisplayWidthPixels;
    private final NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    private int mScaledTouchSlop;
    private int mScrollOrientation;
    private int mScrollableViewId;
    private Scroller mScroller;
    private boolean mSpringBackEnabled;
    private SpringBackLayoutHelper mSpringBackLayoutHelper;
    private OnSpringBackListener mSpringBackListener;
    private int mSpringBackMode;
    private SpringScroller mSpringScroller;
    private List<SpringBackStageChangedListener> mStateListeners;
    private View mTarget;
    private final int[] n;
    private final int[] o;
    private boolean p;
    private boolean q;
    private float r;
    private float s;
    private float t;
    private int u;
    private int v;

    public interface OnSpringBackListener {
        boolean onSpringBack();
    }

    public interface SpringBackStageChangedListener {
        void a(SpringBackLayout springBackLayout, int i, int i2);

        void onStateChanged(int i, int i2);
    }

    public SpringBackLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    private void findTargetView() {
        if (this.mTarget == null) {
            int i2 = this.mScrollableViewId;
            if (i2 != -1) {
                this.mTarget = findViewById(i2);
            } else if (getChildCount() == 1) {
                this.mTarget = getChildAt(0);
            } else {
                throw new IllegalArgumentException("invalid target Id");
            }
        }
        if (this.mTarget != null) {
            if (isEnabled() && !ViewCompat.isNestedScrollingEnabled(this.mTarget)) {
                ViewCompat.setNestedScrollingEnabled(this.mTarget, true);
            }
            if (this.mTarget.getOverScrollMode() != 2) {
                this.mTarget.setOverScrollMode(2);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("fail to get target");
    }

    private boolean b() {
        return (this.mSpringBackMode & 2) != 0;
    }

    private boolean c() {
        return (this.mSpringBackMode & 1) != 0;
    }

    private boolean canScrollRightOrRight(int i2) {
        if (i2 != 2) {
            return !this.mTarget.canScrollHorizontally(1);
        }
        View view = this.mTarget;
        if (view instanceof ListView) {
            return !ListViewCompat.canScrollList((ListView) view, 1);
        }
        return !view.canScrollVertically(1);
    }

    private boolean canScrollLeftOrUp(int i2) {
        if (i2 != 2) {
            return !this.mTarget.canScrollHorizontally(-1);
        }
        View view = this.mTarget;
        if (view instanceof ListView) {
            return !ListViewCompat.canScrollList((ListView) view, -1);
        }
        return !view.canScrollVertically(-1);
    }

    private boolean f(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (!canScrollLeftOrUp(2) && !canScrollRightOrRight(2)) {
            return false;
        }
        if (canScrollLeftOrUp(2) && canScrollRightOrRight(2)) {
            return b(motionEvent, actionMasked, 2);
        }
        if (canScrollRightOrRight(2)) {
            return c(motionEvent, actionMasked, 2);
        }
        return a(motionEvent, actionMasked, 2);
    }

    private void g(int i2) {
        a(0.0f, i2, true);
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.mScroller.computeScrollOffset()) {
            scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
            if (!this.mScroller.isFinished()) {
                postInvalidateOnAnimation();
            } else {
                b(0);
            }
        } else if (this.mSpringScroller.a()) {
            scrollTo(this.mSpringScroller.c(), this.mSpringScroller.d());
            if (!this.mSpringScroller.e()) {
                postInvalidateOnAnimation();
            } else {
                b(0);
            }
        }
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        return this.mNestedScrollingChildHelper.dispatchNestedFling(f, f2, z);
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreFling(f, f2);
    }

    public boolean dispatchNestedPreScroll(int i2, int i3, int[] iArr, int[] iArr2) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(i2, i3, iArr, iArr2);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0 && this.J == 2) {
            b(1);
        }
        boolean dispatchTouchEvent = super.dispatchTouchEvent(motionEvent);
        if (motionEvent.getActionMasked() == 1 && this.J != 2) {
            b(0);
        }
        return dispatchTouchEvent;
    }

    public int getSpringBackMode() {
        return this.mSpringBackMode;
    }

    public boolean isNestedScrollingEnabled() {
        return this.mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mSpringBackEnabled) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (!this.mScroller.isFinished() && actionMasked == 0) {
            this.mScroller.forceFinished(true);
        }
        if (!isEnabled() || !this.mScroller.isFinished() || this.p || this.q || ((Build.VERSION.SDK_INT >= 21 && this.mTarget.isNestedScrollingEnabled()) || (!c() && !b()))) {
            return false;
        }
        int i2 = this.mScrollOrientation;
        if ((i2 & 4) != 0) {
            a(motionEvent);
            if (c(2) && (this.mScrollOrientation & 1) != 0) {
                return false;
            }
            if (c(1) && (this.mScrollOrientation & 2) != 0) {
                return false;
            }
            if (c(2) || c(1)) {
                c(true);
            }
        } else {
            this.v = i2;
        }
        if (c(2)) {
            return e(motionEvent);
        }
        if (c(1)) {
            return b(motionEvent);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i2, int i3, int i4, int i5) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        this.mTarget.layout(paddingLeft, paddingTop, ((measuredWidth - getPaddingLeft()) - getPaddingRight()) + paddingLeft, ((measuredHeight - getPaddingTop()) - getPaddingBottom()) + paddingTop);
    }

    public void onMeasure(int i2, int i3) {
        try {
            findTargetView();
            int mode = MeasureSpec.getMode(i2);
            int mode2 = MeasureSpec.getMode(i3);
            int size = MeasureSpec.getSize(i2);
            int size2 = MeasureSpec.getSize(i3);
            measureChild(this.mTarget, i2, i3);
            if (size > this.mTarget.getMeasuredWidth()) {
                size = this.mTarget.getMeasuredWidth();
            }
            if (size2 > this.mTarget.getMeasuredHeight()) {
                size2 = this.mTarget.getMeasuredHeight();
            }
            if (mode != 1073741824) {
                size = this.mTarget.getMeasuredWidth();
            }
            if (mode2 != 1073741824) {
                size2 = this.mTarget.getMeasuredHeight();
            }
            setMeasuredDimension(size, size2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        return dispatchNestedFling(f, f2, z);
    }

    public boolean onNestedPreFling(View view, float f, float f2) {
        return dispatchNestedPreFling(f, f2);
    }

    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr, int i4) {
        if (this.mSpringBackEnabled) {
            int i5 = this.u == 2 ? 2 : 1;
            if (i4 == 0) {
                if (i3 > 0) {
                    float f = this.s;
                    if (f > 0.0f) {
                        float f2 = (float) i3;
                        if (f2 > f) {
                            iArr[1] = (int) f;
                            this.s = 0.0f;
                        } else {
                            this.s = f - f2;
                            iArr[1] = i3;
                        }
                        b(1);
                        a(c(this.s, i5), i5);
                    }
                }
                if (i3 < 0) {
                    float f3 = this.t;
                    float f4 = -f3;
                    if (f4 < 0.0f) {
                        float f5 = (float) i3;
                        if (f5 < f4) {
                            iArr[1] = (int) f3;
                            this.t = 0.0f;
                        } else {
                            this.t = f3 + f5;
                            iArr[1] = i3;
                        }
                        b(1);
                        a(-c(this.t, i5), i5);
                    }
                }
            } else if (i3 > 0 && this.s > 0.0f) {
                if (!this.F) {
                    this.F = true;
                    a(i5 == 2 ? this.E : this.D, i5, false);
                }
                if (this.mSpringScroller.a()) {
                    scrollTo(this.mSpringScroller.c(), this.mSpringScroller.d());
                }
            } else if (i3 < 0 && (-this.t) < 0.0f) {
                if (!this.F) {
                    this.F = true;
                    a(i5 == 2 ? this.E : this.D, i5, false);
                }
                if (this.mSpringScroller.a()) {
                    scrollTo(this.mSpringScroller.c(), this.mSpringScroller.d());
                }
            }
        }
        int[] iArr2 = this.m;
        if (a(i2 - iArr[0], i3 - iArr[1], iArr2, (int[]) null, i4)) {
            iArr[0] = iArr[0] + iArr2[0];
            iArr[1] = iArr[1] + iArr2[1];
        }
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5, int i6, int[] iArr) {
        int i7;
        boolean z = this.u == 2;
        int i8 = z ? iArr[1] : iArr[0];
        a(i2, i3, i4, i5, this.n, i6, iArr);
        if (this.mSpringBackEnabled) {
            int i9 = (z ? iArr[1] : iArr[0]) - i8;
            int i10 = z ? i5 - i9 : i4 - i9;
            if (i10 == 0) {
                int[] iArr2 = this.n;
                i7 = z ? iArr2[1] : iArr2[0];
            } else {
                i7 = i10;
            }
            int i11 = z ? 2 : 1;
            if (i7 >= 0 || !canScrollLeftOrUp(i11) || !c()) {
                if (i7 > 0 && canScrollRightOrRight(i11) && b()) {
                    if (i6 != 0) {
                        if (this.E != 0.0f || this.D != 0.0f) {
                            this.F = true;
                        } else if (this.t == 0.0f) {
                            float f = f(i11) - this.r;
                            if (this.j < 4) {
                                if (f <= ((float) Math.abs(i7))) {
                                    this.r += f;
                                    iArr[1] = (int) (((float) iArr[1]) + f);
                                } else {
                                    this.r += (float) Math.abs(i7);
                                    iArr[1] = iArr[1] + i10;
                                }
                                b(2);
                                a(-c(this.r, i11), i11);
                                this.j++;
                            }
                        }
                    } else if (this.mSpringScroller.e()) {
                        this.t += (float) Math.abs(i7);
                        b(1);
                        a(-c(this.t, i11), i11);
                        iArr[1] = iArr[1] + i10;
                    }
                }
            } else if (i6 != 0) {
                if (this.E != 0.0f || this.D != 0.0f) {
                    this.F = true;
                } else if (this.s == 0.0f) {
                    float f2 = f(i11) - this.r;
                    if (this.j < 4) {
                        if (f2 <= ((float) Math.abs(i7))) {
                            this.r += f2;
                            iArr[1] = (int) (((float) iArr[1]) + f2);
                        } else {
                            this.r += (float) Math.abs(i7);
                            iArr[1] = iArr[1] + i10;
                        }
                        b(2);
                        a(c(this.r, i11), i11);
                        this.j++;
                    }
                }
            } else if (this.mSpringScroller.e()) {
                this.s += (float) Math.abs(i7);
                b(1);
                a(c(this.s, i11), i11);
                iArr[1] = iArr[1] + i10;
            }
        }
    }

    public void onNestedScrollAccepted(View view, View view2, int i2, int i3) {
        if (this.mSpringBackEnabled) {
            int i4 = 2;
            boolean z = this.u == 2;
            if (!z) {
                i4 = 1;
            }
            float scrollY = (float) (z ? getScrollY() : getScrollX());
            if (i3 != 0) {
                if (scrollY == 0.0f) {
                    this.r = 0.0f;
                } else {
                    this.r = d(Math.abs(scrollY), i4);
                }
                this.p = true;
                this.j = 0;
            } else {
                if (scrollY == 0.0f) {
                    this.s = 0.0f;
                    this.t = 0.0f;
                } else if (scrollY < 0.0f) {
                    this.s = d(Math.abs(scrollY), i4);
                    this.t = 0.0f;
                } else {
                    this.s = 0.0f;
                    this.t = d(Math.abs(scrollY), i4);
                }
                this.q = true;
            }
            this.E = 0.0f;
            this.D = 0.0f;
            this.F = false;
            this.mSpringScroller.b();
        }
        onNestedScrollAccepted(view, view2, i2);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i2, int i3, int i4, int i5) {
        super.onScrollChanged(i2, i3, i4, i5);
        for (SpringBackStageChangedListener a : this.mStateListeners) {
            a.a(this, i2 - i4, i3 - i5);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0034, code lost:
        if ((r4.mTarget instanceof androidx.core.widget.NestedScrollView) != false) goto L_0x0036;
     */
    public boolean onStartNestedScroll(View view, View view2, int i2, int i3) {
        if (this.mSpringBackEnabled) {
            this.u = i2;
            int i4 = 2;
            boolean z = i2 == 2;
            if (!z) {
                i4 = 1;
            }
            if ((i4 & this.mScrollOrientation) != 0 && onStartNestedScroll(view, view, i2)) {
                float scrollY = (float) (z ? getScrollY() : getScrollX());
                if (i3 != 0) {
                    if (scrollY != 0.0f) {
                    }
                }
            }
            return false;
        }
        this.mNestedScrollingChildHelper.dispatchNestedPreFling((float) i2, (float) i3);
        return true;
    }

    public void onStopNestedScroll(View view, int i2) {
        this.mNestedScrollingParentHelper.onStopNestedScroll(view, i2);
        stopNestedScroll(i2);
        if (this.mSpringBackEnabled) {
            int i3 = 1;
            boolean z = this.u == 2;
            if (z) {
                i3 = 2;
            }
            if (this.q) {
                this.q = false;
                float scrollY = (float) (z ? getScrollY() : getScrollX());
                if (!this.p && scrollY != 0.0f) {
                    g(i3);
                } else if (scrollY != 0.0f) {
                    b(2);
                }
            } else if (this.p) {
                this.p = false;
                if (this.F) {
                    if (this.mScroller.isFinished()) {
                        a(i3 == 2 ? this.E : this.D, i3, false);
                    }
                    postInvalidateOnAnimation();
                    return;
                }
                g(i3);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (!this.mScroller.isFinished() && actionMasked == 0) {
            this.mScroller.forceFinished(true);
        }
        if (isEnabled() && this.mScroller.isFinished() && !this.p && !this.q && (Build.VERSION.SDK_INT < 21 || !this.mTarget.isNestedScrollingEnabled())) {
            if (c(2)) {
                return f(motionEvent);
            }
            if (c(1)) {
                return c(motionEvent);
            }
        }
        return false;
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (!isEnabled() || !this.mSpringBackEnabled) {
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        View view = this.mTarget;
        if (view != null && z != ViewCompat.isNestedScrollingEnabled(view)) {
            ViewCompat.setNestedScrollingEnabled(this.mTarget, z);
        }
    }

    public void setNestedScrollingEnabled(boolean z) {
        this.mNestedScrollingChildHelper.setNestedScrollingEnabled(z);
    }

    public void setOnSpringListener(OnSpringBackListener onSpringBackListener) {
        this.mSpringBackListener = onSpringBackListener;
    }

    public void setScrollOrientation(int i2) {
        this.mScrollOrientation = i2;
        this.mSpringBackLayoutHelper.f6434f = i2;
    }

    public void setSpringBackEnable(boolean z) {
        this.mSpringBackEnabled = z;
    }

    public void setSpringBackMode(int i2) {
        this.mSpringBackMode = i2;
    }

    public void setTarget(View view) {
        this.mTarget = view;
        if (ViewCompat.isNestedScrollingEnabled(view)) {
            ViewCompat.setNestedScrollingEnabled(this.mTarget, true);
        }
    }

    public boolean startNestedScroll(int i2) {
        return this.mNestedScrollingChildHelper.startNestedScroll(i2);
    }

    public void stopNestedScroll() {
        this.mNestedScrollingChildHelper.stopNestedScroll();
    }

    public SpringBackLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.i = -1;
        this.j = 0;
        this.m = new int[2];
        this.n = new int[2];
        this.o = new int[2];
        this.mSpringBackEnabled = true;
        this.mStateListeners = new ArrayList();
        this.J = 0;
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        this.mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SpringBackLayout);
        this.mScrollableViewId = obtainStyledAttributes.getResourceId(2, -1);
        this.mScrollOrientation = obtainStyledAttributes.getInt(0, 2);
        this.mSpringBackMode = obtainStyledAttributes.getInt(3, 3);
        obtainStyledAttributes.recycle();
        this.mScroller = new Scroller(context);
        this.mSpringScroller = new SpringScroller();
        this.mSpringBackLayoutHelper = new SpringBackLayoutHelper(this, this.mScrollOrientation);
        setNestedScrollingEnabled(true);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        this.mDisplayWidthPixels = displayMetrics.widthPixels;
        this.mDisplayHeightPixels = displayMetrics.heightPixels;
    }

    private boolean b(MotionEvent motionEvent) {
        if (!canScrollLeftOrUp(1) && !canScrollRightOrRight(1)) {
            return false;
        }
        if (canScrollLeftOrUp(1) && !c()) {
            return false;
        }
        if (canScrollRightOrRight(1) && !b()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int i2 = this.i;
                    if (i2 == -1) {
                        Log.e("SpringBackLayout", "Got ACTION_MOVE event but don't have an active pointer id.");
                        return false;
                    }
                    int findPointerIndex = motionEvent.findPointerIndex(i2);
                    if (findPointerIndex < 0) {
                        Log.e("SpringBackLayout", "Got ACTION_MOVE event but have an invalid active pointer id.");
                        return false;
                    }
                    float x = motionEvent.getX(findPointerIndex);
                    boolean z = canScrollRightOrRight(1) && canScrollLeftOrUp(1);
                    if ((z || !canScrollLeftOrUp(1)) && (!z || x <= this.f6428f)) {
                        if (this.f6428f - x > ((float) this.mScaledTouchSlop) && !this.h) {
                            this.h = true;
                            b(1);
                            this.g = x;
                        }
                    } else if (x - this.f6428f > ((float) this.mScaledTouchSlop) && !this.h) {
                        this.h = true;
                        b(1);
                        this.g = x;
                    }
                } else if (actionMasked != 3 && actionMasked == 6) {
                    d(motionEvent);
                }
            }
            this.h = false;
            this.i = -1;
        } else {
            scrollTo(0, getScrollY());
            int pointerId = motionEvent.getPointerId(0);
            this.i = pointerId;
            this.h = false;
            int findPointerIndex2 = motionEvent.findPointerIndex(pointerId);
            if (findPointerIndex2 < 0) {
                return false;
            }
            this.f6428f = motionEvent.getX(findPointerIndex2);
        }
        return this.h;
    }

    private boolean c(int i2) {
        return this.v == i2;
    }

    private void c(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private boolean c(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (!canScrollLeftOrUp(1) && !canScrollRightOrRight(1)) {
            return false;
        }
        if (canScrollLeftOrUp(1) && canScrollRightOrRight(1)) {
            return b(motionEvent, actionMasked, 1);
        }
        if (canScrollRightOrRight(1)) {
            return c(motionEvent, actionMasked, 1);
        }
        return a(motionEvent, actionMasked, 1);
    }

    private void d(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.i) {
            this.i = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
        }
    }

    private boolean e(MotionEvent motionEvent) {
        if (!canScrollLeftOrUp(2) && !canScrollRightOrRight(2)) {
            return false;
        }
        if (canScrollLeftOrUp(2) && !c()) {
            return false;
        }
        if (canScrollRightOrRight(2) && !b()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int i2 = this.i;
                    if (i2 == -1) {
                        Log.e("SpringBackLayout", "Got ACTION_MOVE event but don't have an active pointer id.");
                        return false;
                    }
                    int findPointerIndex = motionEvent.findPointerIndex(i2);
                    if (findPointerIndex < 0) {
                        Log.e("SpringBackLayout", "Got ACTION_MOVE event but have an invalid active pointer id.");
                        return false;
                    }
                    float y = motionEvent.getY(findPointerIndex);
                    boolean z = canScrollRightOrRight(2) && canScrollLeftOrUp(2);
                    if ((z || !canScrollLeftOrUp(2)) && (!z || y <= this.f6426d)) {
                        if (this.f6426d - y > ((float) this.mScaledTouchSlop) && !this.h) {
                            this.h = true;
                            b(1);
                            this.f6427e = y;
                        }
                    } else if (y - this.f6426d > ((float) this.mScaledTouchSlop) && !this.h) {
                        this.h = true;
                        b(1);
                        this.f6427e = y;
                    }
                } else if (actionMasked != 3 && actionMasked == 6) {
                    d(motionEvent);
                }
            }
            this.h = false;
            this.i = -1;
        } else {
            scrollTo(getScrollX(), 0);
            int pointerId = motionEvent.getPointerId(0);
            this.i = pointerId;
            this.h = false;
            int findPointerIndex2 = motionEvent.findPointerIndex(pointerId);
            if (findPointerIndex2 < 0) {
                return false;
            }
            this.f6426d = motionEvent.getY(findPointerIndex2);
        }
        return this.h;
    }

    private float f(int i2) {
        return b(1.0f, i2);
    }

    private float d(float f, int i2) {
        int i3 = i2 == 2 ? this.mDisplayHeightPixels : this.mDisplayWidthPixels;
        double d = (double) i3;
        return (float) (d - (Math.pow(d, 0.6666666666666666d) * Math.pow((double) (((float) i3) - (f * 3.0f)), 0.3333333333333333d)));
    }

    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return isEnabled();
    }

    private void a(MotionEvent motionEvent) {
        int i2;
        this.mSpringBackLayoutHelper.a(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked == 6) {
                            d(motionEvent);
                            return;
                        }
                        return;
                    }
                } else if (this.v == 0 && (i2 = this.mSpringBackLayoutHelper.f6433e) != 0) {
                    this.v = i2;
                    return;
                } else {
                    return;
                }
            }
            c(false);
            return;
        }
        SpringBackLayoutHelper springBackLayoutHelper = this.mSpringBackLayoutHelper;
        this.f6426d = springBackLayoutHelper.f6430b;
        this.f6428f = springBackLayoutHelper.f6431c;
        this.i = springBackLayoutHelper.f6432d;
        this.v = 0;
    }

    private boolean c(MotionEvent motionEvent, int i2, int i3) {
        int i4;
        float f;
        float f2;
        if (i2 == 0) {
            this.i = motionEvent.getPointerId(0);
            this.h = false;
        } else if (i2 != 1) {
            if (i2 == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.i);
                if (findPointerIndex < 0) {
                    Log.e("SpringBackLayout", "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                } else if (this.h) {
                    if (i3 == 2) {
                        float y = motionEvent.getY(findPointerIndex);
                        f = Math.signum(this.f6427e - y);
                        f2 = c(this.f6427e - y, i3);
                    } else {
                        float x = motionEvent.getX(findPointerIndex);
                        f = Math.signum(this.g - x);
                        f2 = c(this.g - x, i3);
                    }
                    float f3 = f * f2;
                    if (f3 <= 0.0f) {
                        return false;
                    }
                    b(true);
                    a(-f3, i3);
                }
            } else if (i2 == 3) {
                return false;
            } else {
                if (i2 == 5) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.i);
                    if (findPointerIndex2 < 0) {
                        Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid active pointer id.");
                        return false;
                    }
                    if (i3 == 2) {
                        float y2 = motionEvent.getY(findPointerIndex2) - this.f6426d;
                        i4 = motionEvent.getActionIndex();
                        if (i4 < 0) {
                            Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                            return false;
                        }
                        float y3 = motionEvent.getY(i4) - y2;
                        this.f6426d = y3;
                        this.f6427e = y3;
                    } else {
                        float x2 = motionEvent.getX(findPointerIndex2) - this.f6428f;
                        i4 = motionEvent.getActionIndex();
                        if (i4 < 0) {
                            Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                            return false;
                        }
                        float x3 = motionEvent.getX(i4) - x2;
                        this.f6428f = x3;
                        this.g = x3;
                    }
                    this.i = motionEvent.getPointerId(i4);
                } else if (i2 == 6) {
                    d(motionEvent);
                }
            }
        } else if (motionEvent.findPointerIndex(this.i) < 0) {
            Log.e("SpringBackLayout", "Got ACTION_UP event but don't have an active pointer id.");
            return false;
        } else {
            if (this.h) {
                this.h = false;
                g(i3);
            }
            this.i = -1;
            return false;
        }
        return true;
    }

    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(view, view2, i2);
        startNestedScroll(i2 & 2);
    }

    public void a(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
    }

    private boolean a(MotionEvent motionEvent, int i2, int i3) {
        int i4;
        float f;
        float f2;
        if (i2 == 0) {
            this.i = motionEvent.getPointerId(0);
            this.h = false;
        } else if (i2 != 1) {
            if (i2 == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.i);
                if (findPointerIndex < 0) {
                    Log.e("SpringBackLayout", "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                } else if (this.h) {
                    if (i3 == 2) {
                        float y = motionEvent.getY(findPointerIndex);
                        f = Math.signum(y - this.f6427e);
                        f2 = c(y - this.f6427e, i3);
                    } else {
                        float x = motionEvent.getX(findPointerIndex);
                        f = Math.signum(x - this.g);
                        f2 = c(x - this.g, i3);
                    }
                    float f3 = f * f2;
                    if (f3 <= 0.0f) {
                        return false;
                    }
                    b(true);
                    a(f3, i3);
                }
            } else if (i2 == 3) {
                return false;
            } else {
                if (i2 == 5) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.i);
                    if (findPointerIndex2 < 0) {
                        Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid active pointer id.");
                        return false;
                    }
                    if (i3 == 2) {
                        float y2 = motionEvent.getY(findPointerIndex2) - this.f6426d;
                        i4 = motionEvent.getActionIndex();
                        if (i4 < 0) {
                            Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                            return false;
                        }
                        float y3 = motionEvent.getY(i4) - y2;
                        this.f6426d = y3;
                        this.f6427e = y3;
                    } else {
                        float x2 = motionEvent.getX(findPointerIndex2) - this.f6428f;
                        i4 = motionEvent.getActionIndex();
                        if (i4 < 0) {
                            Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                            return false;
                        }
                        float x3 = motionEvent.getX(i4) - x2;
                        this.f6428f = x3;
                        this.g = x3;
                    }
                    this.i = motionEvent.getPointerId(i4);
                } else if (i2 == 6) {
                    d(motionEvent);
                }
            }
        } else if (motionEvent.findPointerIndex(this.i) < 0) {
            Log.e("SpringBackLayout", "Got ACTION_UP event but don't have an active pointer id.");
            return false;
        } else {
            if (this.h) {
                this.h = false;
                g(i3);
            }
            this.i = -1;
            return false;
        }
        return true;
    }

    public void b(boolean z) {
        ViewParent parent = getParent();
        parent.requestDisallowInterceptTouchEvent(z);
        while (parent != null) {
            if (parent instanceof SpringBackLayout) {
                ((SpringBackLayout) parent).a(z);
            }
            parent = parent.getParent();
        }
    }

    private boolean b(MotionEvent motionEvent, int i2, int i3) {
        int i4;
        float f;
        float f2;
        if (i2 == 0) {
            this.i = motionEvent.getPointerId(0);
            this.h = false;
        } else if (i2 != 1) {
            if (i2 == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.i);
                if (findPointerIndex < 0) {
                    Log.e("SpringBackLayout", "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                } else if (this.h) {
                    if (i3 == 2) {
                        float y = motionEvent.getY(findPointerIndex);
                        f = Math.signum(y - this.f6427e);
                        f2 = c(y - this.f6427e, i3);
                    } else {
                        float x = motionEvent.getX(findPointerIndex);
                        f = Math.signum(x - this.g);
                        f2 = c(x - this.g, i3);
                    }
                    b(true);
                    a(f * f2, i3);
                }
            } else if (i2 == 3) {
                return false;
            } else {
                if (i2 == 5) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.i);
                    if (findPointerIndex2 < 0) {
                        Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid active pointer id.");
                        return false;
                    }
                    if (i3 == 2) {
                        float y2 = motionEvent.getY(findPointerIndex2) - this.f6426d;
                        i4 = motionEvent.getActionIndex();
                        if (i4 < 0) {
                            Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                            return false;
                        }
                        float y3 = motionEvent.getY(i4) - y2;
                        this.f6426d = y3;
                        this.f6427e = y3;
                    } else {
                        float x2 = motionEvent.getX(findPointerIndex2) - this.f6428f;
                        i4 = motionEvent.getActionIndex();
                        if (i4 < 0) {
                            Log.e("SpringBackLayout", "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                            return false;
                        }
                        float x3 = motionEvent.getX(i4) - x2;
                        this.f6428f = x3;
                        this.g = x3;
                    }
                    this.i = motionEvent.getPointerId(i4);
                } else if (i2 == 6) {
                    d(motionEvent);
                }
            }
        } else if (motionEvent.findPointerIndex(this.i) < 0) {
            Log.e("SpringBackLayout", "Got ACTION_UP event but don't have an active pointer id.");
            return false;
        } else {
            if (this.h) {
                this.h = false;
                g(i3);
            }
            this.i = -1;
            return false;
        }
        return true;
    }

    private float c(float f, int i2) {
        return b(Math.min(Math.abs(f) / ((float) (i2 == 2 ? this.mDisplayHeightPixels : this.mDisplayWidthPixels)), 1.0f), i2);
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5, int i6) {
        onNestedScroll(view, i2, i3, i4, i5, i6, this.o);
    }

    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        onNestedScroll(view, i2, i3, i4, i5, 0, this.o);
    }

    private void a(float f, int i2) {
        if (i2 == 2) {
            scrollTo(0, (int) (-f));
        } else {
            scrollTo((int) (-f), 0);
        }
    }

    private void a(float f, int i2, boolean z) {
        OnSpringBackListener onSpringBackListener = this.mSpringBackListener;
        if (onSpringBackListener == null || !onSpringBackListener.onSpringBack()) {
            this.mSpringScroller.a((float) getScrollX(), 0.0f, (float) getScrollY(), 0.0f, f, i2);
            b(2);
            if (z) {
                postInvalidateOnAnimation();
            }
        }
    }

    public void a(int i2, int i3, int i4, int i5, int[] iArr, int i6, int[] iArr2) {
        this.mNestedScrollingChildHelper.dispatchNestedScroll(i2, i3, i4, i5, iArr, i6, iArr2);
    }

    public void stopNestedScroll(int i2) {
        this.mNestedScrollingChildHelper.stopNestedScroll(i2);
    }

    public boolean a(int i2, int i3, int[] iArr, int[] iArr2, int i4) {
        return this.mNestedScrollingChildHelper.dispatchNestedPreScroll(i2, i3, iArr, iArr2, i4);
    }

    public boolean a(float f, float f2) {
        this.D = f;
        this.E = f2;
        return true;
    }

    private float b(float f, int i2) {
        double min = (double) Math.min(f, 1.0f);
        return ((float) (((Math.pow(min, 3.0d) / 3.0d) - Math.pow(min, 2.0d)) + min)) * ((float) (i2 == 2 ? this.mDisplayHeightPixels : this.mDisplayWidthPixels));
    }

    private void b(int i2) {
        if (this.J != i2) {
            for (SpringBackStageChangedListener onStateChanged : this.mStateListeners) {
                onStateChanged.onStateChanged(this.J, i2);
            }
            this.J = i2;
        }
    }
}
