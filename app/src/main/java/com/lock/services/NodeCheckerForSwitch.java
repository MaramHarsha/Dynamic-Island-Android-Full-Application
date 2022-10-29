package com.lock.services;

import android.view.accessibility.AccessibilityNodeInfo;
import com.lock.services.AccessibilityNodeUtil;
import java.util.Objects;

public final class NodeCheckerForSwitch implements AccessibilityNodeUtil.performActionListener {
    public final AccessibilityNodeUtil accessibilityNodeUtil;

    public NodeCheckerForSwitch(AccessibilityNodeUtil accessibilityNodeUtil2) {
        this.accessibilityNodeUtil = accessibilityNodeUtil2;
    }

    public final boolean performActionOnButton(Object obj, String str) {
        CharSequence contentDescription;
        AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj;
        Objects.requireNonNull(this.accessibilityNodeUtil);
        if (((String) accessibilityNodeInfo.getClassName()) == null || (contentDescription = accessibilityNodeInfo.getContentDescription()) == null) {
            return false;
        }
        String[] split = str.split(",");
        boolean z = false;
        for (String lowerCase : split) {
            if (contentDescription.toString().toLowerCase().contains(lowerCase.toLowerCase())) {
                z = true;
            }
        }
        if (!z) {
            return false;
        }
        if (accessibilityNodeInfo.isClickable()) {
            AccessibilityNodeUtil.performButtonClick(accessibilityNodeInfo);
        } else if (!accessibilityNodeInfo.getParent().isClickable()) {
            return false;
        } else {
            AccessibilityNodeUtil.performButtonClick(accessibilityNodeInfo.getParent());
        }
        return true;
    }
}
