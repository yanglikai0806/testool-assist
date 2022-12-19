package com.kevin.testassist;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import com.kevin.testassist.utils.FileUtils;
import com.kevin.testassist.utils.logUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Reflect {
    @Test
    public void run(){
        String content = FileUtils.readJsonFile(CONST.REFLECT_FILE);
 //       String content = "{\"method\":\"drag\", \"args\":[{\"text\":\"测试工具\"}, [0.5, 0.5], 500]}";
//        String content = "{\"method\":\"drag\"}";
        try {
            JSONObject reflectObj = new JSONObject(content);
            logUtil.d("reflectObj", reflectObj + "");
            executeMethod(
                    reflectObj.getString("method"),
                    reflectObj.optJSONArray("args"));

        } catch (JSONException e) {
            logUtil.e("", e);
        }
    }

    public void executeMethod(String func, JSONArray args){
        Automator auto = new Automator();
        try {
            Method method;
            if (args == null) {
                method = auto.getClass().getDeclaredMethod(func);
                method.setAccessible(true);
                method.invoke(auto);
            } else {
                method = auto.getClass().getDeclaredMethod(
                        func,
                        JSONArray.class);
                method.setAccessible(true);
                method.invoke(auto, args);

            }
        } catch (Exception NoSuchMethodException){
            logUtil.e("", NoSuchMethodException);
        }
    }
}
