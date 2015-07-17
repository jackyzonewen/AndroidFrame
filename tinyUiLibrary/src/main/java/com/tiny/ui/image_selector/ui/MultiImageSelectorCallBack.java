package com.tiny.ui.image_selector.ui;

import com.tiny.framework.mvp.impl.vu.AbstractVu;

/**
 * Created by Administrator on 2015/6/15.
 */
public interface MultiImageSelectorCallBack extends AbstractVu.CallBack{


    /**
     * 目录ist item click 事件
     * @param position
     */
    void onDirectorySelected(int position);

    /**
     * 图片点击事件
     * @param position
     */
    void onDisplayImageClick(int position);

    void preView();

    void finishTask(boolean isFromCamera);

}
