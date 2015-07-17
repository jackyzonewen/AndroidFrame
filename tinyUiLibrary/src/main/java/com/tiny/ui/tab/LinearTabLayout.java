package com.tiny.ui.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.FrameLayout;

import com.tiny.ui.R;
import com.tiny.ui.util.DisplayUtil;

/**
 * Created by Administrator on 2015/6/11.
 */
public class LinearTabLayout extends FrameLayout implements TabLayout {





    TabListener mTabListener;

    TabContainerLayout mTabContainer;
    DisplayMetrics dm;


    private int mIndicatorHeight;
    private int mIndicatorColor;
    private int mIndicatorMargin;

    private Paint mPaint;
    public LinearTabLayout(Context context) {
        this(context, null);
    }

    public LinearTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        dm=context.getResources().getDisplayMetrics();
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.TabLayout);
        mIndicatorHeight= a.getDimensionPixelSize(R.styleable.TabLayout_indicatorHeight, (int) dipToPx(DEFAULT_INDICATOR_HEIGHT));
        TypedValue value=new TypedValue();
        int defaultColor=0;
        boolean result=context.getTheme().resolveAttribute(R.attr.colorAccent,value,true);
        if(result){
            defaultColor=value.resourceId;
        }else
            defaultColor=getResources().getColor(R.color.green);
        mIndicatorColor=a.getColor(R.styleable.TabLayout_indicatorColor, defaultColor);
        mIndicatorMargin= a.getDimensionPixelSize(R.styleable.TabLayout_indicatorMargin, 0);
        a.recycle();
        mTabContainer = new TabContainerLayout(context, attrs);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(0,0,0,mIndicatorHeight);
        mTabContainer.setTabListener(mInternalListener);
        addView(mTabContainer, lp);
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mIndicatorColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mIndicatorWidth=mTabContainer.getTabTextWidth();
        canvas.drawRect(mIndicatorWidth * mTabContainer.getCurrentTab() + mIndicatorMargin, getMeasuredHeight() - getIndicatorHeight(),
                mIndicatorWidth * (mTabContainer.getCurrentTab() + 1) - mIndicatorMargin, getMeasuredHeight(),mPaint);
    }

    @Override
    public void setTabs(Tab[] tabs) {
        mTabContainer.clear();
        mTabContainer.addTabs(tabs, true);
    }
    public void setTabListener(TabListener l){
        this.mTabListener=l;
    }

    @Override
    public void setCurrentItem(int index) {
        mTabContainer.setCurrentTab(index);
    }

    @Override
    public int getCurrentItem() {
       return  mTabContainer.getCurrentTab();

    }

    @Override
    public void updateTabs() {
        mTabContainer.updateTabView();
    }

    @Override
    public void setIndicatorColor(int color) {
        this.mIndicatorColor=color;
        mPaint.setColor(mIndicatorColor);
        invalidateIndicator();
    }

    @Override
    public void setIndicatorHeight(int height) {
        this.mIndicatorHeight=height;
        invalidateIndicator();
    }

    @Override
    public void setIndicatorMargin(int margin) {
        this.mIndicatorMargin=margin;
        invalidateIndicator();
    }

    @Override
    public int getIndicatorColor() {
        return mIndicatorColor;
    }

    @Override
    public int getIndicatorHeight() {
        return mIndicatorHeight;
    }

    @Override
    public int getIndicatorMargin() {
        return mIndicatorMargin;
    }

    protected void invalidateIndicator(){
        invalidate(0,getMeasuredHeight()-mIndicatorHeight,getWidth(),getMeasuredHeight());
    }
    TabListener mInternalListener=new TabListener() {
        @Override
        public void onTabSelect(Tab tab) {
            invalidateIndicator();
            if(mTabListener!=null){
                mTabListener.onTabSelect(tab);
            }
        }
    };


    protected float dipToPx(int dip){
       return    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,dm);
    }
}
