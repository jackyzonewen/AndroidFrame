package com.tiny.ui.image_selector.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.tiny.framework.mvp.impl.presenter.activity.PresentActivityBase;
import com.tiny.ui.R;
import com.tiny.ui.image_selector.bean.Image;
import com.tiny.ui.photoview.PhotoView;
import com.tiny.ui.photoview.ViewPagerFixed;
import com.tiny.ui.util.RecyclerViewPagerAdapter;

/**
 * 这个是用于进行图片浏览时的界面
 *
 * @author king
 * @version 2014年10月18日  下午11:47:53
 * @QQ:595163260
 */
public class GalleryActivity extends PresentActivityBase<GalleryVu> {
    private Intent intent;
    //获取前一个activity传过来的position
    private int position;
    private int location = 0;
    private ArrayList<View> listViews = null;
    private GalleryAdapter adapter;
    private ArrayList<Image> images = new ArrayList<>();

    @Override
    public void onVuCreated() {
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = intent.getIntExtra("position", 1);
        images = intent.getParcelableArrayListExtra("data");
        if (images != null && images.size() > 0) {
            if (Image.isCameraItem(images.get(0))) {
                images.remove(0);
                position = position - 1;                          //这里做个偏差
            }
        }
    }

    @Override
    public Class<GalleryVu> getVuClass() {
        return GalleryVu.class;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.plugin_camera_gallery;
    }

    @Override
    protected void initData() {
        System.gc();
        getVuInstance().setOnPagerChangeListener(pageChangeListener);
        adapter = new GalleryAdapter(images.size());
        getVuInstance().setAdapter(adapter);
        getVuInstance().getViewPager().setCurrentItem(position);
        getVuInstance().refreshPercent(position + 1, images.size());
    }

    @Override
    protected void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_multi_detail);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
        public void onPageSelected(int arg0) {
            location = arg0;
            getVuInstance().refreshPercent(location + 1, images.size());
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageScrollStateChanged(int arg0) {
        }
    };
    class GalleryAdapter extends RecyclerViewPagerAdapter{
        private int size;
        public GalleryAdapter(int size) {
            this.size=size;
        }

        @Override
        public int getViewType(int position) {
            return size;
        }

        @Override
        public View onCreateView(ViewGroup container, int position) {
            PhotoView img = new PhotoView(container.getContext());
            img.setBackgroundColor(0xff000000);

            img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            return img;
        }

        @Override
        public void onBindView(int position, View view) {
            PhotoView v= (PhotoView) view;
            Image img=images.get(position);
            v.setImageBitmap(img.getBitmap());
        }

        @Override
        public int getCount() {
            return size;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        for(Image img:images){
            img.recycle();
        }
        super.onDestroy();
    }
}
