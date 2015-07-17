package com.tiny.ui.tab;

/**
 * Created by Administrator on 2015/6/11.
 */
 interface TabLayout {

    /**
     * 默认indicator高度 dp
     */
    int DEFAULT_INDICATOR_HEIGHT=2;


     void setTabs(Tab[] tabs);

     int getCurrentItem();

     void setCurrentItem(int index);

     void updateTabs();


     void setIndicatorColor(int color);

     void setIndicatorHeight(int height);

     void setIndicatorMargin(int margin);

     int getIndicatorColor();

     int getIndicatorHeight();

     int getIndicatorMargin();

     void setTabListener(TabListener l);

}
