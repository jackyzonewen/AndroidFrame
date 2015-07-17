package com.tiny.ui.image_selector.ui;


import com.tiny.ui.image_selector.bean.Image;

/**
 * 图片展示列表里的item选中状态改变后的回调
 * Created by Administrator on 2015/6/15.
 */
public interface DisplayItemChangedListener {

    void onDisplayItemStateChanged(Image image, int position);
}
