package com.kevin.testassist;

import android.os.Environment;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public class dumpWindow {
    @Test
    public void getDump() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                Automator.takeScreenshot(new JSONArray().put(Environment.getExternalStorageDirectory().getPath() + File.separator + "window_dump.png"));
            }
        });
        th.start();
        Automator.dumpWindowHierarchy();
        while (th.isAlive()){
            SystemClock.sleep(100);
        }
    }
}