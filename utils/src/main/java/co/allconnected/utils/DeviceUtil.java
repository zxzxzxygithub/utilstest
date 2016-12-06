package co.allconnected.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.io.IOException;
import java.util.Locale;

/**
 * 获取手机相关的信息
 *
 * @author michael
 * @time 16/12/5 下午7:15
 */
public class DeviceUtil {

    /**
     * @return IMEI
     */
    public static String getIMEI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getDeviceId();
    }

    /**
     * @return IMSI
     */
    public static String getIMSI(Context context) {
        TelephonyManager teleMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return teleMgr.getSubscriberId();
    }

    /**
     * 获取手机品牌 + 型号
     *
     * @return
     */
    public static String getBrandModel() {
        return android.os.Build.BRAND + "," + android.os.Build.MODEL;
    }

    /**
     * 判断系统是否root
     */
    static public boolean isRoot() {

        try {
            if (Runtime.getRuntime().exec("su").getOutputStream() == null) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 获得手机注册网络的所在国家
     */
    static public String getCountry(Context context) {
        if (context == null) {
            return "";
        }

        String country = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkCountryIso();
        return country == null ? "" : country;
    }

    /**
     * 获得手机注册网络的所在国家代码(大写)，错误返回'??'
     */
    static public String getCountryCode(Context context) {
        if (context == null) {
            return "??";
        }

        String country = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkCountryIso();
        if (country == null || country.length() != 2)
            return "??";

        return country.toUpperCase(Locale.US);
    }



}
