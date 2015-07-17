package com.tiny.framework.mvp.impl.vu;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.tiny.framework.mvp.imvp.vu.IExListVu;

/**
 * Created by tiny on 2015/4/18.
 */
public class ExListVuImpl extends AdapterVuImpl implements IExListVu {


    ExpandableListView mExListView;

    @Override
    protected void initView(Context context, int layoutId, ViewGroup container) {
        super.initView(context, layoutId, container);
        mExListView = (ExpandableListView) getContentView()
                .findViewById(com.tiny.framework.R.id.base_adapter_view);
    }

    @Override
    public ExpandableListView getExpandableListView() {
        return mExListView;
    }

    @Override
    public void setExListAdapter(ExpandableListAdapter mAdapter) {
        mExListView.setAdapter(mAdapter);
    }

    /**
     * use {@link #setExListAdapter(ExpandableListAdapter mAdapter)} instead ;
     * @param mAdapter
     */
    @Deprecated
    @Override
    public void setAdapter(BaseAdapter mAdapter) {
    }
}
