package com.per.note.utils;

import android.util.Log;

/**
 * 日志工具类
 * Created by Magnolia on 2016/11/18.
 */
public class LogUtils {
    // 是否debug模式 true不打印日志 false打印日志
    private static final boolean DEBUG = false;

    // 提醒日志
    public static void w(String msg) {
        if (DEBUG) return;
        Log.w(getMethodInfo(), msg);
    }

    // 异常日志
    public static void e(String msg) {
        if (DEBUG) return;
        Log.e(getMethodInfo(), msg);
    }

    // 异常日志
    public static void i(String tag, String msg) {
        if (DEBUG) return;
        Log.e(getMethodInfo(), msg);
    }

    /**
     * 获取日志打印时所在的类.方法
     */
    private static String getMethodInfo() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String info = "=======" + stacks[2].getClassName() + "." + stacks[2].getMethodName() + "()";
        return info;
    }
}