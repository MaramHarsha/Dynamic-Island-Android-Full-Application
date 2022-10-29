package com.view;

import android.view.animation.AnimationUtils;

class SpringScroller {
    private long f6437a;
    private long f6438b;
    private double f6439c;
    private double f6440d;
    private double f6442f;
    private double g;
    private double h;
    private double i;
    private double j;
    private double k;
    private double l;
    private double m;
    private SpringOperator mSpringOperator;
    private int n;
    private boolean o = true;
    private boolean p;

    SpringScroller() {
    }

    public void a(float f, float f2, float f3, float f4, float f5, int i2) {
        this.o = false;
        this.p = false;
        double d = (double) f;
        this.g = d;
        this.h = d;
        this.f6442f = (double) f2;
        double d2 = (double) f3;
        this.j = d2;
        this.k = d2;
        this.f6440d = (double) ((int) d2);
        this.i = (double) f4;
        double d3 = (double) f5;
        this.l = d3;
        this.m = d3;
        if (Math.abs(d3) <= 5000.0d) {
            this.mSpringOperator = new SpringOperator(1.0f, 0.4f);
        } else {
            this.mSpringOperator = new SpringOperator(1.0f, 0.55f);
        }
        this.n = i2;
        this.f6437a = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void b() {
        this.o = true;
    }

    public final int c() {
        return (int) this.f6439c;
    }

    public final int d() {
        return (int) this.f6440d;
    }

    public final boolean e() {
        return this.o;
    }

    public boolean a() {
        if (this.mSpringOperator == null || this.o) {
            return false;
        }
        if (this.p) {
            this.o = true;
            return true;
        }
        long currentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        this.f6438b = currentAnimationTimeMillis;
        float f = 0.016f;
        float min = Math.min(((float) (currentAnimationTimeMillis - this.f6437a)) / 1000.0f, 0.016f);
        if (min != 0.0f) {
            f = min;
        }
        this.f6437a = this.f6438b;
        if (this.n == 2) {
            double a = this.mSpringOperator.a(this.m, f, this.i, this.j);
            double d = this.j + (((double) f) * a);
            this.f6440d = d;
            this.m = a;
            if (a(d, this.k, this.i)) {
                this.p = true;
                this.f6440d = this.i;
            } else {
                this.j = this.f6440d;
            }
        } else {
            double a2 = this.mSpringOperator.a(this.m, f, this.f6442f, this.g);
            double d2 = this.g + (((double) f) * a2);
            this.f6439c = d2;
            this.m = a2;
            if (a(d2, this.h, this.f6442f)) {
                this.p = true;
                this.f6439c = this.f6442f;
            } else {
                this.g = this.f6439c;
            }
        }
        return true;
    }

    public boolean a(double d, double d2, double d3) {
        if (d2 < d3 && d > d3) {
            return true;
        }
        int i2 = (d2 > d3 ? 1 : (d2 == d3 ? 0 : -1));
        char c = i2 > 0 ? 1 : i2 == 0 ? (char) 0 : 65535;
        if (c > 0 && d < d3) {
            return true;
        }
        if ((c != 0 || Math.signum(this.l) == Math.signum(d)) && Math.abs(d - d3) >= 1.0d) {
            return false;
        }
        return true;
    }
}
