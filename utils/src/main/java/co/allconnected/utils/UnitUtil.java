package co.allconnected.utils;

/**
 * 单位换算
 *
 * @author michael
 * @time 16/12/5 下午8:51
 */
public class UnitUtil {

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    public static final long TB = 1024 * GB;

    /**
     * 把byte转换为kb，mb，gb或者tb
     *
     * @author michael
     * @time 16/12/5 下午8:51
     */
    public static String formatBytes(long bytes) {
        if (bytes >= 10 * TB)
            return bytes / TB + "TB";
        else if (bytes > TB)
            return format(bytes / (float) TB) + "TB";
        else if (bytes >= 10 * GB)
            return bytes / GB + "GB";
        else if (bytes > GB)
            return format(bytes / (float) GB) + "GB";
        else if (bytes >= 10 * MB)
            return bytes / MB + "MB";
        else if (bytes > MB)
            return format(bytes / (float) MB) + "MB";
        else if (bytes >= 10 * KB)
            return bytes / KB + "KB";
        else
            return format(bytes / (float) KB) + "KB";
    }

    private static float format(float value) {
        value = (float) (Math.round(value * 100)) / 100;
        return value;
    }


}
