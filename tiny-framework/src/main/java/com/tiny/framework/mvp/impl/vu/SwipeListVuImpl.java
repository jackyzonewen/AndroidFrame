package com.tiny.framework.mvp.impl.vu;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.tiny.framework.R;
import com.tiny.framework.mvp.imvp.vu.ISwipeListVu;
import com.tiny.framework.ui.bottomlayout.BottomLayout;
import com.tiny.framework.ui.bottomlayout.BaseBottomLayout;
import com.tiny.framework.ui.recyclerview.interfaces.OnLastItemVisibleListener;

/**
 * Created by tiny on 2015/4/18.
 */
public class SwipeListVuImpl<T extends AbstractVu.CallBack> extends AdapterVuImpl<T> implements ISwipeListVu, AbsListView.OnScrollListener {


    ListView lv;
    SwipeRefreshLayout mSwipeLayout;

    OnLastItemVisibleListener mOnLastItemVisibleListener;
    AbsListView.OnScrollListener mInnerScrollListener;
    boolean mLastItemVisible;

    protected BaseBottomLayout mBottomLayout;

    @Override
    public void initView(Context context, int layoutId, ViewGroup container) {
        super.initView(context, layoutId, container);
        lv = (ListView) getContentView()
                .findViewById(com.tiny.framework.R.id.base_adapter_view);
        mSwipeLayout = (SwipeRefreshLayout) getContentView().findViewById(R.id.base_swipeLayout);
        if (mSwipeLayout != null) {
            mSwipeLayout.setColorSchemeColors(getContentView().getResources().getColor(android.R.color.holo_red_light), getContext().getResources().getColor(android.R.color.holo_purple), getContext().getResources().getColor(android.R.color.holo_green_light));
        }
        if (lv != null) {
            lv.setOnScrollListener(this);
        }
        if (isAddBottomLayout()) {
            mBottomLayout = createBottomLayout();
            lv.addFooterView(mBottomLayout);
        }
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeLayout;
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


    @Override
    public ListView getListView() {
        return lv;
    }

    @Override
    public void refreshBottomView() {
        if(mBottomLayout!=null){
            mBottomLayout.refreshBottomView();
        }
    }

    @Override
    public void setAdapter(BaseAdapter mAdapter) {
        lv.setAdapter(mAdapter);
    }

    @Override
    public void setBottomState(BaseBottomLayout.State mState) {
        if(mBottomLayout!=null){
            mBottomLayout.setState(mState);
        }
    }

    public void postRefresh() {
        getSwipeRefreshLayout().post(new Runnable() {
            @Override
            public void run() {
                getSwipeRefreshLayout().setRefreshing(true);
            }
        });
    }

    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener mRefreshListener) {
        if (mSwipeLayout != null) {
            mSwipeLayout.setOnRefreshListener(mRefreshListener);
        }
    }

    public void setOnScrollListener(AbsListView.OnScrollListener mListener) {
        mInnerScrollListener = mListener;
    }

    public void setOnLastItemVisibleListener(OnLastItemVisibleListener mListener) {
        mOnLastItemVisibleListener = mListener;
    }

    /**
     * 这里用来监控listView是否已滚到最后一个item
     *
     * @param view
     * @param state
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int state) {
        if (mInnerScrollListener != null) {
            mInnerScrollListener.onScrollStateChanged(view, state);
        }
        /**
         * Check that the scrolling has stopped, and that the last item is
         * visible.
         */
        if (state == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && null != mOnLastItemVisibleListener && mLastItemVisible) {
            d("onLastItemVisible");
            mOnLastItemVisibleListener.onLastItemVisible();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mInnerScrollListener != null) {
            mInnerScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        if (null != mOnLastItemVisibleListener) {
            mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case COMPLETE_REFRESH:
                    if(mSwipeLayout!=null){
                        mSwipeLayout.setRefreshing(false);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void completeRefresh() {
        completeRefreshDelay(0);
    }


    @Override
    public void completeRefreshDelay(long delay) {
        handler.sendEmptyMessageDelayed(COMPLETE_REFRESH, delay);
    }
}
