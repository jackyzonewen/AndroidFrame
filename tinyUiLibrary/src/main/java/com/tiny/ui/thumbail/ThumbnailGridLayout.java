package com.tiny.ui.thumbail;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tiny.ui.R;

import java.util.List;


/**
 * Created by Administrator on 2015/5/26.
 */
public class ThumbnailGridLayout extends GridLayout {


    public static final float IMAGE_SIZE = 80;//DP

    public static final float IMAGE_MARGIN = 4;//DP


    private int mThumbnailViewWidth;
    private int mThumbnailViewHeight;
    private int mThumbnailViewMargin;
    private int mThumbnailBackRes;

//    private int mMaxWidth;

    private boolean hasLargeView;


    private ThumbnailLoader mThumbnailLoader;

    private Thumbnail mLargeThumbnailItem;


    private SparseArray<ThumbnailImageView> mThumbnailViews;

    private ThumbnailImageView mLargeView;


    public ThumbnailGridLayout(Context context) {
        this(context, null);
    }

    public ThumbnailGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ThumbnailGrid);
        mThumbnailViewWidth = a.getDimensionPixelSize(R.styleable.ThumbnailGrid_thumbnailWidth, dpToPx(IMAGE_SIZE));
        mThumbnailViewHeight = a.getDimensionPixelSize(R.styleable.ThumbnailGrid_thumbnailHeight, dpToPx(IMAGE_SIZE));
        mThumbnailViewMargin = a.getDimensionPixelSize(R.styleable.ThumbnailGrid_thumbnailMargin, dpToPx(IMAGE_MARGIN));
        mThumbnailBackRes = a.getResourceId(R.styleable.ThumbnailGrid_thumbnailBackground, R.color.thumb_default_back);
        a.recycle();
        mThumbnailViews = new SparseArray<ThumbnailImageView>();
        setColumnCount(3);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        Log.e("ThumbnailGridLayout","onMeasure");
        super.onMeasure(widthSpec, heightSpec);
    }

    public void prepare(boolean isLargeView) {
        this.hasLargeView = isLargeView;
        if (isLargeView) {
            prepareLargeView();
        } else {
            prepareRowItems();
        }
    }
    /**
     *
     * @param mThumbnailItems
     */
    public void setThumbnailItems(@NonNull List<Thumbnail> mThumbnailItems) {
        if (mThumbnailItems == null) {
            return;
        }
        if (mThumbnailItems.size() == 1) {
            applyLargeItem(mLargeView,mThumbnailItems.get(0));
        } else {
            dispatchThumbnailItems(mThumbnailItems);
        }
    }

    private void prepareRowItems() {
        int[][] items = new int[][]{{0, 0}, {0, 1}, {0, 2}, {1, 0}, {1, 1}, {1, 2}};
        for (int i = 0; i < items.length; i++) {
            int[] obj = items[i];
            addImageViewWithPosition(i, obj[0], obj[1]);
        }
    }

    private void addImageViewWithPosition(int position, int row, int column) {
        ThumbnailImageView mImageView = new ThumbnailImageView(getContext());
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setSpec(row, column);
        mImageView.setBackgroundResource(mThumbnailBackRes);
        MarginLayoutParams mSource = new MarginLayoutParams(mThumbnailViewWidth, mThumbnailViewHeight);
        mSource.setMargins(mThumbnailViewMargin, mThumbnailViewMargin, mThumbnailViewMargin, mThumbnailViewMargin);
        Spec imageRow = spec(row);
        Spec imageColumn = spec(column, 1, CENTER);
        LayoutParams params = new LayoutParams(mSource);
        params.rowSpec = imageRow;
        params.columnSpec = imageColumn;
        mImageView.setSpec(row, column);
        addView(mImageView, params);
        mImageView.setThumbnailLoader(mThumbnailLoader);
        mThumbnailViews.put(position, mImageView);

    }

    private void prepareLargeView() {
        mLargeView = new ThumbnailImageView(getContext());
        mLargeView.setThumbnailLoader(mThumbnailLoader);
        mLargeView.setBackgroundResource(mThumbnailBackRes);
        addView(mLargeView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }




    /**
     * 是否 处理大图模式
     * @return
     */
    public boolean hasLargeThumbnailItem() {
        return hasLargeView;
    }


    /**
     * 处理大图数据
     */
    private Thumbnail applyLargeItem(ThumbnailImageView mView,Thumbnail item) {
        mLargeThumbnailItem=item;
        ViewGroup.LayoutParams lp=mLargeView.getLayoutParams();
        lp.width=item.getThumbnailWidth();
        lp.height=item.getThumbnailHeight();
        mView.setThumbnailData(item);
        return item;
    }

    /**
     * @param loader
     */
    public void setThumbnailLoader(ThumbnailLoader loader) {
        this.mThumbnailLoader = loader;
        for (int i = 0; i < mThumbnailViews.size(); i++) {
            ThumbnailImageView view = mThumbnailViews.get(i);
            if (view != null) {
                view.setThumbnailLoader(mThumbnailLoader);
            }
        }
        if(mLargeView!=null){
            mLargeView.setThumbnailLoader(mThumbnailLoader);
        }
    }

    /**
     * 处理多张缩略图数据
     *
     * @param mThumbnailItems
     */
    private void dispatchThumbnailItems(List<Thumbnail> mThumbnailItems) {
        int size = mThumbnailItems.size();
        if (size == 4) {
            ThumbnailImageView v0 = mThumbnailViews.get(0);
            v0.setThumbnailData(mThumbnailItems.get(0));
            showThumbnailView(v0);
            ThumbnailImageView v1 = mThumbnailViews.get(1);
            v1.setThumbnailData(mThumbnailItems.get(1));
            showThumbnailView(v1);
            ThumbnailImageView v2 = mThumbnailViews.get(2);
            v2.removeThumbnailData();
            hideThumbnailView(v2);
            ThumbnailImageView v3 = mThumbnailViews.get(3);
            v3.setThumbnailData(mThumbnailItems.get(2));
            showThumbnailView(v3);
            ThumbnailImageView v4 = mThumbnailViews.get(4);
            v4.setThumbnailData(mThumbnailItems.get(3));
            showThumbnailView(v4);
            ThumbnailImageView v5 = mThumbnailViews.get(5);
            v5.removeThumbnailData();
            hideThumbnailView(v5);
        } else {
            for (int i = 0; i < mThumbnailViews.size(); i++) {
                ThumbnailImageView view = mThumbnailViews.get(i);
                if (i < size) {
                    showThumbnailView(view);
                    view.setThumbnailData(mThumbnailItems.get(i));
                } else {
                    hideThumbnailView(view);
                    view.removeThumbnailData();
                }
            }
        }

    }

    private void hideThumbnailView(ThumbnailImageView view) {
        view.setVisibility(View.GONE);
    }

    private void showThumbnailView(ThumbnailImageView view) {
        view.setVisibility(View.VISIBLE);

    }

    private int dpToPx(float dp) {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public int getThumbnailWidth() {
        return mThumbnailViewWidth;
    }

    public int getThumbnailHeight() {
        return mThumbnailViewHeight;
    }
}
