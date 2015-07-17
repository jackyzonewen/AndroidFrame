package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tiny on 2015/4/11.
 */
public interface IGroupViewHolder<T extends IGroupItem> extends IExpandableViewHolder {

    void bindView(boolean isExpanded, View convertView, ViewGroup parent);

    void bindData(int groupPosition, T mItem, boolean isExpanded);

}
