package co.allconnected.utils;

import android.util.Log;

/**
 * 日志工具类
 * @author michael
 * @time 16/12/5 下午5:00
 */
public class LogUtil {

    private static boolean isDubug = false;

    public static void enableDebug(boolean isEnable) {
        isDubug = isEnable;
    }

    public static void d(String tag, String msg) {
        if (isDubug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDubug) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDubug) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (isDubug) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDubug) {
            Log.w(tag, msg);
        }
    }
}
