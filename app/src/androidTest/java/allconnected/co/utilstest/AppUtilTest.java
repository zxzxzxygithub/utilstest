package allconnected.co.utilstest;

import android.content.pm.PackageInfo;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dsw.androidutils.AppUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppUtilTest {

    private static final String TAG = "ContentValues";

    @Test
    public void getPacketName() throws Exception {
        String packetName = AppUtil.getPacketName(getContext());
        Log.d(TAG, "getPacketName: "+packetName);


    }

    @Test
    public void getVersionName() throws Exception {
        String versionName = AppUtil.getVersionName(getContext());
        Log.d(TAG, "getVersionName: "+versionName);

    }

    @Test
    public void getVersionCode() throws Exception {
        int versionCode = AppUtil.getVersionCode(getContext());
        Log.d(TAG, "getVersionCode: "+versionCode);

    }

    @Test
    public void getInstalledPackages() throws Exception {

        List<PackageInfo> installedPackages = AppUtil.getInstalledPackages(getContext());
        String pName;
        for (PackageInfo p :
                installedPackages) {
            pName = p.packageName;
            Log.d(TAG, "testInstalledPackages: pName_"+pName);

        }

    }

    public void getApplicationIcon() throws Exception {

    }

    public void installApk() throws Exception {

    }



}
