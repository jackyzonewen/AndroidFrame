package com.tiny.ui.image_selector.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.tiny.framework.ui.recyclerview.BaseViewHolder;
import com.tiny.framework.util.StringUtil;
import com.tiny.image.AsyncImageBufferLoader;
import com.tiny.image.Config;
import com.tiny.image.ImageCallBack2;
import com.tiny.image.NativeImageLoader;
import com.tiny.ui.R;
import com.tiny.ui.image_selector.ui.DisplayImageView;

/**
 * Created by Administrator on 2015/6/15.
 */
public abstract  class BaseRecyclerViewItem<T> extends BaseViewHolder<T> {
    public BaseRecyclerViewItem(View itemView) {
        super(itemView);
    }




    public void loadNetImage(ImageView mImageView, String url, String tag, Config config) {
        if (mImageView != null && !StringUtil.isEmpty(url) && !StringUtil.isEmpty(tag)) {
            mImageView.setTag(tag);
            setDefaultImage(mImageView);
            Bitmap bitmap = AsyncImageBufferLoader.getInstance().loadDrawable(mImageView, url, mCallBack, config);
            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }


    protected ImageCallBack2 mCallBack = new ImageCallBack2() {
        @Override
        public void imageLoaded(ImageView imageView, Bitmap bitmap, Config config) {
            if (config!=null&&mRecyclerView != null && !StringUtil.isEmpty(config.getTag()) && bitmap != null) {
                ImageView mImageView = (ImageView) mRecyclerView.findViewWithTag(config.getTag());
                if (mImageView != null) {
                    mImageView.setImageBitmap(bitmap);
                }
            }
        }

    };
    public void loadNativeImage(int position,String path,int mTargetWidth,int mTargetHeight,DisplayImageView mDisplay){
        if(path==null){
            return;
        }
        Config config=new Config(false);
        config.set(mTargetWidth, mTargetHeight);
        String tag=path+position;
        mDisplay.setTag(tag);
        config.setTag(tag);
        config.setUrl(path);
        Bitmap bitmap= NativeImageLoader.getInstance().loadNativeImage(mDisplay,path,config,callBack);
        if(bitmap!=null){
            mDisplay.setBitmap(bitmap);
            mDisplay.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }else{
            mDisplay.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mDisplay.setImageResource(R.drawable.multi_image_select_default_error);
        }

    }
    NativeImageLoader.NativeImageCallBack callBack=new NativeImageLoader.NativeImageCallBack() {
        @Override
        public void onImageLoader(ImageView imageView, Bitmap bitmap, String path, Config config) {
            String tag = config.getTag();
            DisplayImageView mDisplay = (DisplayImageView) getRecyclerView().findViewWithTag(tag);
            if(mDisplay==null){
                return;
            }
            if(bitmap!=null){
                mDisplay.setBitmap(bitmap);
                mDisplay.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }else{
                mDisplay.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                mDisplay.setImageResource(R.drawable.multi_image_select_default_error);
            }
        }
    };

    @Override
    public void setDefaultImage(ImageView mImageView) {
        super.setDefaultImage(mImageView);
    }
}
