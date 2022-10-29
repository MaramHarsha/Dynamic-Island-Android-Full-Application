package com.lock.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AccessibilityNodeUtil {
    public static final boolean a = (J() || (Build.VERSION.SDK_INT < 28 && Build.MANUFACTURER.equalsIgnoreCase("samsung")));
    public AccessibilityNodeInfo accessibilityNodeInfo;
    public final ArrayList<AccessibilityNodeInfo> accessibilityNodeInfoArrayList = new ArrayList<>();
    public Context context;
    public String f10353d;
    public double f10355f;
    public final Rect rect = new Rect();
    public SharedPreferences sharedPreferences;
    public final int statusBarHeight;

    public interface performActionListener<T> {
        boolean performActionOnButton(T t, String str);
    }

    public static boolean J() {
        try {
            return Build.VERSION.class.getDeclaredField("SEM_PLATFORM_INT").getInt((Object) null) - 90000 >= 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    public AccessibilityNodeUtil(Context context2, int i) {
        this.context = context2;
        this.statusBarHeight = i;
        SharedPreferences sharedPreferences2 = context2.getSharedPreferences("QS_TILES", 0);
        this.sharedPreferences = sharedPreferences2;
        N(sharedPreferences2);
    }

    public static void N(SharedPreferences sharedPreferences2) {
        SharedPreferences.Editor editor = null;
        for (Map.Entry next : sharedPreferences2.getAll().entrySet()) {
            if (next.getValue() instanceof Integer) {
                if (editor == null) {
                    editor = sharedPreferences2.edit();
                }
                editor.remove((String) next.getKey());
                StringBuilder w = w("/");
                w.append(((Integer) next.getValue()).intValue() - 1);
                editor.putString((String) next.getKey(), w.toString());
            }
        }
        if (editor != null) {
            editor.apply();
        }
    }

    public static StringBuilder w(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static boolean performButtonClick(AccessibilityNodeInfo accessibilityNodeInfo2) {
        if (accessibilityNodeInfo2 == null) {
            return false;
        }
        boolean performAction = accessibilityNodeInfo2.performAction(16);
        accessibilityNodeInfo2.recycle();
        return performAction;
    }

    public static void f(Collection<AccessibilityNodeInfo> collection) {
        if (collection != null) {
            for (AccessibilityNodeInfo next : collection) {
                if (next != null) {
                    next.recycle();
                }
            }
            collection.clear();
        }
    }

    public static String h(CharSequence charSequence) {
        return charSequence.toString().toLowerCase().replaceAll("\n", " ").replaceAll(",", " ").replaceAll("\\s{2,}", " ");
    }

    public final boolean findButtonAndClick(AccessibilityNodeInfo accessibilityNodeInfo2, performActionListener<AccessibilityNodeInfo> performactionlistener, String str) {
        if (accessibilityNodeInfo2 == null) {
            return false;
        }
        int childCount = accessibilityNodeInfo2.getChildCount();
        while (true) {
            childCount--;
            if (childCount < 0) {
                break;
            }
            this.accessibilityNodeInfoArrayList.add(accessibilityNodeInfo2.getChild(childCount));
        }
        while (!this.accessibilityNodeInfoArrayList.isEmpty()) {
            ArrayList<AccessibilityNodeInfo> arrayList = this.accessibilityNodeInfoArrayList;
            AccessibilityNodeInfo remove = arrayList.remove(arrayList.size() - 1);
            if (remove != null) {
                if (performactionlistener.performActionOnButton(remove, str)) {
                    f(this.accessibilityNodeInfoArrayList);
                    return true;
                }
                int childCount2 = remove.getChildCount();
                while (true) {
                    childCount2--;
                    if (childCount2 < 0) {
                        break;
                    }
                    this.accessibilityNodeInfoArrayList.add(remove.getChild(childCount2));
                }
                remove.recycle();
            }
        }
        return false;
    }

    public final String d() {
        String str;
        try {
            str = ((TelephonyManager) this.context.getSystemService("phone")).getNetworkOperatorName();
        } catch (Throwable unused) {
            str = null;
        }
        return (str == null || str.isEmpty()) ? "Mobile data" : str;
    }

    public final boolean e(AccessibilityNodeInfo accessibilityNodeInfo2, double d) {
        try {
            this.accessibilityNodeInfo.recycle();
        } catch (Throwable unused) {
        }
        this.accessibilityNodeInfo = AccessibilityNodeInfo.obtain(accessibilityNodeInfo2);
        this.f10355f = d;
        return d == 1.0d;
    }
}
