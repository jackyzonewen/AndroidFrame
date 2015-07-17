package com.tiny.framework.mvp.impl.presenter.fragment;

import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.tiny.framework.mvp.impl.vu.ExListVuImpl;
import com.tiny.framework.mvp.imvp.presenter.IExListViewPresenter;

/**
 * Created by tiny on 2015/4/18.
 */
public abstract class ExListPresenterFragmentBase<V extends ExListVuImpl> extends AdapterViewPresenterFragment<V> implements IExListViewPresenter<V> {

    public static final String TAG = "ExListPresenterFragmentBase";


    BaseExpandableListAdapter mAdapter;


    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Deprecated
    @Override
    public BaseAdapter getAdapter() {
        return null;
    }

    @Deprecated
    @Override
    public void setAdapter(BaseAdapter mAdapter) {

    }

    @Override
    public void notifyDataSetChanged() {
        if (this.mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BaseExpandableListAdapter getExListAdapter() {
        return mAdapter;
    }

    @Override
    public void setExListAdapter(BaseExpandableListAdapter mAdapter) {
        this.mAdapter = mAdapter;
        getVuInstance().setExListAdapter(mAdapter);
    }

    @Override
    public ExpandableListView getExpandableListView() {
        return getVuInstance().getExpandableListView();
    }
}
