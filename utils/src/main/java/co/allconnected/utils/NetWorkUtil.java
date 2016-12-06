package co.allconnected.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.List;


/**
 * 网络工具类，包含网络的判断、跳转到设置页面
 *
 * @author michael
 * @time 16/12/5 下午5:07
 */
public class NetWorkUtil {

    // 网络类型标识
    public static final int NO_NET = 0;
    public static final int NET_MOBILE = 1;
    public static final int NET_WIFI = 2;

    /**
     * 判断当前是否有网络连接
     *
     * @param context
     * @return 有网络返回true；无网络返回false
     */
    public static boolean isNetWorkEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检测网络类型
     *
     * @return 网络类型
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null)
            return NO_NET;
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            if (info.isAvailable()) {
                int type = info.getType();
                if (type == ConnectivityManager.TYPE_MOBILE)
                    return NET_MOBILE;
                if (type == ConnectivityManager.TYPE_WIFI)
                    return NET_WIFI;
            } else {
                return NO_NET;
            }
        }
        return NO_NET;
    }

    /**
     * 判断当前网络是否为wifi
     *
     * @param context
     * @return 如果为wifi返回true；否则返回false
     */
    public static boolean isWiFiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) ? true : false;
    }

    /**
     * 控制WIFI状态
     *
     * @param state
     * @return 操作成功则返回true，失败则返回false
     */
    public static boolean setWifi(Context context, boolean state) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.setWifiEnabled(state);
    }

    /**
     * 获取Wifi ssid
     *
     * @param context
     * @return ssid
     */
    public static String getWifiName(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        if (TextUtils.isEmpty(ssid) || ssid.contains("0x"))
            ssid = "";
        if (ssid.length() > 0)
            if (ssid.contains("unknown"))
                ssid = "";
        return ssid;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isMobileDataEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Network[] allNetworks = connectivityManager.getAllNetworks();
            if (allNetworks != null && allNetworks.length > 0) {
                for (Network n :
                        allNetworks) {
                    NetworkInfo networkInfo = connectivityManager.getNetworkInfo(n);
                    if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        return true;
                    }
                }
            }
        } else {
            isMobileDataEnable = connectivityManager.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        }

        return isMobileDataEnable;
    }

    /**
     * 判断wifi 是否可用
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static boolean isWifiDataEnable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;
        isWifiDataEnable = connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }

    /**
     * 跳转到网络设置页面
     *
     * @param activity
     */
    public static void goSetting(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * 返回 已连接的wifi信号强度,值越小表示信号越强 <br> -1:表示不存在
     *
     * @param context
     * @return
     */
    public static int getWifiLevel(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        if (wifiManager.startScan()) {
            List<ScanResult> scanResults = wifiManager.getScanResults();

            WifiInfo info = wifiManager.getConnectionInfo();
            String ssid = info.getSSID();

            if (!TextUtils.isEmpty(ssid)) {
                try {
                    if (ssid.contains("\"")) //部分手机返回的 ssid 包含 """
                        ssid = ssid.substring(1, ssid.length() - 1);
                    if (scanResults != null && scanResults.size() > 0) {
                        for (ScanResult s : scanResults) {
                            if (s.SSID.equals(ssid)) {
                                return Math.abs(s.level);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return -1;
    }

}
