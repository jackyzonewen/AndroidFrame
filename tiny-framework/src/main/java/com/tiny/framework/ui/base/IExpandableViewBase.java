package com.tiny.framework.ui.base;

import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * Created by tiny on 2015/4/11.
 */
public interface IExpandableViewBase extends IAdapterViewBase {


    void setExpandableAdapter(BaseExpandableListAdapter mAdapter);
    BaseExpandableListAdapter getExpandableAdapter();

    ExpandableListView getExpandableListView();
}
