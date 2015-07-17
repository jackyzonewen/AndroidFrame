package com.tiny.ui.camera_preview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tiny.framework.mvp.impl.presenter.activity.PresentActivityBase;
import com.tiny.framework.util.Arith;
import com.tiny.framework.util.BitmapUtil;
import com.tiny.framework.util.FileUtil;
import com.tiny.ui.R;

import java.io.File;
import java.io.IOException;

/**
 * <p>Created by Administrator on 2015/6/9.</p>
 * <p>注册该 MultiImageSelectorActivity</p>
 * <p>android:theme="@style/Theme.AppCompat.Light.NoActionBar"</p>
 */
public class CameraPreViewActivity extends PresentActivityBase<CameraPreViewVu> implements CameraPreViewVu.EventCallBack{


    public static final int KB=1024;

    public static final int MB=1024*1024;

    public static final int REQ_CAMERA = 0x11;

    /**
     * 是否在拍照后直接返回，返回原照片的路径
     */
    public static final String EXTRA_RETURN_AFTER_CAMERA="returnAfterCamera";
    /**
     * 是否在创建出720x1280的bitmap后直接返回 ,返回720x1280图片的路径
     */
    public static final String EXTRA_RETURN_AFTER_SCALE="returnAfterScale";

    /**
     * 拍照后的文件保存路径，拍照文件存在sd卡
     */
    public static final String CAMERA_DIR = "DCIM/Camera";

    /**
     * 480X800文件的外部存储路径
     */
    public static final String EXTERNAL_TEMP_CACHE = "DCIM/TEMP";

    /**
     * 输出文件的路径
     */
    public static final String RESULT_PATH="path";
    /**
     * 输出文件是原图还是压缩图
     * true 原图 false 压缩图
     */
    public static final String RESULT_ORIGINAL="userOriginal";

    /**
     *  在data/data里的缓存路径
     *
     */
    public static final String INTERNAL_CACHE="img";

    /**
     * 480x800文件存在InternalCahce还是ExternalCache
     * 默认 true 在Internal
     */
    public static final String EXTRA_INTERNAL_CACHE="isInternalCache";
    File mCameraFile;
    String m720x1280Path;
    Bitmap m720x1280Bitmap;
    boolean returnAfterCamera;
    boolean returnAfterScale;
    boolean internalCache;

    public static String getCameraDir() {
        return Environment
                .getExternalStorageDirectory().getPath() + "/" + CAMERA_DIR;
    }



    @Override
    public Class<CameraPreViewVu> getVuClass() {
        return CameraPreViewVu.class;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_camera_preview;
    }

    @Override
    protected void initData() {
        internalCache=getIntent().getBooleanExtra(EXTRA_INTERNAL_CACHE,true);
        returnAfterCamera=getIntent().getBooleanExtra(EXTRA_RETURN_AFTER_CAMERA,false);
        returnAfterScale=getIntent().getBooleanExtra(EXTRA_RETURN_AFTER_SCALE,false);
        takePhoto();
    }

    @Override
    public void onVuCreated() {
        getVuInstance().setCallBack(this);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar_multi);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void sendBitmap() {
        if(getVuInstance().userOriginalBitmap()){
            finishTask(m720x1280Path, true);
        }else{
            try {
                String path = BitmapUtil.compressBitmapToFile(get720PCacheDir(), m720x1280Bitmap, 90);
                File file=new File(m720x1280Path);
                if(file.exists()){
                    //这里用压缩图的话原图文件直接删掉
                    file.delete();
                }
                finishTask(path, false);

            } catch (IOException e) {
                e.printStackTrace();
                showToast(R.string.create_temp_file_error);
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQ_CAMERA){
            if(resultCode==RESULT_OK){
                if(returnAfterCamera){
                    String path=mCameraFile.getPath();
                    finishTask(path,true);
                    return;
                }
                m720x1280Bitmap = create720x1280Bitmap(mCameraFile);
                m720x1280Path =BitmapUtil.saveBitmap(getInternalCacheDir(), m720x1280Bitmap);
                getVuInstance().setBitmapCacheSize(calculateBitmapLength());
                getVuInstance().displayImage(m720x1280Bitmap);
                if(returnAfterScale){
                    finishTask(m720x1280Path,true);
                }
            } else{
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void finishTask(String path,boolean userOriginal){
        Intent intent=new Intent();
        intent.putExtra(RESULT_PATH, path);
        intent.putExtra(RESULT_ORIGINAL, userOriginal);
        setResult(RESULT_OK, intent);
        finish();
    }

    private String calculateBitmapLength() {
        File file=new File(m720x1280Path);
        String text=null;
        if(file.exists()){
            long length=file.length();
           double result= Arith.div(length, KB,0);
            if(result>1024){
                result=Arith.div(length,MB,0);
                text=result+"Mb";
            }else{
                text=result+"Kb";
            }
            return text;
        }
        return text;
    }

    public void takePhoto() {
        try {
            clearCameraFile();
            mCameraFile = FileUtil.createTmpFile(this, getCameraDir());
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
                // 设置系统相机拍照后的输出路径
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameraFile));
                startActivityForResult(cameraIntent, REQ_CAMERA);
            } else {
                showToast(R.string.open_camera_error);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToast(R.string.create_temp_file_error);
        }
        // 跳转到系统照相机
    }


    /**
     * 将图片降低大小至720x1280
     * @param file
     * @return
     */
    public Bitmap create720x1280Bitmap(File file){
        int size= BitmapUtil.calculateInSampleSize(file, 720f, 1280);
        BitmapFactory.Options opt=new BitmapFactory.Options();
        opt.inSampleSize=size;
        opt.inPreferredConfig= Bitmap.Config.ARGB_4444;
        Bitmap bitmap=BitmapFactory.decodeFile(file.getPath(), opt);
        Bitmap newBitmap=BitmapUtil.zoom(bitmap, 720, 1280);
        bitmap.recycle();
        int c=BitmapCompat.getAllocationByteCount(newBitmap);
        return newBitmap;
    }

    /**
     * 清理掉拍照源文件
     */
    private void clearCameraFile(){
        if(returnAfterCamera){
            return;
        }
        if(mCameraFile!=null&&mCameraFile.exists()){
         try{
            boolean result= mCameraFile.delete();
             int i=0;
         }catch (Exception e){
             e.printStackTrace();
         }
        }
    }

    /**
     * 这里在生成480X4800的文件保存路径时，可以选择内部或外部存储
     * 如果是存在外部存储，图片无法被用于裁剪
     * @return
     */
    public String get720PCacheDir(){
        if(internalCache){
            return getInternalCacheDir();
        }else{
            return getExternalImgCacheDir();
        }
    }


    public String getInternalCacheDir(){
        File file= FileUtil.getInternalCacheDir(this);
        return file.getPath()+"/"+INTERNAL_CACHE;
    }
    public String getExternalImgCacheDir(){
        return Environment
                .getExternalStorageDirectory().getPath() + "/" + EXTERNAL_TEMP_CACHE;
    }

    @Override
    protected void onDestroy() {
        clearCameraFile();
        if(m720x1280Bitmap!=null&&!m720x1280Bitmap.isRecycled()){
            m720x1280Bitmap.recycle();
            m720x1280Bitmap=null;
        }
        System.gc();
        super.onDestroy();
    }
}
