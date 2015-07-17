package com.tiny.framework.mvp.impl.vu;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tiny.framework.mvp.imvp.vu.IPtrListVu;
import com.tiny.framework.ui.bottomlayout.BottomLayout;
import com.tiny.framework.ui.bottomlayout.BaseBottomLayout;

/**
 * Created by tiny on 2015/4/14.
 */
public class PtrListVuImpl extends AdapterVuImpl implements IPtrListVu {


    protected ListView lv;
    protected PullToRefreshListView mPtlListView;

    protected BaseBottomLayout mBottomLayout;


    @Override
    public void initView(Context context, int layoutId, ViewGroup container) {
        super.initView(context, layoutId, container);
        mPtlListView = (PullToRefreshListView) getContentView()
                .findViewById(com.tiny.framework.R.id.base_ptl_list);
        lv =  mPtlListView.getRefreshableView();
        if (isAddBottomLayout()) {
            mBottomLayout = createBottomLayout();
            lv.addFooterView(mBottomLayout,null,false);
        }
    }


    @Override
    protected void onViewCreate() {
        super.onViewCreate();

    }

    @Override
    public ListView getListView() {
        return lv;
    }

    @Override
    public PullToRefreshListView getPtlListView() {
        return mPtlListView;
    }

    @Override
    public void setPullRefreshDisable(boolean enable) {
        mPtlListView.setPullToRefreshEnabled(enable);
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case COMPLETE_REFRESH:
                    if (mPtlListView != null) {
                        mPtlListView.onRefreshComplete();
                    }
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    public void setAdapter(BaseAdapter mAdapter) {
        mPtlListView.setAdapter(mAdapter);
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener mItemClick) {
        if(getListView() != null){
            getListView().setOnItemClickListener(mItemClick);
        }
    }

    public void postRefreshing() {
        getPtlListView().post(new Runnable() {
            @Override
            public void run() {
                getPtlListView().setRefreshing();
            }
        });
    }

    @Override
    public BaseBottomLayout getBottomLayout() {
        return mBottomLayout;
    }

    @Override
    public boolean isAddBottomLayout() {
        return false;
    }

    @Override
    public BaseBottomLayout createBottomLayout() {
        return BottomLayout.createBottomLayout(getContext());
    }

    public final void completeRefresh() {
        handler.sendEmptyMessage(COMPLETE_REFRESH);
    }


    public final void completeRefreshDelay(int delayTime) {
        handler.sendEmptyMessageDelayed(COMPLETE_REFRESH, delayTime);
    }


    public void refreshBottomView() {
        if (mBottomLayout == null) {
            return;
        }
        mBottomLayout.refreshBottomView();
    }

    public void setBottomState(BaseBottomLayout.State mState) {
        if (mBottomLayout != null) {
            mBottomLayout.setState(mState);
        }
    }

    public void refreshBottomView(BaseBottomLayout.State mState) {
        if (mBottomLayout == null) {
            return;
        }
        setBottomState(mState);
        mBottomLayout.refreshBottomView();
    }


}
