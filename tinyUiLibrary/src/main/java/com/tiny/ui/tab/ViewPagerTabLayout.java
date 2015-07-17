package com.tiny.ui.tab;

import android.support.v4.view.ViewPager;

/**
 * Created by Administrator on 2015/6/11.
 */
public interface ViewPagerTabLayout extends TabLayout {




    public void setViewPager(ViewPager mViewPager, int initialPosition);


    public void setPageChangeListener(ViewPager.OnPageChangeListener mListener);

}
