package com.tiny.framework.mvp.imvp.vu;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by tiny on 2015/4/14.
 */
public interface IPtrListVu extends IBaseListVu {


    int COMPLETE_REFRESH = 2;


    PullToRefreshListView getPtlListView();

    void setPullRefreshDisable(boolean enable);




}
