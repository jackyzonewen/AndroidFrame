package com.tiny.framework.mvp.imvp.vu;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * Created by tiny on 2015/4/18.
 */
public interface IExListVu extends IAdapterVu {

    ExpandableListView getExpandableListView();


    void setExListAdapter(ExpandableListAdapter mAdapter);



}
