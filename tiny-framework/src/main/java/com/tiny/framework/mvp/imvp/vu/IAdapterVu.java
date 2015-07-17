package com.tiny.framework.mvp.imvp.vu;

import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.tiny.framework.ui.common.OnErrorViewClickListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnEmptyListener;

/**
 * Created by tiny on 2015/4/18.
 */
public interface IAdapterVu extends IBaseVu {


    int SHOW_PROGRESS = 1;
    int DISMISS_PROGRESS = 0;
    void showProgressView();

    void hideProgressView();


    AdapterView getAdapterView();


    void setAdapter(BaseAdapter mAdapter);


    void setOnItemClickListener(AdapterView.OnItemClickListener mItemClick);


    /**
     * 如果有empytView，检查是否list size==0
     * size=0显示emptyView
     */
    void makeEmptyVisibility();


    void showErrorView();

    void hideErrorView();

    void setEmptyListener(OnEmptyListener mEmptyListener);

    void setErrorViewListener(OnErrorViewClickListener mErrorListener);


}
