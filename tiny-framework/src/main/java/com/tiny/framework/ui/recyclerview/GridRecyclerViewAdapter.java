package com.tiny.framework.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tiny.framework.ui.recyclerview.interfaces.ISpanSizeResolve;

import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public abstract  class GridRecyclerViewAdapter<T extends ISpanSizeResolve> extends RecyclerViewAdapter<T> {

    GridLayoutManager.SpanSizeLookup mSpanSizeLookup;


    public GridRecyclerViewAdapter(Context mContext, List<T> mList,  RecyclerView mRecyclerView ) {
        super(mContext, mList,  mRecyclerView);
        mSpanSizeLookup=new BaseSpanSizeLookup();
    }

    public GridLayoutManager.SpanSizeLookup getDefautSpanSizeLookup() {
        return mSpanSizeLookup;
    }

    public class BaseSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            return mList.get(position).getSpanSize();
        }
    }





}
