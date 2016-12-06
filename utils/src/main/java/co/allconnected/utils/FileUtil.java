package co.allconnected.utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作类
 *
 * @author michael
 * @time 16/12/5 下午8:02
 */
public class FileUtil {

    /**
     * 创建文件
     *
     * @param path 路径+文件名
     * @author michael
     * @time 16/12/5 下午7:11
     */
    public static File createFile(String path) throws IOException {
        File file = new File(path);
        // 先创建该文件对应的文件夹
        createDir(file.getParent());
        if (file.exists() && file.isFile()) {
            final File to = new File(file.getAbsolutePath() + System.currentTimeMillis());
            file.renameTo(to);
            to.delete();
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    /**
     * 创建文件夹
     *
     * @author michael
     * @time 16/12/5 下午7:11
     */
    public static File createDir(String dirName) {
        File dir = new File(dirName);
        if (dir.exists() && dir.isDirectory())
            return dir;
        // mkdir只能创建一级目录 ,mkdirs可以创建多级目录
        boolean ok = dir.mkdirs();
        return dir;
    }


    /**
     * 将字符串转为指定编码后写入文件。当编码参数为null时，使用系统默认编码。
     */
    public static void writeFile(String filename, String text, String charset) throws IOException {
        if (charset != null && charset.length() > 0)
            writeFile(filename, text.getBytes(charset));
        else
            writeFile(filename, text.getBytes(Charset.defaultCharset().toString()));
    }

    /**
     * 将文件全部读出并转为指定编码的字符串返回。当编码参数为null时，使用系统默认编码。
     */
    public static String readFile(String filename, String charset) throws IOException {
        if (charset != null && charset.length() > 0)
            return new String(readFile(filename), charset);
        else
            return new String(readFile(filename), Charset.defaultCharset().toString());
    }

    /**
     * 将文件全部读到byte数组中
     */
    public static byte[] readFile(String filename) throws IOException {
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(new File(filename));
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            byte[] data = new byte[(int) fc.size()];
            bb.get(data);
            return data;
        } finally {
            if (stream != null) stream.close();
        }
    }

    /**
     * 将byte数组内容全部写入文件
     */
    public static void writeFile(String filename, byte[] data) throws IOException {

        if (data == null || data.length == 0)
            return;

        FileOutputStream fos = null;

        try {
            new File(filename).getParentFile().mkdirs();
            fos = new FileOutputStream(filename);
            fos.write(data);
        } finally {
            if (fos != null) fos.close();
        }
    }

    /**
     * 对象序列化到本地
     */
    public static <T> void write2Serializable(String path, List<T> data) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从本地反序列化对象
     *
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> readFromSerializable(Context context, String fileName) {
        ArrayList<T> data = new ArrayList<T>();

        ObjectInputStream in = null;
        try {
            String fullName = getLocalFilename(context, fileName);
            if (!(new File(fullName)).exists()) {
                return data;
            }

            in = new ObjectInputStream(new FileInputStream(fullName));
            data.addAll((ArrayList<T>) in.readObject());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return data;
    }

    /**
     * 获取data下的file文件夹下的文件的名称
     *
     * @author michael
     * @time 16/12/5 下午7:03
     */
    public static String getLocalFilename(Context context, String filename) {
        return context.getFilesDir().getAbsolutePath() + File.separator
                + filename;
    }

    /**
     * 获取data下的cache文件夹下的文件名称
     *
     * @author michael
     * @time 16/12/5 下午7:03
     */
    public static String getCacheFilename(Context context, String filename) {
        return context.getCacheDir().getAbsolutePath() + File.separator
                + filename;
    }

    /**
     * 复制asset文件到指定目录
     *
     * @param oldPath asset下的路径
     * @param newPath SD卡下保存路径
     */
    public static void copyAssets(Context context, String oldPath, String newPath) {
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(newPath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyAssets(context, oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {// 如果是文件
                is = context.getAssets().open(oldPath);
                fos = new FileOutputStream(new File(newPath));
                byte[] buffer = new byte[1024];
                int byteCount;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    if (fos != null) {
                        fos.flush();// 刷新缓冲区
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @author michael
     * @time 16/12/5 下午9:00
     */
    public static String getMimeType(String filePath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        String mime = "text/plain";
        if (filePath != null) {
            try {
                mmr.setDataSource(filePath);
                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
            } catch (IllegalStateException e) {
                return mime;
            } catch (IllegalArgumentException e) {
                return mime;
            } catch (RuntimeException e) {
                return mime;
            }
        }
        return mime;
    }

}
