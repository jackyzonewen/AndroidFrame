package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tiny on 2015/4/11.
 */
public interface IChildViewHolder<T extends IChildItem> extends IExpandableViewHolder {

    void bindView(boolean isLastChild, View convertView, ViewGroup parent);

    void bindData(int groupPosition, int childPosition, boolean isLastChild, T mItem);


}
