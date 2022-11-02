package com.kevin.testassist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.kevin.testassist.utils.logUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.kevin.testassist.CONST.DRAG_RECEIVER;


@RunWith(AndroidJUnit4.class)
public class drag {
    private static String Args = "";

    @Test
    public void run() throws JSONException {
        logUtil.d("","**************drag****************");
        BroadcastReceiver sr = new DragReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction(DRAG_RECEIVER);
        InstrumentationRegistry.getTargetContext().registerReceiver(sr, filter);
        int waitLoop = 10;
        while (TextUtils.isEmpty(Args) && waitLoop > 0){
            SystemClock.sleep(200);
            waitLoop -= 1;
        }

        JSONArray args = new JSONArray(Args);
        Automator.drag(args);
        InstrumentationRegistry.getTargetContext().unregisterReceiver(sr);
    }


    public static class DragReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("ARGS")) {
                Args = intent.getStringExtra("ARGS");
                logUtil.d("", "接收到drag命令: " + Args);
            }
        }
    }
}