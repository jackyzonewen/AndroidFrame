package com.tiny.framework.ui.recyclerview.interfaces;


/**
 * Created by Administrator on 2015/5/5.
 */
public interface IListViewHolder<T> extends IBaseViewHolder {


    void bindView(int mViewType);

    void bindData(int position, T mItem, int mViewType);


}