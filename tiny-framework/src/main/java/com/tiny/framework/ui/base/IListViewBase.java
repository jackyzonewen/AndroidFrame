package com.tiny.framework.ui.base;

import android.widget.ListView;

import com.tiny.framework.ui.bottomlayout.BaseBottomLayout;


public interface IListViewBase extends IAdapterViewBase {

    int DIVIDER_NORMAL = 8;//dp
    int DIVIDER_LARGE = 12;//dp

    boolean isAddBottomLayout();

    //必须先添加bottomView
    BaseBottomLayout createBottomLayout();

    ListView getListView();


    void refreshBottomView();


    void setBottomState(BaseBottomLayout.State mState);



}
