package com.dpdp.base_moudle.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-07
 * <p>
 * Summary: 日志打印工具类 release版本会影响性能  不会打印
 */
public class LogUtils {

    private static boolean isDebug = true;

    public static void init(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            isDebug = (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
            isDebug = true;
        }
    }

    public static void v(String tag, String message) {
        if (isDebug) {
            Log.v(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (isDebug) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (isDebug) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (isDebug) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (isDebug) {
            Log.e(tag, message);
        }
    }
}
