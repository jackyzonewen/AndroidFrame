package com.tiny.ui.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiny.ui.R;
import com.tiny.ui.util.DisplayUtil;

/**
 * Created by Administrator on 2015/6/11.
 */
public class TabContainerLayout extends LinearLayout   {
    public static final String TAG="TabContainerLayout";

    static final String STATE_INDEX="index";
    /**
     * 不用weight的情况下的tebText的默认宽度
     * dp
     */
    public static final int DEFAULT_TAB_WIDTH = 80;

    public static final int DEFAULT_TEXT_LAYOUT = R.layout.tab_text;


    private SparseArray<Tab> mTabs;
    private int mTabTextWidth;

    private TabListener mListener;

    private int mTextResId;

    private boolean isWeight;

    private int mIndex=0;




    public TabContainerLayout(Context context) {
        super(context);
    }


    public TabContainerLayout(Context context, AttributeSet attrs ) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        mTabs = new SparseArray<Tab>();
        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.TabLayout);
        mTextResId=a.getResourceId(R.styleable.TabLayout_textLayout,DEFAULT_TEXT_LAYOUT);
        mTabTextWidth=a.getDimensionPixelOffset(R.styleable.TabLayout_tabWidth,DisplayUtil.dip2px(context,DEFAULT_TAB_WIDTH));
        a.recycle();
    }

    public void setTabListener(TabListener mListener){
        this.mListener=mListener;
    }

    /**
     *
     * @param Tabs
     * @param weight true tab的宽用weight false 用mTabTextWidth
     */
    public void addTabs(Tab[] Tabs, boolean weight) {
        addTabs(Tabs, weight, 0);
    }

    /**
     * @param Tabs
     * @param weight        tabText的宽度是否用weight
     * @param mTabTextWidth tabText的宽度
     */
    private void addTabs(Tab[] Tabs, boolean weight, int mTabTextWidth) {
        this.mTabTextWidth = mTabTextWidth;
        if (mTabTextWidth == 0) {
            this.mTabTextWidth = DisplayUtil.dip2px(getContext(), DEFAULT_TAB_WIDTH);
        }
        isWeight = weight;
        for (Tab tab : Tabs) {
            mTabs.put(tab.getPosition(), tab);
        }
        inflateTabs();
    }

    private void inflateTabs() {
        for (int i = 0; i < mTabs.size(); i++) {
            Tab tab = mTabs.valueAt(i);
            TextView mTextView = null;
            mTextView = createDefaultTextView(mTextResId);
            final TextView finalMTextView = mTextView;
            mTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mTabTextWidth = finalMTextView.getMeasuredWidth();
                    finalMTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
            mTextView.setOnClickListener(new TabClickListener());
            mTextView.setText(tab.getText());
            addView(mTextView, i, createLayoutParams());
            if(i==0){
                mTextView.setSelected(true);
            }
        }
    }
    protected  TextView createDefaultTextView(int mTextResId){
        return  (TextView) View.inflate(getContext(), mTextResId, null);
    }

    public void setCurrentTab(int index){
        if(index>=mTabs.size()){
            Log.e(TAG,"setCurrentTab IndexOutOfSize");
            return ;
        }
        mIndex=index;
        updateTabView();
        mListener.onTabSelect(mTabs.valueAt(mIndex));
    }
    public int getCurrentTab(){
        return mIndex;
    }
    public void updateTabView(){
        for(int i=0;i<getChildCount();i++){
            View view=getChildAt(i);
            view.setSelected(i==mIndex);
        }
    }
    private LayoutParams createLayoutParams() {
        LayoutParams lp = null;
        if (isWeight) {
            lp = new LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        } else {
            lp = new LayoutParams(mTabTextWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        }
        return lp;
    }

    public int getTabTextWidth(){
        return mTabTextWidth;
    }

    public void clear() {
        removeAllViews();
        mTabs.clear();
    }

//    @Override
//    protected void onRestoreInstanceState(Parcelable state) {
//        if(!(state instanceof SavedState)){
//            return ;
//        }
//        SavedState savedState= (SavedState) state;
//        int index=savedState.index;
//       setCurrentTab(index);
//        super.onRestoreInstanceState(savedState.getSuperState());
//    }
//
//    @Override
//    protected Parcelable onSaveInstanceState() {
//        Parcelable p=super.onSaveInstanceState();
//        SavedState savedState=new SavedState(p);
//        savedState.index=mIndex;
//        return savedState;
//    }

    public static class SavedState  extends BaseSavedState{

        public int index;
        public SavedState(Parcelable superState) {
            super(superState);
        }

        public SavedState(Parcel source) {
            super(source);
            index=source.readInt();
        }


        @Override
        public int describeContents() {
            return super.describeContents();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(index);

        }
        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                if (view == v) {
                    if(getCurrentTab()!=i){
                        v.setSelected(true);
                        mIndex=i;
                        mListener.onTabSelect(mTabs.valueAt(i));
                    }
                } else {
                    view.setSelected(false);
                }
            }
        }
    }

}
