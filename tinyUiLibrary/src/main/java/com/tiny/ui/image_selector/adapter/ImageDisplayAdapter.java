package com.tiny.ui.image_selector.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.tiny.framework.ui.recyclerview.BaseViewHolder;
import com.tiny.framework.ui.recyclerview.RecyclerViewAdapter;
import com.tiny.ui.image_selector.bean.Image;
import com.tiny.ui.image_selector.ui.DisplayItemChangedListener;

import java.util.List;

/**
 * Created by Administrator on 2015/6/15.
 */
public abstract  class ImageDisplayAdapter extends RecyclerViewAdapter<Image> {

    private int mItemSize;
    private DisplayItemChangedListener mChangedListener;
    private boolean showSelectUi;


    public ImageDisplayAdapter(Context mContext, List<Image> mList, RecyclerView mRecyclerView) {
        super(mContext, mList, mRecyclerView);
    }

    public ImageDisplayAdapter(Context mContext, List<Image> mList) {
        super(mContext, mList);
    }


    public void setDisplayItemChangedListener(DisplayItemChangedListener l){
        this.mChangedListener=l;
    }
    public void setMultiModeUiVisibility(boolean show){
        this.showSelectUi=show;
    }

    @Override
    public void bindViewHolderItem(BaseViewHolder mHolder, int mViewType) {
        if(mHolder instanceof ImgDisplayViewHolder){
            ImgDisplayViewHolder vh= (ImgDisplayViewHolder) mHolder;
            vh.setMultiUiVisibility(showSelectUi);
        }
        super.bindViewHolderItem(mHolder, mViewType);
        RecyclerView.LayoutParams lp= (RecyclerView.LayoutParams) mHolder.itemView.getLayoutParams();
        if(mItemSize!=0&&lp.height!=mItemSize){
            lp.height=mItemSize;
            mHolder.itemView.setLayoutParams(lp);
        }


    }

    @Override
    public void bindViewData(BaseViewHolder holder, int position, int mViewType) {
        if(holder instanceof ImgDisplayViewHolder){
            ImgDisplayViewHolder vh= (ImgDisplayViewHolder) holder;
            vh.setItemChangedListener(mChangedListener);
            if(mItemSize!=0){
                vh.setImageSize(mItemSize);
            }
        }
        RecyclerView.LayoutParams lp= (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if(mItemSize!=0&&lp.height!=mItemSize){
            lp.height=mItemSize;
            holder.itemView.setLayoutParams(lp);
        }
        super.bindViewData(holder, position, mViewType);
    }

    @Override
    protected RecyclerView.LayoutParams createDefaultLayoutParams() {
        RecyclerView.LayoutParams lp= super.createDefaultLayoutParams();
        if(mItemSize!=0){
            lp.height=mItemSize;
        }
        return lp;
    }


    public void setItemSize(int size){
        if(mItemSize==size){
            return ;
        }
        mItemSize=size;
        notifyDataSetChanged();
    }
}
