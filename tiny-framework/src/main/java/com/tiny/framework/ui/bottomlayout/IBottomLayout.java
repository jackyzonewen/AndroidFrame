package com.tiny.framework.ui.bottomlayout;

import android.graphics.Color;

/**
 * Created by tiny on 2015/4/1.
 */
public interface IBottomLayout {


    /**
     * 加载中显示的text
     * @param mLoadText
     */
    void  setLoadingText(CharSequence mLoadText);

    /**
     * 一次加载完成时显示text
     * @param mLoadedText
     */
    void setLoadedText(CharSequence mLoadedText);


    void setLoadOverText(CharSequence mOverText);



    /**
     * 显示加载中
     */
    void showLoadingBottomView();

    /**
     * 显示已加载完成
     */
    void showLoadedBottomView();

    /**
     * 显示数据加载完
     */
    void showOverBottomView();


    void setBottomTextColor(int color);



}
