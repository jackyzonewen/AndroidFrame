package com.tiny.framework.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtil {
    public static File createFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        File parent=file.getParentFile();
        if(!parent.exists()){
            parent.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static boolean getSDcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 初始化路径
     */
    public static void initDir(String path) {
        File destDir = new File(path);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    public static void RecursionDeleteFile(String path) {
        File file = new File(path);
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    public static String readText(Context context, String path) {
        File file = new File(path);
        if (!file.exists()) {
            Log.d("readText", "文件:" + path + "不存在");
            return null;
        }
        InputStream is;
        try {
            is = new FileInputStream(file);
            ByteArrayBuffer bb = new ByteArrayBuffer(8192);
            int current = 0;
            while ((current = is.read()) != -1) {
                bb.append(current);
            }
            String reuslt = EncodingUtils.getString(bb.toByteArray(), "UTF8");
            is.close();
            is = null;
            return reuslt;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }

    public static File createTmpFile(Context context, String dir) throws IOException {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 已挂载
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File parent = new File(dir);
            if (!parent.exists()) {
                parent.mkdirs();
            }
            File tmpFile = new File(parent, fileName + ".jpg");
            tmpFile.createNewFile();
            return tmpFile;
        } else {
            File cacheDir = context.getCacheDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(cacheDir, fileName + ".jpg");
            tmpFile.createNewFile();
            return tmpFile;
        }
    }

    public static File getInternalCacheDir(Context context){
        return context.getCacheDir();
    }
}
