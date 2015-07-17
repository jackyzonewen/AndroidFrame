package com.tiny.framework.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2015/7/1.
 */
public class ArrayAdapter<T> extends RecyclerViewAdapter<T> {

    int mLayoutResId, mTextViewId;

    public ArrayAdapter(Context mContext, List<T> mList, RecyclerView mRecyclerView,int mLayoutResId,int mTextViewId) {
        super(mContext, mList, mRecyclerView);
        this.mLayoutResId=mLayoutResId;
        this.mTextViewId=mTextViewId;
    }
    public ArrayAdapter(Context mContext, List<T> mList, RecyclerView mRecyclerView,int mLayoutResId ) {
        this(mContext, mList, mRecyclerView, mLayoutResId, 0);
    }
    public ArrayAdapter(Context mContext, List<T> mList,int mLayoutResId) {
        this(mContext, mList, null, mLayoutResId);
    }
    public ArrayAdapter(Context mContext, List<T> mList,int mLayoutResId,int mTextViewId) {
        this(mContext, mList, null, mLayoutResId, mTextViewId);
    }
    @Override
    public BaseViewHolder createViewHolderByViewType(Context context, int viewType) {
        return new ArrayItemViewHolder<T>(View.inflate(context,mLayoutResId,null),mTextViewId);
    }
}
