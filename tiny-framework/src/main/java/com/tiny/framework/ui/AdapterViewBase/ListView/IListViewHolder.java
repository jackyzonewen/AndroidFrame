package com.tiny.framework.ui.AdapterViewBase.ListView;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import com.tiny.framework.ui.AdapterViewBase.IBaseViewHolder;

/**
 * Created by Administrator on 2015/3/28.
 */
public interface IListViewHolder<T> extends IBaseViewHolder {


    void bindView(View convertView, int mViewType);

    void bindData(int position, T mItem, int mViewType);


}
