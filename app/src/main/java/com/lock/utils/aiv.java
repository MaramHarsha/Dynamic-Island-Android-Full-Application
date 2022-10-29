package com.lock.utils;

import android.graphics.Bitmap;

public final class aiv {
    private static String f572a = "ImageBlurCPU";

    public static Bitmap m436a(Bitmap bitmap) {
        try {
            Object[] objArr = new Object[2];
            Integer.valueOf(Math.round(((float) bitmap.getWidth()) * 0.15f));
            Integer.valueOf(Math.round(((float) bitmap.getHeight()) * 0.15f));
            return Bitmap.createScaledBitmap(bitmap, Math.round(((float) bitmap.getWidth()) * 0.15f), Math.round(((float) bitmap.getHeight()) * 0.15f), true);
        } catch (Throwable th) {
            th.getMessage();
            return bitmap;
        }
    }

    public static Bitmap m438a(Bitmap bitmap, int i) {
        try {
            Integer.valueOf(i).intValue();
            if (i > 96) {
                i = 96;
            }
            int width = bitmap.getWidth() > i ? i : bitmap.getWidth();
            if (bitmap.getHeight() <= i) {
                i = bitmap.getHeight();
            }
            return Bitmap.createScaledBitmap(bitmap, width, i, true);
        } catch (Throwable th) {
            th.getMessage();
            return bitmap;
        }
    }
}
