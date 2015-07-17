package com.tiny.framework.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2015/7/7.
 */
public abstract class SingleCheckAdapter<T> extends RecyclerViewAdapter<T> {
    int mCheckedPosition;

    public SingleCheckAdapter(Context mContext, List<T> mList, RecyclerView mRecyclerView) {
        super(mContext, mList, mRecyclerView);
    }

    public SingleCheckAdapter(Context mContext, List<T> mList) {
        super(mContext, mList);
    }
    public void setCheckedPosition(int position){

        this.mCheckedPosition=position;
    }

    @Override
    public void bindViewData(BaseViewHolder holder, int position, int mViewType) {
        ((ISingleChecked)holder).setCheckedPosition(mCheckedPosition);
        super.bindViewData(holder, position, mViewType);
    }


    public interface ISingleChecked{
        void setCheckedPosition(int mCheckedPosition);
    }
}
