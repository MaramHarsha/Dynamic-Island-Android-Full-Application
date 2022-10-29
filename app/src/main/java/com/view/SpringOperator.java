package com.view;

class SpringOperator {
    private final double f6435a;
    private final double f6436b;

    public SpringOperator(float f, float f2) {
        double d = (double) f2;
        this.f6436b = Math.pow(6.283185307179586d / d, 2.0d);
        this.f6435a = (((double) f) * 12.566370614359172d) / d;
    }

    public double a(double d, float f, double d2, double d3) {
        double d4 = (double) f;
        return (d * (1.0d - (this.f6435a * d4))) + ((double) ((float) (this.f6436b * (d2 - d3) * d4)));
    }
}
