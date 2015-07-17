package com.tiny.framework.mvp.imvp.presenter;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.tiny.framework.mvp.imvp.vu.IExListVu;

/**
 * Created by tiny on 2015/4/18.
 */
public interface IExListViewPresenter<V extends IExListVu>  extends IAdapterViewPresenter<V>{


    void setExListAdapter(BaseExpandableListAdapter mAdapter);

    BaseExpandableListAdapter getExListAdapter();

    ExpandableListView getExpandableListView();

    }
