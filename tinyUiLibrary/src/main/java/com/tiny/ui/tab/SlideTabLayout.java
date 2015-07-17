package com.tiny.ui.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.HorizontalScrollView;

import com.tiny.ui.R;

import java.util.Observable;

/**
 * Created by Administrator on 2015/6/11.
 */
public class SlideTabLayout extends HorizontalScrollView implements ViewPagerTabLayout,ViewPager.OnPageChangeListener {

    public static final String TAG="SlideTabLayout";

    TabListener mTabListener;

    TabContainerLayout mTabContainer;
    DisplayMetrics dm;
    ViewPager mViewPager;
    ViewPager.OnPageChangeListener mOutListener;

    private int mIndicatorHeight;
    private int mIndicatorColor;
    private int mIndicatorMargin;
    private Paint mPaint;

    private float mSlideOffset;
    private int mSlidePosition;
    TabObserver mObserver;
    boolean slidingEnable=true;


    public SlideTabLayout(Context context) {
        super(context);
    }

    public SlideTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        setHorizontalScrollBarEnabled(false);
        dm = context.getResources().getDisplayMetrics();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabLayout);
        mIndicatorHeight = a.getDimensionPixelSize(R.styleable.TabLayout_indicatorHeight, (int) dipToPx(DEFAULT_INDICATOR_HEIGHT));
        TypedValue value = new TypedValue();
        int defaultColor = 0;
        boolean result = context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        if (result) {
            defaultColor = value.resourceId;
        } else
            defaultColor = getResources().getColor(R.color.green);
        mIndicatorColor = a.getColor(R.styleable.TabLayout_indicatorColor, defaultColor);
        mIndicatorMargin = a.getDimensionPixelSize(R.styleable.TabLayout_indicatorMargin, 0);
        a.recycle();
        mTabContainer = new TabContainerLayout(context, attrs);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 0, mIndicatorHeight);
        mTabContainer.setTabListener(new InternalTabListener());
        addView(mTabContainer, lp);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mIndicatorColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mIndicatorWidth = mTabContainer.getTabTextWidth();
        int  offset= (int) (mSlideOffset *mTabContainer.getTabTextWidth());
        int left=mIndicatorWidth * mSlidePosition+offset + mIndicatorMargin;
        int right=mIndicatorWidth * (mSlidePosition + 1) - mIndicatorMargin + offset;
        int top=getMeasuredHeight() - getIndicatorHeight();
        int bottom=getMeasuredHeight();

        canvas.drawRect(left, top,
                right, bottom, mPaint);
    }

    public void setViewPager(ViewPager mViewPager) {
        setViewPager(mViewPager, 0);
    }

    @Override
    public void setViewPager(ViewPager mViewPager, int initialPosition) {
        this.mViewPager = mViewPager;
        PagerAdapter mAdapter=mViewPager.getAdapter();
        if(mObserver!=null){
            mAdapter.unregisterDataSetObserver(mObserver);
            mTabContainer.removeAllViews();
            smoothScrollTo(0,0);
        }
        if(mObserver==null){
            mObserver=new TabObserver();
            mAdapter.registerDataSetObserver(mObserver);
        }
        mViewPager.setOnPageChangeListener(this);
        populateTabLayout();
    }

    @Override
    public void setPageChangeListener(ViewPager.OnPageChangeListener mListener) {
        this.mOutListener = mListener;
    }

    @Deprecated
    @Override
    public void setTabs(Tab[] tabs) {

    }
    public void setSlideEnable(boolean enable){
        this.slidingEnable=enable;
    }

    private void populateTabLayout() {
        mTabContainer.clear();
        PagerAdapter mAdapter = mViewPager.getAdapter();
        Tab[] tabs = new Tab[mAdapter.getCount()];
        if (!(mAdapter instanceof TabResourceProvider)) {
            throw new ClassCastException("the PagerAdapter must implements TabResourceProvider");
        }
        TabResourceProvider provider = (TabResourceProvider) mAdapter;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            Tab tab = new Tab(i, provider.getTabTitle(i));
            if (provider.getTabIcon(i) != null) {
                tab.setIcon(provider.getTabIcon(i));
            }
            tabs[i] = tab;
        }
        mTabContainer.addTabs(tabs, false);
    }

    @Override
    public int getCurrentItem() {
        return 0;
    }

    @Override
    public void setCurrentItem(int index) {
        mTabContainer.setCurrentTab(index);
    }

    @Override
    public void setTabListener(TabListener l) {
        this.mTabListener = l;
    }

    @Override
    public void updateTabs() {
        populateTabLayout();
    }

    @Override
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    @Override
    public void setIndicatorColor(int color) {
        this.mIndicatorColor = color;
        mPaint.setColor(mIndicatorColor);
        invalidateIndicator();
    }

    @Override
    public int getIndicatorHeight() {
        return mIndicatorHeight;
    }

    @Override
    public void setIndicatorHeight(int height) {
        this.mIndicatorHeight = height;
        invalidateIndicator();
    }

    @Override
    public int getIndicatorMargin() {
        return mIndicatorMargin;
    }

    @Override
    public void setIndicatorMargin(int margin) {
        this.mIndicatorMargin = margin;
        invalidateIndicator();
    }

    protected void smoothToTab(int index) {
        int x = (mTabContainer.getChildAt(index)).getLeft()-(mTabContainer.getTabTextWidth());
        smoothScrollTo(x, 0);
    }

    protected float dipToPx(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm);
    }

    protected void invalidateIndicator() {
        invalidate(0, getMeasuredHeight() - mIndicatorHeight, getWidth(), getMeasuredHeight());
    }
    public interface TabResourceProvider {
        String getTabTitle(int position);

        Drawable getTabIcon(int position);
    }
    private class InternalTabListener implements TabListener {
        @Override
        public void onTabSelect(Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition(), true);
            smoothToTab(tab.getPosition());
            invalidateIndicator();
            if (mTabListener != null) {
                mTabListener.onTabSelect(tab);
            }
        }
    }
    private void scrollIndicator(int position ,float offset){
        mSlidePosition=position;
        mSlideOffset=offset;
    }
    public enum State {
        IDLE, GOING_LEFT, GOING_RIGHT
    }
        private int oldPage;
        private State mState;
        private boolean isSmall(float positionOffset) {
            return Math.abs(positionOffset) < 0.001||Math.abs(1-positionOffset) < 0.001;
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(mOutListener!=null){
                mOutListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }
//
            if (mState == State.IDLE && positionOffset > 0) {
                oldPage = getCurrentItem();
                mState = position == oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
            }

            boolean goingRight = position == oldPage;
            if (mState == State.GOING_RIGHT && !goingRight)
                mState = State.GOING_LEFT;
            else if (mState == State.GOING_LEFT && goingRight)
                mState = State.GOING_RIGHT;
            mSlideOffset =positionOffset;
            if(slidingEnable){
                scrollIndicator(position, positionOffset);
                if ((positionOffset)==0) {
                    mState = State.IDLE;
                    mSlideOffset =0;
                }
                invalidateIndicator();
            }

        }

        @Override
        public void onPageSelected(int position) {
            if(mOutListener!=null){
                mOutListener.onPageSelected( position);
            }
            setCurrentItem(position);
            smoothToTab(position);
            invalidateIndicator();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(mOutListener!=null){
                mOutListener.onPageScrollStateChanged(state);
            }
            switch (state){
                case ViewPager.SCROLL_STATE_IDLE:
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    break;
        }
    }
    private class TabObserver extends DataSetObserver{

        @Override
        public void onChanged() {
            updateTabs();
        }

        @Override
        public void onInvalidated() {
            updateTabs();
        }
    }
}
