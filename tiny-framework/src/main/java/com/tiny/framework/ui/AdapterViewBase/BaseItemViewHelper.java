package com.tiny.framework.ui.AdapterViewBase;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiny.framework.R;


/**
 * Created by tiny on 2015/4/11.
 */
public abstract class BaseItemViewHelper<T> implements IBaseViewHolder {
    protected Context mContext;
    protected  AdapterView mAdapterView;
    protected View mItemView;

    /**
     * 尽量构造方法别带其他参数，否则adapter里通过反射构造会失败
     * 如果一定要待其他参数 重载adapter里的createViewItemInstance方法 返回一个正确的ViewHolder实例
     *
     * @param context
     */
    public BaseItemViewHelper(Context context) {
        this.mContext = context;
    }

    public Context getContext() {
        return mContext;
    }


    public AdapterView getAdapterView() {
        return mAdapterView;
    }

    @Override
    public View getItemView() {
        return mItemView;
    }

    @Override
    public void setItemView(View mItemView) {
    this.mItemView=mItemView;
    }

    @Override
    public void setAdapterView(AdapterView mAdapterView) {
        this.mAdapterView = mAdapterView;
    }

    public TextView findTextView(int id) {
        return (TextView) (getItemView().findViewById(id));
    }

    public ImageView findImageView(int id) {
        return (ImageView) (getItemView().findViewById(id));
    }
    public void setDefaultImage(ImageView mImageView) {
        mImageView.setImageResource(getLoadingImageRes(mImageView.getId()));
    }



    /**
     * 如果要对不同的ImageView提供不同的loading Image ，重写该方法
     *
     * @param id ImageView的id
     * @return
     */
    protected int getLoadingImageRes(int id) {
        return R.color.light_gray;
    }




}
