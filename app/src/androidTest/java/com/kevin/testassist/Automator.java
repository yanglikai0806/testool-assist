package com.kevin.testassist;

import static android.content.Context.WINDOW_SERVICE;

import android.graphics.Point;
import android.os.Environment;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.kevin.testassist.utils.FileUtils;
import com.kevin.testassist.utils.logUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Automator {

    public static UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    private static final int DEFAULT_SPEED = 1000;
    private static final Double DEFAULT_PERCENT = 1.0;

    private static UiObject2 findElement(JSONObject obj) {
        UiObject2 targetObj;
        Iterator<String> itr = obj.keys();
        ArrayList<String> keys = new ArrayList<>();
        while (itr.hasNext()) {
            keys.add(itr.next());
        }
        int index = 0;
        int margin = 0;
        int timeout = 3000;
        if (keys.contains("index")) {
            index = (int) obj.optInt("index");
            keys.remove("index");
        }
        // 目标对象操作范围
        if (keys.contains("margin")) {
            margin = obj.optInt("margin");
            keys.remove("margin");
        }

        // 自定义超时时间
        if (keys.contains("timeout")) {
            timeout = obj.optInt("timeout");
            keys.remove("timeout");
        }
        List<UiObject2> elements = new ArrayList<>();
        String key = keys.get(0);
        switch (key){
            case "id":
                elements = device.wait(Until.findObjects(By.res(obj.optString(key))), timeout);
                break;
            case "text":
                elements = device.wait(Until.findObjects(By.text(obj.optString(key))), timeout);
                break;
            case "content":
                elements = device.wait(Until.findObjects(By.desc(obj.optString(key))), timeout);
                break;
            case "content-desc":
                elements = device.wait(Until.findObjects(By.desc(obj.optString(key))), timeout);
                break;
            case "class":
                elements = device.wait(Until.findObjects(By.clazz(obj.optString(key))), timeout);
                break;
            default:
                logUtil.d("", "不支持的查找key: " + key);
        }
        if (elements.size() > index){
            logUtil.d("", String.format("找到 %s %s 个", obj, elements.size()));
            targetObj = elements.get(index);
            if (margin > 0) {
                targetObj.setGestureMargin(margin);
            }

            return targetObj;
        }
        logUtil.d("", "未找到 " + obj);
        return null;
    }

    private static UiObject findElement1(JSONObject obj) {
        UiObject targetObj = null;
        Iterator<String> itr = obj.keys();
        ArrayList<String> keys = new ArrayList<>();
        while (itr.hasNext()) {
            keys.add(itr.next());
        }
        int index = 0;
        int margin = 0;
        int timeout = 3000;
        if (keys.contains("index")) {
            index = (int) obj.optInt("index");
            keys.remove("index");
        }
        // 目标对象操作范围
        if (keys.contains("margin")) {
            margin = obj.optInt("margin");
            keys.remove("margin");
        }

        // 自定义超时时间
        if (keys.contains("timeout")) {
            timeout = obj.optInt("timeout");
            keys.remove("timeout");
        }
        String key = keys.get(0);
        switch (key){
            case "id":
                targetObj = device.findObject(new UiSelector().resourceId(obj.optString(key)).index(index));
                break;
            case "text":
                targetObj = device.findObject(new UiSelector().text(obj.optString(key)).index(index));
                break;
            case "content":
                targetObj = device.findObject(new UiSelector().description(obj.optString(key)).index(index));
                break;
            case "content-desc":
                targetObj = device.findObject(new UiSelector().description(obj.optString(key)).index(index));
                break;
            case "class":
                targetObj = device.findObject(new UiSelector().className(obj.optString(key)).index(index));
                break;
            default:
                logUtil.d("", "不支持的查找key: " + key);
        }
        if (targetObj != null){
            return targetObj;
        }
        logUtil.d("", "未找到 " + obj);
        return null;
    }


    private static void click(JSONObject obj) {
        UiObject2 target = findElement(obj);
        if (target != null){
            target.click();
        }
    }

    public static void click(JSONArray args) {
        click(args.optJSONObject(0));
    }

    public static void longClick(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target != null){
            target.longClick();
        }
    }

    public static void pinchOpen(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.pinchOpen((float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));
    }

    public static void pinchClose(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.pinchClose((float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));
    }

    public static void scrollLeft(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
//        if (!target.isScrollable()){
//            logUtil.d("", (JSONObject) args.get(0) +" 不支持scroll");
//            return;
//        }
        target.scroll(Direction.LEFT, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));

    }

    public static void scrollRight(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
//        if (!target.isScrollable()){
//            logUtil.d("", (JSONObject) args.get(0) +" 不支持scroll");
//            return;
//        }
        target.scroll(Direction.RIGHT, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));
    }

    public static void scrollUp(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
//        if (!target.isScrollable()){
//            logUtil.d("", (JSONObject) args.get(0) +" 不支持scroll");
//            return;
//        }
        target.scroll(Direction.UP, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));
    }

    public static void scrollDown(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
//        if (!target.isScrollable()){
//            logUtil.d("", (JSONObject) args.get(0) +" 不支持scroll");
//            return;
//        }
        target.scroll(Direction.DOWN, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));
    }

    public static void setText(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        if (args.length() > 1) {
            String text = args.optString(1);
            if (args.optString(2, "").equals("a")){
                text += target.getText();
            }
            target.setText(text);
        } else {
            logUtil.d("setText", "缺少必要参数");
        }
    }


    public static void drag(JSONArray args){
        if (args.opt(0) instanceof JSONObject){
            UiObject2 target = findElement(args.optJSONObject(0));
            if (target == null) return;
            if (args.length() > 1) {
                if (args.opt(1) instanceof JSONObject) {
                    UiObject2 destTarget = findElement(args.optJSONObject(1));
                    if (destTarget == null) return;
                    target.drag(destTarget.getVisibleCenter(), args.optInt(2, DEFAULT_SPEED));
                }
                if (args.opt(1) instanceof JSONArray) {
                    Point point = getRealPoint(args.optJSONArray(1));
                    target.drag(new Point(point.x, point.y), args.optInt(2, DEFAULT_SPEED));
                }
            } else {
                logUtil.d("drag", "缺少必要参数");
            }
        } else {
            Point startPoint = getRealPoint(args.optJSONArray(0));
            Point endPoint = getRealPoint(args.optJSONArray(1));
            device.drag(startPoint.x, startPoint.y, endPoint.x, endPoint.y,args.optInt(2, DEFAULT_SPEED));
        }

    }

    public static void swipeLeft(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.swipe(Direction.RIGHT, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));

    }

    public static void swipeRight(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.swipe(Direction.LEFT, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));

    }

    public static void swipeUp(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.swipe(Direction.DOWN, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));

    }

    public static void swipeDown(JSONArray args) {
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.swipe(Direction.UP, (float) args.optDouble(1, DEFAULT_PERCENT), args.optInt(2, DEFAULT_SPEED));

    }

    public static void flingLeft(JSONArray args){
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.fling(Direction.LEFT, args.optInt(1, DEFAULT_SPEED * 3));

    }

    public static void flingRight(JSONArray args){
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.fling(Direction.RIGHT, args.optInt(1, DEFAULT_SPEED * 3));

    }

    public static void flingUp(JSONArray args){
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.fling(Direction.UP, args.optInt(1, DEFAULT_SPEED * 3));

    }

    public static void flingDown(JSONArray args){
        UiObject2 target = findElement(args.optJSONObject(0));
        if (target == null) return;
        target.fling(Direction.DOWN, args.optInt(1, DEFAULT_SPEED * 3));
    }

    public static void twoPointerGesture(JSONArray args) {
        UiObject target = findElement1(args.optJSONObject(0));
        //
        Point point = getRealPoint(args.optJSONArray(1));
        Point startPoint1 = new Point(point.x, point.y);
        //
        point = getRealPoint(args.optJSONArray(2));
        Point startPoint2 = new Point(point.x, point.y);
        //
        point = getRealPoint(args.optJSONArray(3));
        Point endPoint1 = new Point(point.x, point.y);
        //
        point = getRealPoint(args.optJSONArray(4));
        Point endPoint2 = new Point(point.x, point.y);
        //
        int steps = args.optInt(4, 20);
        target.performTwoPointerGesture(startPoint1, startPoint2, endPoint1, endPoint2, steps);
    }

    public static void multiPointerGesture(JSONArray args) {
        UiObject target = findElement1(args.optJSONObject(0));
        //
        int size = args.length();
        JSONArray ps = new JSONArray();
        JSONArray ps1 = new JSONArray();
        for (int i=1; i<size; i++){
            ps1.put(getRealPoint(args.optJSONArray(i)));
        }
        // 组装成二维数组形式
        ps.put(ps1);
        ps.put(new JSONArray().put(new JSONArray()));
        try {
            assert target != null;
            Method method = target.getClass().getMethod("performMultiPointerGesture", MotionEvent.PointerCoords[][].class);
            Boolean rt = (Boolean)method.invoke(target, (Object)parsePointerCoords(ps));
        } catch (Exception e) {
            logUtil.e("", e);
        }
    }

    public static void takeScreenshot(JSONArray args) {
        File f = new File(args.optString(0, CONST.LOGPATH +"screenshot.png"));
        long startTime = SystemClock.currentThreadTimeMillis();
        device.takeScreenshot(f, (float) args.optDouble(1, 1.0), args.optInt(2, 80));
        logUtil.d("", "截图耗时: " + (SystemClock.currentThreadTimeMillis() - startTime));
    }

    public static void dumpHierarchy() {
        device.setCompressedLayoutHeirarchy(false);
        try {
            String sdPath = Environment.getExternalStorageDirectory().getPath();
            //创建xml文件
            File file = new File(sdPath, "window_dump.xml");
            FileOutputStream fos = new FileOutputStream(file);
            long start = System.currentTimeMillis();
            AccessibilityNodeInfoDumper.dumpWindowHierarchy(device, fos);
            logUtil.d("dump 耗时", (System.currentTimeMillis() - start) + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dumpWindowHierarchy() {
        Configurator configurator = Configurator.getInstance();
        configurator.setWaitForIdleTimeout(1000);
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        //创建xml文件
        File file = new File(sdPath, "window_dump.xml");
        try {
            long start = System.currentTimeMillis();
            device.dumpWindowHierarchy(file);
            logUtil.d("dump 耗时", (System.currentTimeMillis() - start) + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String lastToast() {
        AccessibilityEventListener instance = AccessibilityEventListener.getInstance();
        String filePath = Environment.getExternalStorageDirectory() + File.separator + "toast.txt";
        FileUtils.deleteFile(filePath);
        File file = new File(filePath);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 20000) {
            if (instance.toastMsg != null) {
                try {
                    logUtil.d("", instance.toastMsg);
                    FileOutputStream fos = new FileOutputStream(file,false);
                    OutputStreamWriter osw = new OutputStreamWriter(fos);
                    BufferedWriter bw = new BufferedWriter(osw);

                    bw.write(instance.toastMsg);
                    bw.close();
                } catch (Exception e) {
                    logUtil.e("", e);
                }
                return instance.toastMsg;
            } else{
                SystemClock.sleep(500);
            }
        }
        return "";
    }


    /**
    =======================以下是内部私有方法===================================
     */
    private static MotionEvent.PointerCoords createPointerCoords(JSONArray pt) {
        final int x = pt.optInt(0);
        final int y = pt.optInt(1);

        final MotionEvent.PointerCoords p = new MotionEvent.PointerCoords();
        p.size = 1;
        p.pressure = 1;
        p.x = x;
        p.y = y;
        return p;
    }

    private static MotionEvent.PointerCoords[] gesturesToPointerCoords(final JSONArray gestures, final double maxTime) {
        final int steps = (int) (maxTime * 200);
        final MotionEvent.PointerCoords[] pc = new MotionEvent.PointerCoords[steps];
        int i = 1;
        JSONArray current = gestures.optJSONArray(0);
        double currentTime = 0.2;
        double runningTime = 0.0;
        final int gesturesLength = gestures.length();
        for (int j = 0; j < steps; j++) {
            if (runningTime > currentTime && i < gesturesLength) {
                current = gestures.optJSONArray(i++);
                currentTime += 0.2;
            }
            pc[j] = createPointerCoords(current);
            runningTime += 0.005;
        }
        return pc;
    }

    private static MotionEvent.PointerCoords[][] parsePointerCoords(JSONArray actions) {
        final MotionEvent.PointerCoords[][] pcs = new MotionEvent.PointerCoords[actions.length()][];
        for (int i = 0; i < actions.length(); i++) {
            final JSONArray gestures = actions.optJSONArray(i);
            pcs[i] = gesturesToPointerCoords(gestures, 1.0);
        }
        return pcs;
    }

    private static android.graphics.Point getScreenSize() {
        WindowManager windowManager = (WindowManager) MyApplication.getContext().getSystemService(WINDOW_SERVICE);
        android.graphics.Point size = new Point();
        windowManager.getDefaultDisplay().getRealSize(size);
        return size;
    }


    private static Point getRealPoint(JSONArray point){
        Point Size = getScreenSize();
        Point realPoint = new Point();
        double x = point.optDouble(0);
        double y = point.optDouble(1);
        if (x<1 && y<1) {
            realPoint.x = (int) (x * Size.x);
            realPoint.y = (int) (y * Size.y);

        } else {
            realPoint.x = (int) x;
            realPoint.y = (int) y;
        }
        return realPoint;
    }
}
