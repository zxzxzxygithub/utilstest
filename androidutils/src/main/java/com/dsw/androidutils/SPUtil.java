package com.dsw.androidutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * sharedpreference相关方法
 *
 * @author michael
 * @time 16/12/5 下午6:41
 */
public class SPUtil {

    private SPUtil() {
    }

    /**
     * 默认的SharePreference名称
     */
    private static final String SHARED_NAME = "SharedPreferences";

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String key, Object data) {
        String filename = null;
        saveData(context, filename, key, data);
    }

    /**
     * 保存数据到文件
     *
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String fileName, String key, Object data) {

        Editor editor;
        String type = data.getClass().getSimpleName();
        if (!TextUtils.isEmpty(fileName)) {
            editor = getEditor(context, fileName);
        } else {
            editor = getEditor(context);
        }

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }
        editor.commit();
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String key, Object defValue) {

        String fileName = null;
        return getData(context, fileName, key, defValue);
    }

    /**
     * 从文件中读取数据
     *
     * @param context
     * @param fileName
     * @param key
     * @param defValue
     * @return
     */
    public static Object getData(Context context, String fileName, String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        SharedPreferences sharedPreferences;
        if (TextUtils.isEmpty(fileName)) {
            sharedPreferences = getSharedPreferences(context);
        } else {
            sharedPreferences = getSharedPreferences(context, fileName);
        }
        //defValue为为默认值，如果当前获取不到数据就返回它
        if ("Integer".equals(type)) {
            return sharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return sharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return sharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return sharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }


    /**
     * 是否包含key
     *
     * @param context 应用程序上下文
     * @param key     key关键字
     * @return 包含返回true；反之返回false
     */
    public static boolean containsKey(Context context, String key) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.contains(key);
    }

    /**
     * 是否包含key
     *
     * @param context 应用程序上下文
     * @param key     key关键字
     * @return 包含返回true；反之返回false
     */
    public static boolean containsKey(Context context, String fileName, String key) {
        SharedPreferences sp = getSharedPreferences(context, fileName);
        return sp.contains(key);
    }


    /**
     * 删除关键字key
     *
     * @param context 应用程序上下文
     * @param key     关键字key
     * @return 成功返回true，失败返回false
     */
    public static boolean removeKey(Context context, String key) {
        return getEditor(context).remove(key).commit();
    }

    /**
     * 删除关键字key
     *
     * @param context 应用程序上下文
     * @param key     关键字key
     * @return 成功返回true，失败返回false
     */
    public static boolean removeKey(Context context, String fileName, String key) {
        return getEditor(context, fileName).remove(key).commit();
    }

    /**
     * 清除所有的关键字
     *
     * @param context 应用程序上下文
     * @return 成功返回true，失败返回false
     */
    public static boolean clearValues(Context context) {
        return getEditor(context).clear().commit();
    }

    /**
     * 清除所有的关键字
     *
     * @param context 应用程序上下文
     * @return 成功返回true，失败返回false
     */
    public static boolean clearValues(Context context, String fileName) {
        return getEditor(context, fileName).clear().commit();
    }


    /**
     * 获取SharedPreferences对象
     *
     * @param context 应用程序上下文
     * @return 返回SharedPreferences对象
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferences对象
     *
     * @param context 应用程序上下文
     * @return 返回SharedPreferences对象
     */
    private static SharedPreferences getSharedPreferences(Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 获取Editor对象
     *
     * @param context 应用程序上下文
     * @return 返回Editor对象
     */
    private static Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    /**
     * 获取Editor对象
     *
     * @param context 应用程序上下文
     * @return 返回Editor对象
     */
    private static Editor getEditor(Context context, String fileName) {
        return getSharedPreferences(context, fileName).edit();
    }
}
