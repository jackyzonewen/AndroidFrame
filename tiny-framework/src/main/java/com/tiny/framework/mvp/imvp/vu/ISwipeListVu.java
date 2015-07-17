package com.tiny.framework.mvp.imvp.vu;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AbsListView;

/**
 * Created by tiny on 2015/4/18.
 */
public interface ISwipeListVu extends IBaseListVu {

    int COMPLETE_REFRESH = 1;


    SwipeRefreshLayout getSwipeRefreshLayout();



    void completeRefresh();

    void completeRefreshDelay(long delay);

}
