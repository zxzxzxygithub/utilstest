package co.allconnected.utils;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 *
 * @author michael
 * @time 16/12/5 下午4:38
 */
public class MD5Util {

    /**
     * MD5 32位加密
     *
     * @author michael
     * @time 16/12/5 下午4:33
     */
    public static String encrypt(String inStr) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(inStr.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }


}
