package com.tiny.ui.image_selector.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.tiny.ui.R;
import com.tiny.ui.image_selector.bean.Image;
import com.tiny.ui.image_selector.ui.DisplayImageView;
import com.tiny.ui.image_selector.ui.DisplayItemChangedListener;


/**
 * Created by Administrator on 2015/6/15.
 */
public abstract  class ImgDisplayViewHolder extends BaseRecyclerViewItem<Image> implements View.OnClickListener{
    int mImageSize;
    CheckBox mCheckBox;
    DisplayImageView mDisplayView;
    DisplayItemChangedListener mListener;
    boolean mMultiUiVisibility;



    public ImgDisplayViewHolder(View itemView) {
        super(itemView);
    }
    public void setItemChangedListener(DisplayItemChangedListener mListener){
        this.mListener=mListener;
    }

    @Override
    public void bindView(int mViewType) {
        mCheckBox= (CheckBox) itemView.findViewById(R.id.checkbox_item_image_display);
        mCheckBox.setVisibility(mMultiUiVisibility?View.VISIBLE:View.GONE);
        mDisplayView= (DisplayImageView) itemView.findViewById(R.id.image_display_item);
        mCheckBox.setOnClickListener(this);
    }

    @Override
    public void bindData(int position, Image mItem, int mViewType) {
        mCheckBox.setChecked(mItem.isSelected);
        mCheckBox.setTag(mItem);
        mCheckBox.setTag(R.id.multi_position, position);
        loadNativeImage(position,mItem.path,mImageSize,mImageSize,mDisplayView);
        mDisplayView.setChecked(mItem.isSelected);
    }

    public void setMultiUiVisibility(boolean show){
        mMultiUiVisibility=show;
    }
    @Override
    public void onClick(View v) {
        CheckBox mCheckBox= (CheckBox) v;
        Image item= (Image) mCheckBox.getTag();
        RecyclerView.Adapter adapter=getRecyclerView().getAdapter();
        int position= (int) mCheckBox.getTag(R.id.multi_position);
        if(!item.isSelected&&isSelectedCountFully()){
            //设为选择状态之前检查是否选择的数量已达上线
            makeToast();
            adapter.notifyItemChanged(position);
            return ;
        }
        item.isSelected=!item.isSelected;
        adapter.notifyItemChanged(position);
        if(mListener!=null){
            mListener.onDisplayItemStateChanged(item,position);
        }
    }

    /**
     * 是否选择的图片数量已达最大
     * @return
     */
    public abstract boolean isSelectedCountFully();

    /**
     * 选择数量达到最大时，再选择时出现 提示
     */
    public abstract void makeToast();
    public void setImageSize(int size){
        if(mImageSize!=size){
            mImageSize=size;
        }
    }
}
