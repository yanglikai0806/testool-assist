package com.kevin.testassist;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * 全局context
 */
public class MyApplication extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context =getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
