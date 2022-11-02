package com.kevin.testassist;

import android.app.Notification;
import android.app.UiAutomation;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;

import com.kevin.testassist.utils.logUtil;

import java.util.HashSet;


public class AccessibilityEventListener implements UiAutomation.OnAccessibilityEventListener {
    public String toastMsg;

    private static AccessibilityEventListener instance;

    public AccessibilityEventListener(HashSet<String> watchers) {
        AccessibilityEventListener.instance = this;
    }

    public static AccessibilityEventListener getInstance() {
        if (instance == null) {
            throw new RuntimeException();
        }
        return instance;
    }

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        if (event.getPackageName() == null) {
            return;
        }
        if (event.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            Parcelable parcelable = event.getParcelableData();
            if (!(parcelable instanceof Notification)) {
                String packageName = event.getPackageName().toString();
                toastMsg = "" + event.getText().get(0);
                logUtil.d("","Toast:" + toastMsg + " Pkg:" + packageName);
            }
        }
    }
}
