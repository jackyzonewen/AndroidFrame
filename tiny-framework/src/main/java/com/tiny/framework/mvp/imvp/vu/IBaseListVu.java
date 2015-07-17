package com.tiny.framework.mvp.imvp.vu;

import android.widget.ListView;

import com.tiny.framework.ui.bottomlayout.BaseBottomLayout;

/**
 * Created by tiny on 2015/4/18.
 */
public interface IBaseListVu extends IAdapterVu {


    BaseBottomLayout getBottomLayout();

    void refreshBottomView();

    boolean isAddBottomLayout();

    //必须先添加bottomView
    BaseBottomLayout createBottomLayout();

    void setBottomState(BaseBottomLayout.State mState);

    ListView getListView();
}
