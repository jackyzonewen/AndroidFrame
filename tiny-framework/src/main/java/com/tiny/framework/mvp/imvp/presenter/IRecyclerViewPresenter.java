package com.tiny.framework.mvp.imvp.presenter;

import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.tiny.framework.mvp.imvp.vu.IRecycleViewVu;

/**
 * Created by Administrator on 2015/5/5.
 */
public interface IRecyclerViewPresenter<V extends IRecycleViewVu> extends IPresenter<V>{

    void setAdapter(RecyclerView.Adapter mAdapter);

    RecyclerView.Adapter getAdapter();


    void notifyDataSetChanged();

}
