package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.widget.ExpandableListView;

import com.tiny.framework.ui.AdapterViewBase.IBaseViewHolder;

/**
 * Created by tiny on 2015/4/11.
 */
public interface IExpandableViewHolder extends IBaseViewHolder{

    void setExpandListView(ExpandableListView mExListView);


    ExpandableListView getExpandListView();
}
