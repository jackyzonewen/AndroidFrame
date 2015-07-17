package com.tiny.ui.image_selector.ui;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tiny.framework.mvp.impl.vu.BaseVuImpl;
import com.tiny.ui.R;
import com.tiny.ui.photoview.ViewPagerFixed;

/**
 * Created by Administrator on 2015/6/10.
 */
public class GalleryVu extends BaseVuImpl {

    TextView num_percent;
    TextView tv_preview, tv_finish;
    Button send_button;

    private ViewPagerFixed pager;

    @Override
    protected void onViewCreate() {
        super.onViewCreate();
        pager = (ViewPagerFixed) findViewById(R.id.gallery01);
//        pager.setPageMargin(10);
        send_button = findButton(R.id.send_button);
        num_percent = findTextView(R.id.num_percent);
        send_button.setVisibility(View.GONE);
    }

    public void setAdapter(PagerAdapter adapter) {
        pager.setAdapter(adapter);
    }

    public void setOnPagerChangeListener(ViewPager.OnPageChangeListener pagerChangeListener) {
        pager.setOnPageChangeListener(pagerChangeListener);
    }

    public ViewPagerFixed getViewPager() {
        return pager;
    }

    public void refreshPercent(int current, int all) {
        num_percent.setText(current + "/" + all);
    }

}
