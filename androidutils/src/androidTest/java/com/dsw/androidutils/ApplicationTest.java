package com.dsw.androidutils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testInstalledPackages() {
        List<PackageInfo> installedPackages = AppUtil.getInstalledPackages(getContext());
        String pName;
        for (PackageInfo p :
                installedPackages) {
            pName = p.packageName;
            Log.d(TAG, "testInstalledPackages: pName_" + pName);

        }
    }

    public void testAESEncryptor() {

        String originText = "origin";
        try {
            String encrypt = AESEncryptor.encrypt(originText);
            Log.d(TAG, "testAESEncryptor: encrypt_" + encrypt);
            String decrypt = AESEncryptor.decrypt(encrypt);
            Log.d(TAG, "testAESEncryptor: decrypt_" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void testAppUtil(){
        Context context = getContext();
        Drawable applicationIcon = AppUtil.getApplicationIcon(context);
        Log.d(TAG, "testAppUtil: "+applicationIcon);
        int appProcessId = AppUtil.getAppProcessId();
        Log.d(TAG, "testAppUtil: processid_"+appProcessId);
        String metaData = AppUtil.getMetaData(context, "hello");
        Log.d(TAG, "testAppUtil: meta_"+metaData);
        String packetName = AppUtil.getPacketName(context);
        Log.d(TAG, "testAppUtil: pkg_"+packetName);

        int versionCode = AppUtil.getVersionCode(context);
        Log.d(TAG, "testAppUtil: vcode_"+versionCode);

        String versionName = AppUtil.getVersionName(context);
        Log.d(TAG, "testAppUtil: vname_"+versionName);


    }


    public void testCheckUtil(){

       String[] list=new String[]{"a","b"};
        boolean b = CheckUtil.checArrayLength(list, 1);
        Log.d(TAG, "testCheckUtil: cheArrayLength_"+b);

        boolean checkDenominator = CheckUtil.checkDenominator(123d);
        Log.d(TAG, "testCheckUtil: checkDenominator_"+checkDenominator);

    }


    public  void testDateUtil(){

        String allTimeDayStr = DateUtil.dateToString(new Date(), DateUtil.DatePattern.ALL_TIME);
        Log.d(TAG, "testDateUtil: all_"+allTimeDayStr);

        int daysOfMonth = DateUtil.daysOfMonth(2016, 2);
        Log.d(TAG, "testDateUtil: daysOfMonth_"+daysOfMonth);

        int indexWeekOfDate = DateUtil.getIndexWeekOfDate(new Date());
        Log.d(TAG, "testDateUtil: indexWeekOfDate_"+indexWeekOfDate);

        String nowDate = DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY);
        Log.d(TAG, "testDateUtil: nowDate_"+nowDate);

        int nowDay = DateUtil.getNowDay();
        Log.d(TAG, "testDateUtil: nowday_"+nowDay);

        int nowDaysOfMonth = DateUtil.getNowDaysOfMonth();
        Log.d(TAG, "testDateUtil: nowdaysOfMonth_"+nowDaysOfMonth);

        int nowMonth = DateUtil.getNowMonth();
        Log.d(TAG, "testDateUtil: nowmonth_"+nowMonth);


        int nowYear = DateUtil.getNowYear();
        Log.d(TAG, "testDateUtil: nowyear_"+nowYear);


        String weekOfDate = DateUtil.getWeekOfDate(new Date());
        Log.d(TAG, "testDateUtil: weekofdate_"+weekOfDate);


        boolean today = DateUtil.isToday(System.currentTimeMillis() - 10000);
        Log.d(TAG, "testDateUtil: today_"+today);

//        yyyy-MM-dd HH:mm:ss
        Date date = DateUtil.stringToDate("16-10-5", DateUtil.DatePattern.ALL_TIME);
        Log.d(TAG, "testDateUtil: date_"+date);



    }



}