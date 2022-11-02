package com.kevin.testassist.utils;

/**
 * Toast提示显示工具类
 *
 */

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import static android.os.Looper.getMainLooper;

public class ToastUtils {

    // 短时间显示Toast信息
    public static void showShort(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
        logUtil.d("---Toast---", info+"");
    }

    // 长时间显示Toast信息
    public static void showLong(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
    }

    public static void showShortByHandler(final Context context, final String info){
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort(context, info);
            }
        });
    }
    public static void showLongByHandler(final Context context, final String info){
        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLong(context, info);
            }
        });
    }

    public static void showDialog(final Context context, final String message){

        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder popWindow = new AlertDialog.Builder(context);
                //设置对话框标题
                popWindow.setTitle("log");
                //设置对话框消息
                popWindow.setMessage(message);
                // 添加选择按钮并注册监听
                popWindow.setPositiveButton("确定", null);
                popWindow.setNegativeButton("取消", null);
                //对话框显示
                popWindow.show();
            }
        });


    }

}
