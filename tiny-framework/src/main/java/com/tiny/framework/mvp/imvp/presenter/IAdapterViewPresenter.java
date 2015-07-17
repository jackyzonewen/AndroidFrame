package com.tiny.framework.mvp.imvp.presenter;

import android.widget.BaseAdapter;

import com.tiny.framework.mvp.imvp.vu.IAdapterVu;

/**
 * Created by tiny on 2015/4/14.
 */
public interface IAdapterViewPresenter<V extends IAdapterVu> extends IPresenter<V> {
    void setAdapter(BaseAdapter mAdapter);

    BaseAdapter getAdapter();


    void notifyDataSetChanged();
}
