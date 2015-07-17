package com.tiny.framework.ui.base;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by tiny on 2015/4/11.
 */
public interface IPtlListBase extends IListViewBase {

    PullToRefreshListView getPtlListView();

    void setPullRefreshDisable(boolean enable);

}
