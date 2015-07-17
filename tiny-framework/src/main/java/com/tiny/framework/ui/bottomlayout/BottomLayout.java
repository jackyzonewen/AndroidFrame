package com.tiny.framework.ui.bottomlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.tiny.framework.R;
import com.tiny.framework.view.ProgressWheel;

/**
 * Created by tiny on 2015/4/1.
 */
public class BottomLayout extends BaseBottomLayout implements IBottomLayout {


    public static BottomLayout createBottomLayout(Context mContext) {
        BottomLayout mBottomLayout = new BottomLayout(mContext);
        mBottomLayout.setLoadingText(mContext.getResources().getString(R.string._loading));
        mBottomLayout.setLoadedText(mContext.getResources().getString(R.string.loading_finish));
        mBottomLayout.setLoadOverText(" ");
        return mBottomLayout;
    }

    CharSequence mLoadingText, mLoadedText, mOverText;
    ProgressWheel mPb;
    TextView mTextView;


    public BottomLayout(Context context) {
        this(context, null);
    }

    public BottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.layout_bottom_loading_wheel, this);
        mPb = (ProgressWheel) findViewById(R.id.pb_loading_bottom);
        mTextView = (TextView) findViewById(R.id.txt_loading_bottom);

    }

    @Override
    public void setLoadingText(CharSequence mLoadingText) {
        this.mLoadingText = mLoadingText;
    }

    @Override
    public void setLoadedText(CharSequence mLoadedText) {
        this.mLoadedText = mLoadedText;
    }

    @Override
    public void setLoadOverText(CharSequence mOverText) {
        this.mOverText = mOverText;

    }

    @Override
    public void showLoadingBottomView() {
        mPb.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText(mLoadingText);


    }

    @Override
    public void showLoadedBottomView() {
        mPb.setVisibility(View.INVISIBLE);
        mTextView.setText(mLoadedText);
    }

    @Override
    public void showOverBottomView() {
        mPb.setVisibility(View.GONE);
        mTextView.setText(mOverText);
    }

    @Override
    public void setBottomTextColor(int color) {
        mTextView.setTextColor(color);
    }
}
