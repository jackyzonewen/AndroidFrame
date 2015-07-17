package com.tiny.framework.mvp.imvp.vu;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.tiny.framework.ui.common.OnErrorViewClickListener;
import com.tiny.framework.ui.recyclerview.RecyclerViewAdapter;
import com.tiny.framework.ui.recyclerview.interfaces.OnEmptyListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnLastItemVisibleListener;

/**
 * Created by Administrator on 2015/5/5.
 */
public interface IRecycleViewVu   extends IBaseVu  {

    int SHOW_PROGRESS = 1;
    int DISMISS_PROGRESS = 0;

    int COMPLETE_REFRESH = 0x101;


    void showProgressView();

    void hideProgressView();


    RecyclerView getRecyclerView();


    void setAdapter(RecyclerView.Adapter mAdapter);

    void completeRefresh();

    void completeRefreshDelay(long delay);

    SwipeRefreshLayout getSwipeRefreshLayout();

    void postRefresh();

    void setOnLastItemVisibleListener(OnLastItemVisibleListener mLastItemListener);
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
