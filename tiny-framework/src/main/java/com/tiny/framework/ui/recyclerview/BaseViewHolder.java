package com.tiny.framework.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiny.framework.R;
import com.tiny.framework.ui.recyclerview.interfaces.IListViewHolder;

/**
 *
 * Created by Administrator on 2015/5/5.
 * 尽量构造方法别带其他参数，否则adapter里通过反射构造会失败
 */
public abstract  class BaseViewHolder<T> extends RecyclerView.ViewHolder implements IListViewHolder<T>{


    protected  RecyclerView mRecyclerView;


    public BaseViewHolder(View itemView) {
        super(itemView);
    }



    @Override
    public View getItemView() {
        return super.itemView;
    }


    @Override
    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView=mRecyclerView;
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
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


    public Context getContext(){
        return itemView.getContext();
    }

    //TO DO
    //这里优化成返回一个drwable 提高扩展性
    /**
     * 如果要对不同的ImageView提供不同的loading Image ，重写该方法
     *
     * @param id ImageView的id
     * @return
     */
    public int getLoadingImageRes(int id) {
        return R.color.light_gray;
    }

}
