package com.tiny.ui.image_selector.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.tiny.framework.mvp.impl.presenter.activity.RecyclerViewPresentActivity;
import com.tiny.framework.ui.recyclerview.BaseViewHolder;
import com.tiny.ui.R;
import com.tiny.ui.camera_preview.CameraPreViewActivity;
import com.tiny.ui.image_selector.adapter.CameraViewHolder;
import com.tiny.ui.image_selector.adapter.FolderListAdapter;
import com.tiny.ui.image_selector.adapter.ImageDisplayAdapter;
import com.tiny.ui.image_selector.adapter.ImgDisplayViewHolder;
import com.tiny.ui.image_selector.bean.Folder;
import com.tiny.ui.image_selector.bean.Image;
import com.tiny.ui.util.GlobalLayoutCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/10.
 * 注册该 MultiImageSelectorActivity
 * android:theme="@style/Theme.AppCompat.Light.NoActionBar"
 * 如果要用显示camera,注册CameraPreViewActivity activity 见 {@link CameraPreViewActivity}
 */
public class MultiImageSelectorActivity extends RecyclerViewPresentActivity<MultiImageSelectorVu>
        implements MultiImageSelectorCallBack,DisplayItemChangedListener {

    /**
     * 最大可选择数量，默认9（最好不要多余9）
     * int
     */
    public static final String EXTRA_COUNT = "max_select_count";

    /**
     * 单选还是多选
     * int
     */
    public static final String EXTRA_MODE = "select_mode";


    public static final int MODE_SINGLE=0;

    public static final int MODE_MULTI=1;


    /**
     * 是否显示相机
     * boolean 默认 true
     */
    public static final String EXTRA_CAMERA = "show_camera";

    /**
     * 返回结果
     * arrayList<String>  not null
     */
    public static final String EXTRA_RESULT = "result";

    /**
     * 结果是否由为拍照文件
     */
    public static final String EXTRA_RESULT_CAMERA="isResultFromCamera";
    /**
     * 前置场景传入已选择图片路径 arrayList<String>
     */
    public static final String EXTRA_DEFAULT="default";


    static final int REQUEST_CAMERA=101;
    /**
     * laoder id
     */
    private static final int LOADER_ALL = 0;

    /**
     * 最大选择数量
     */
    private  int mMaxCount;
    /**
     *单选还是多选
     */
    private int mode;

    /**
     * 见CameraPreViewActivity$EXTRA_INTERNAL_CACHE
     */
    public static final String EXTRA_INTERNAL_CACHE="EXTRA_INTERNAL_CACHE";

    /**
     * 当前选择目录索引
     */
    private boolean showCamera;
    private int mSelectedPosition = 0;
    private boolean hasFolderGened;
    private boolean isInternalCache;
    /**
     * 前置场景传入已选择图片路径，也是输出的数据
     */
    private ArrayList<String> resultList = new ArrayList<>();
    // 文件夹数据
    private ArrayList<Folder> mResultFolder = new ArrayList<>();
    //当前文件夹下的数据
    private ArrayList<Image> mCurrentImage = new ArrayList<>();
    /**
     * 目录list adapter
     */
    private FolderListAdapter mFolderAdapter;
    /**
     * 目录list adapter
     */
    private ImageDisplayAdapter mDisplayAdapter;



    @Override
    protected int getContentViewId() {
        return R.layout.activity_image_selector;
    }

    @Override
    protected void initData() {
        prepareIntentData();
        updateDefaultItemCount(null);
        getVuInstance().setUiVisibility(mode == MODE_MULTI);
        getVuInstance().updateSelectedItemCount(mMaxCount, resultList.size());
        GridLayoutManager mLayoutManager=new GridLayoutManager(this,3);
        getRecyclerView().setLayoutManager(mLayoutManager );
        getRecyclerView().getItemAnimator().setSupportsChangeAnimations(false);
        List<Image> list=new ArrayList<>();
        mDisplayAdapter = new DisplayAdapter(this, list, getRecyclerView()) ;
        mDisplayAdapter.setMultiModeUiVisibility(mode == MODE_MULTI);
        mDisplayAdapter.setDisplayItemChangedListener(this);
        mFolderAdapter = new FolderListAdapter(this, mResultFolder);
        setAdapter(mDisplayAdapter);
        getVuInstance().setListPopAdapter(mFolderAdapter);
        getSupportLoaderManager().initLoader(LOADER_ALL, null, mLoaderCallback);
        getRecyclerView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = getRecyclerView().getMeasuredWidth();
                int size = width / 3;
                mDisplayAdapter.setItemSize(size);
                GlobalLayoutCompat.removeOnGlobalLayoutListener(getRecyclerView().getViewTreeObserver(), this);
            }
        });
    }
    void prepareIntentData(){
        showCamera=getIntent().getBooleanExtra(EXTRA_CAMERA,true);
        isInternalCache=getIntent().getBooleanExtra(EXTRA_INTERNAL_CACHE,true);
        mMaxCount=getIntent().getIntExtra(EXTRA_COUNT,9);
        mode=getIntent().getIntExtra(EXTRA_MODE,MODE_MULTI);
        ArrayList<String> array=getIntent().getStringArrayListExtra(EXTRA_DEFAULT);
        if(array!=null){
            resultList.addAll(array);
        }

    }
    @Override
    public void onVuCreated() {
        //释放内存，给加载图片腾出空间
        System.gc();
        getVuInstance().setCallback(this);
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
    public void onDirectorySelected(int position) {
        if (mSelectedPosition != position) {
            mFolderAdapter.setCheckedPosition(position);
            mFolderAdapter.notifyDataSetChanged();
            mSelectedPosition = position;
            Folder folder = mResultFolder.get(position);
            ArrayList<Image> images=folder.getImages();
            mDisplayAdapter.replaceList(images);
            mDisplayAdapter.notifyDataSetChanged();
            getVuInstance().updateDirTextView(folder.getName());
        }
    }

    @Override
    public void onDisplayImageClick(int position) {
        Intent intent=null;
        Image image=mDisplayAdapter.getItem(position);
        if(Image.isCameraItem(image)){
            intent=new Intent(this,CameraPreViewActivity.class);
            intent.putExtra(CameraPreViewActivity.EXTRA_INTERNAL_CACHE,isInternalCache);
            startActivityForResult(intent, REQUEST_CAMERA);
        }else{
            if(mode == MODE_SINGLE){
                resultList.clear();
                if(mDisplayAdapter.getItem(position)!=null){
                    resultList.add(mDisplayAdapter.getItem(position).path);
                }
                finishTask(false);
                return ;
            }
            intent=new Intent(this,GalleryActivity.class);
            Folder folder= mFolderAdapter.getItem(mFolderAdapter.getCheckedPosition());
            ArrayList<Image> list=folder.getImages();
            intent.putExtra("position",position);
            intent.putParcelableArrayListExtra("data",list);
            startActivity(intent);
//            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_CAMERA:
                    String path=data.getStringExtra(CameraPreViewActivity.RESULT_PATH);
                    resultList.clear();
                    if(path!=null){
                        resultList.add(path);
                    }
                    finishTask(true);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDisplayItemStateChanged(Image image, int position) {
        updateDefaultItemCount(image);
    }

    @Override
    public void preView() {
    }

    @Override
    public void finishTask(boolean isFromCamera) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT_CAMERA,isFromCamera);
        intent.putStringArrayListExtra(EXTRA_RESULT, resultList);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     *遍历设置默认选中图片的状态
     * @param collections
     */
    private void checkDefaultList(List<Image> collections){
        if(resultList.size()==0){
            return ;
        }
        for(String path:resultList){
            Image mTarget=null;
            for(Image image:collections){
                if(image.path!=null&&image.path.equalsIgnoreCase(path)){
                    mTarget=image;
                    break;
                }
            }
            if(mTarget!=null){
                mTarget.isSelected=true;
            }
        }
    }
    private int getSelectedItemSize(){
        return resultList.size();
    }


    void updateDefaultItemCount(Image image){
        if(image!=null&&image.path!=null){
            if(resultList.contains(image.path)){
                resultList.remove(image.path);
            }else{
                resultList.add(image.path);
            }
        }
        getVuInstance().updateSelectedItemCount(mMaxCount,resultList.size());
    }

    @Override
    public Class<MultiImageSelectorVu> getVuClass() {
        return MultiImageSelectorVu.class;
    }

    private class DisplayAdapter extends ImageDisplayAdapter{

        public DisplayAdapter(Context mContext, List<Image> mList, RecyclerView mRecyclerView) {
            super(mContext, mList, mRecyclerView);
        }

        public DisplayAdapter(Context mContext, List<Image> mList) {
            super(mContext, mList);
        }

        @Override
        public BaseViewHolder createViewHolderByViewType(Context context, int viewType) {
            View view=null;
            if(viewType==1){
                view=View.inflate(context, R.layout.item_camera,null);
                return new CameraViewHolder(view);
            }else{
                view=View.inflate(context, R.layout.item_display_image,null);
                return new DisplayViewHolder(view);
            }
        }
    }
    private class DisplayViewHolder extends ImgDisplayViewHolder {
        public DisplayViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void makeToast() {
            Toast.makeText(getContext(),"你最多只能选择 "+mMaxCount+"张图片",Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean isSelectedCountFully() {
            return getSelectedItemSize()==mMaxCount;
        }
    }

    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == LOADER_ALL) {
                CursorLoader cursorLoader = new CursorLoader(MultiImageSelectorActivity.this,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                        null, null, IMAGE_PROJECTION[2] + " DESC");
                return cursorLoader;
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            if (data != null) {
//                List<Image> images = new ArrayList<>();
                mCurrentImage.clear();
                int count = data.getCount();
                if (count > 0) {
                    data.moveToFirst();
                    do {
                        String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                        String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                        long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                        Image image = new Image(path, name, dateTime);
                        mCurrentImage.add(0,image);
                        if (!hasFolderGened) {
                            // 获取文件夹名称
                            File imageFile = new File(path);
                            File folderFile = imageFile.getParentFile();
                            Folder folder = new Folder();
                            folder.name = folderFile.getName();
                            folder.path = folderFile.getAbsolutePath();
                            folder.cover = image;
                            if (!mResultFolder.contains(folder)) {
                                ArrayList<Image> imageList = new ArrayList<>();
                                imageList.add(image);
                                folder.images = imageList;
                                mResultFolder.add(folder);
                            } else {
                                // 更新
                                Folder f = mResultFolder.get(mResultFolder.indexOf(folder));
                                f.images.add(image);
                            }
                        }
                    } while (data.moveToNext());
                    // 设定默认选择
                    checkDefaultList(mCurrentImage);
//                    ImageCache.tempSelectBitmap.clear();
//                    ImageCache.tempSelectBitmap.addAll(images);
                    if(mCurrentImage.size()>0){
                        Folder folder=Folder.CollectionFolder() ;
                        folder.cover= mCurrentImage.get(0);
                        folder.setImages(mCurrentImage);
                        mResultFolder.add(0, folder);
                        getVuInstance().updateDirTextView(folder.name);
                    }
                    mFolderAdapter.updateData(mResultFolder);
                    hasFolderGened = true;
                    if(showCamera){
                        mCurrentImage.add(0,Image.CameraItem());
                    }
                    mDisplayAdapter.replaceList(mCurrentImage);
                    mDisplayAdapter.notifyDataSetChanged();
                    mFolderAdapter.setCheckedPosition(0);
                }
            }

        }
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
