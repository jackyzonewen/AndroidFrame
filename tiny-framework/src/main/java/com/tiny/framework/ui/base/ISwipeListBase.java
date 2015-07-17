package com.tiny.framework.ui.base;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by tiny on 2015/4/11.
 */
public interface ISwipeListBase extends IListViewBase {


    SwipeRefreshLayout getSwipeRefreshLayout();


    void setColorsScheme(int... colors) ;

    void setRefresh(boolean enable);

    void setRefreshListener(SwipeRefreshLayout.OnRefreshListener mRefreshListener) ;
}
