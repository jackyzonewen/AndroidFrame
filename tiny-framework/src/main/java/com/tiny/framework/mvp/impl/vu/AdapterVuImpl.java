package com.tiny.framework.mvp.impl.vu;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.tiny.framework.R;
import com.tiny.framework.mvp.imvp.vu.IAdapterVu;
import com.tiny.framework.ui.common.OnErrorViewClickListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnEmptyListener;

/**
 * Created by tiny on 2015/4/18.
 */
public class AdapterVuImpl<T extends AbstractVu.CallBack> extends BaseVuImpl<T> implements IAdapterVu {

    protected AdapterView mAdapterView;

    protected View mProgressView;


    View mEmptyView;

    OnEmptyListener mEmptyListener;

    View mErrorView;
    OnErrorViewClickListener mErrorViewListener;



    @Override
    protected void initView(Context context, int layoutId, ViewGroup container) {
        super.initView(context, layoutId, container);
        mAdapterView = (AdapterView) getContentView()
                .findViewById(com.tiny.framework.R.id.base_adapter_view);
        mProgressView = getContentView().findViewById(com.tiny.framework.R.id.view_progressing);
        mEmptyView=findViewById(R.id.empty_view);
        if(mEmptyView!=null){
            mEmptyView.setOnClickListener(this);
        }
        mErrorView=findViewById(R.id.error_view);
        if(mErrorView!=null){
            mErrorView.setOnClickListener(this);
        }
    }

    @Override
    public void setAdapter(BaseAdapter mAdapter) {
        if(mAdapterView!=null){
            mAdapterView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.empty_view) {
            if (mEmptyListener != null) {
                mEmptyListener.onEmptyViewCilick(v);
            }
        }else if(v.getId()==R.id.error_view){
            if(mErrorViewListener!=null){
                mErrorViewListener.onErrorViewClick(mErrorView);
            }
        }
    }



    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener mItemClick) {
        if(mAdapterView!=null){
            mAdapterView.setOnItemClickListener(mItemClick);
        }
    }

    @Override
    public AdapterView getAdapterView() {
        return mAdapterView;
    }

    @Override
    public void showProgressView() {
        hander.sendEmptyMessage(SHOW_PROGRESS);
    }

    @Override
    public void hideProgressView() {
        hander.sendEmptyMessage(DISMISS_PROGRESS);
    }

    @Override
    public void setEmptyListener(OnEmptyListener mEmptyListener) {
        this.mEmptyListener=mEmptyListener;
    }
    @Override
    public void makeEmptyVisibility() {
        if ( mEmptyView != null) {
            if (getAdapterView().getAdapter().getCount() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
                mAdapterView.setVisibility(View.GONE);
            } else {
                mEmptyView.setVisibility(View.GONE);
                mAdapterView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setErrorViewListener(OnErrorViewClickListener mErrorListener) {
        this.mErrorViewListener=mErrorListener;
    }

    @Override
    public void showErrorView() {
        if(mErrorView!=null){
            mAdapterView.setVisibility(View.GONE);
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideErrorView() {
        if(mErrorView!=null){
            mAdapterView.setVisibility(View.VISIBLE);
            mErrorView.setVisibility(View.GONE);
        }
    }

    private Handler hander = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS:
                    if (mAdapterView != null) {
                        mAdapterView.setVisibility(View.INVISIBLE);
                    }
                    if (mProgressView != null) {
                        mProgressView.setVisibility(View.VISIBLE);
                    }
                    break;
                case DISMISS_PROGRESS:
                    if (mAdapterView != null) {
                        mAdapterView.setVisibility(View.VISIBLE);
                    }
                    if (mProgressView != null) {
                        mProgressView.setVisibility(View.INVISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }

    };

}
