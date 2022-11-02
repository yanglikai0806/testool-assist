package com.kevin.testassist;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.kevin.testassist.utils.logUtil;


@RunWith(AndroidJUnit4.class)
public class getLastToast {

    @Test
    public void getToast() {
        Automator auto = new Automator();
        try {
            Method method = auto.getClass().getDeclaredMethod("lastToast");
            method.setAccessible(true);
            method.invoke(auto);
        } catch (Exception e){
            logUtil.e("", e);
        }
    }

}