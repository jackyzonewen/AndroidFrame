package com.tiny.framework.ui.AdapterViewBase.ListView;

import android.content.Context;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by Administrator on 2015/3/28.
 * 当item有多种类型时，T 必须实现IMultiTypeViewItem接口
 * getItemViewType();
 */
public abstract  class MultiItemViewAdapter<T extends IMultiTypeViewItem> extends CommonAdapter {

    int viewTypeSize;

    public MultiItemViewAdapter(Context mContext, List<T> mList,int viewTypeSize) {
        this(mContext, mList, null, viewTypeSize);
    }

    public MultiItemViewAdapter(Context mContext, List<T> mList, AdapterView mAdapterView,int viewTypeSize) {
        super(mContext, mList,  mAdapterView);
        this.viewTypeSize=viewTypeSize;
    }

    @Override
    public int getViewTypeCount() {
        return viewTypeSize;
    }

    @Override
    public int getItemViewType(int position) {
        IMultiTypeViewItem mItem = (IMultiTypeViewItem) getItem(position);
        return mItem.getViewType();
    }






}
