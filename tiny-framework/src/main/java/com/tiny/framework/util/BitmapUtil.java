package com.tiny.framework.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BitmapUtil {
    public static final int MB = 1024 * 1024;
    public static final int KB = 1024;

    /**
     * 图片上传的最大宽度，如果大于这个值需要压缩，
     * 防止原图上传后下载下来无法显示在Imgeview
     */
    public static final int MAX_WIDTH = 1280;
    /**
     * 图片上传的最大高度，如果大于这个值需要压缩，
     * 防止原图上传后下载下来无法显示在Imgeview
     * //
     */
    public static final int MAX_SIZE_HDIP = 800;

    public static final int MAX_SIZE_XHDIP = 1280;

    public static final int MAX_SIZE_XXHDIP = 1920;


    /**
     * ����ͼƬ
     *
     * @param bitmap
     * @return
     */
    public static Bitmap zoom(Bitmap bitmap, float zf) {
        Matrix matrix = new Matrix();
        matrix.postScale(zf, zf);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * 描述:		图片按较小边比例缩放
     *
     * @param size   要缩放的尺寸
     * @param bitmap
     * @return
     */
    public static Bitmap zoom(float size, Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        if (size == 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width < size || height < size) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        float scale = 1.0f;
        //宽边较小按 宽边缩放
        if (width < height) {
            scale = size / width;
        } else {
            //长边较小按长边缩放
            scale = size / height;

        }
        matrix.postScale(scale, scale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return bitmap;
    }

    /**
     * ����ͼƬ
     *
     * @param bitmap
     * @return
     */
    public static Bitmap zoom(Bitmap bitmap, float wf, float hf) {
        Matrix matrix = new Matrix();
        matrix.postScale(wf, hf);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    public static Bitmap zoom(Bitmap bitmap, int width, int height) {
        if (bitmap == null) {
            return null;
        }
        if (width <= 0 || height <= 0) {
            return bitmap;
        }
        float wf = ((float) width) / bitmap.getWidth();
        float hf = ((float) height) / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(wf, hf);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * ͼƬԲ�Ǵ���
     *
     * @param bitmap
     * @param roundPX
     * @return
     */
    public static Bitmap getRCB(Bitmap bitmap, float roundPX) {
        // RCB means
        // Rounded
        // Corner Bitmap
        Bitmap dstbmp = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(dstbmp);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return dstbmp;
    }

    /**
     * 保存图片到sd卡
     *
     * @param path
     * @param bitmap
     * @return
     */
    public static String saveBitmap(String dir, Bitmap bitmap) {
        SimpleDateFormat format = new SimpleDateFormat("yymmddhhmmss");
        String date = format.format(new Date());
        return saveBitmap(dir, bitmap, date + ".jpg");
    }

    /**
     * @param dir
     * @param bitmap
     * @param fileName 包含图片后缀
     * @return
     */
    public static String saveBitmap(String dir, Bitmap bitmap, String fileName) {
        if (bitmap == null) {
            return null;
        }
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }
        File image = new File(dir + "/" + fileName);
        if (image.exists()) {
            image.delete();
        }
        if (!image.exists()) {
            try {
                image.createNewFile();
            } catch (IOException e) {
                System.out.println("创建文件出错");
                e.printStackTrace();
                return null;
            }
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(image);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.close();
            return image.getPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public static Bitmap readBitamp(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return readBitmap(path, options);
    }

    public static Bitmap readBitmap(String path, Options opt) {
        InputStream is = null;
        File file = new File(path);
        if (file.exists()) {
            return  BitmapFactory.decodeFile(path, opt);
        }
        return null;
    }

    // 将图片压缩至小于size
    public static InputStream compressToIS(Bitmap bitmap, int size) {
        if (bitmap == null) {
            return null;
        }
        Log.d("compressToIS", "图片压缩前大小" + bitmap.getByteCount() / 1024 + "kb");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(CompressFormat.JPEG, quality, baos);
        // 大于1M就压缩 每次压成90%质量
        // 这里限定图片大小1M
        try {
            while (baos.toByteArray().length > (size) && quality > 0) {
                baos.reset();
                quality -= 20;
                bitmap.compress(CompressFormat.JPEG, quality, baos);
                baos.flush();
            }
            ByteArrayInputStream bays = new ByteArrayInputStream(baos.toByteArray());
            baos.close();
            Log.d("compressToIS", "图片压缩后大小" + baos.toByteArray().length / 1024 + "Kb");
            return bays;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 压缩到 MB/5
     * @param bitmap
     * @return
     */
    public static InputStream compressToIS(Bitmap bitmap) {
        return compressToIS(bitmap, MB / 5);
    }

    public static byte[] compressBitmapToBytes(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Log.d("compressToIS", "图片压缩前大小" + bitmap.getByteCount() / 1024 + "kb");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(CompressFormat.JPEG, quality, baos);
        // 大于1M就压缩 每次压成90%质量
        // 这里限定图片大小1M
        try {
            while (baos.toByteArray().length > (MB / 2) && quality > 0) {
                baos.reset();
                quality -= 20;
                bitmap.compress(CompressFormat.JPEG, quality, baos);
                baos.flush();
            }
            Log.d("compressToIS", "图片压缩后大小" + baos.toByteArray().length / 1024 + "Kb");
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap getRoundedCornerBitmap(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap outBmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(outBmp);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(0xFF424242);
        canvas.drawRoundRect(rectF, 5.0F, 5.0F, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);
        return outBmp;
    }

    // 将图片压缩至小于2M
    public static Bitmap compressToBitmap(Bitmap bitmap) {
        return compressToBitmap(bitmap, MB / 2);
    }

    public static Bitmap compressToBitmap(Bitmap bitmap, int size) {
        if (bitmap == null) {
            return null;
        }
        InputStream is = compressToIS(bitmap, size);
        if (is != null) {
            Bitmap result = BitmapFactory.decodeStream(is);
            result.getByteCount();
            return result;
        } else return null;
    }

    public static int calculateInSampleSize(Options options,
                                            int maxSize) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > maxSize || width > maxSize) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) maxSize);
            final int widthRatio = Math.round((float) width / (float) maxSize);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 图片尺寸是否过大 需要压缩尺寸再上传
     *
     * @param opt     图片文件
     * @param maxSize 期望最大长边尺寸 不能为0
     * @return
     */
    public static  boolean reqZoomBitmap(Options opt, int maxSize) {
        if (opt == null) {
            return false;
        }

        if (opt.outWidth > maxSize) {
            return true;
        }
        if (opt.outHeight > maxSize) {
            return true;
        }
        int size = calculateInSampleSize(opt, maxSize);

        return false;
    }

    /**
     * 压缩尺寸
     *
     * @param options
     * @param maxSize
     * @return
     */
    public static int calculateInSampleSize2(Options options,
                                             int maxSize) {
        int test = calculateInSampleSize(options, maxSize);
        int inSampleSize = 1;
        while (!(options.outWidth / inSampleSize <= maxSize && options.outHeight / inSampleSize <= maxSize)) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    /**
     *
     * @param width
     * @param height
     * @return
     */
    public static int calculateInSampleSize(@NonNull File file,float width,float height){
        Options opt=new Options();
        opt.inJustDecodeBounds=true;
         BitmapFactory.decodeFile(file.getPath(),opt);
        int size=1;
        final int heightRatio = Math.round((float) opt.outHeight / (float) height);
        final int widthRatio = Math.round((float) opt.outWidth / (float) width);
        size= Math.min(heightRatio,widthRatio);
        if(size<=0){
            size=1;
        }
        return size;

    }


    /**
     * 根据期望尺寸检查是否需要压缩图片的尺寸
     *
     * @param path
     * @param maxSize
     * @return
     */

    public static Bitmap compressBitmapInSize(Options opt, String path, int maxSize) {
        //尺寸过大做尺寸压缩处理
        int inSampleSize = BitmapUtil.calculateInSampleSize2(opt, maxSize);
        opt.inJustDecodeBounds = false;
        opt.inSampleSize = inSampleSize;
//        opt.inPreferredConfig=Config.RGB_565;
        return BitmapFactory.decodeFile(path, opt);
    }

    public static String transBitmapToString(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        String string = null;
        byte[] bytes = compressBitmapToBytes(bitmap);
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }
    public static String compressBitmapToFile(String dir,Bitmap bitmap,int quality) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yymmddhhmmss");
        String date = format.format(new Date());
        File file = new File(dir, "compress"+ "_" + date + ".jpg");
        File parent = new File(dir);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        return file.getPath();
    }
}
