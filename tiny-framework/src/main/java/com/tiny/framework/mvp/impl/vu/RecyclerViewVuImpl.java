package com.tiny.framework.mvp.impl.vu;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.tiny.framework.R;
import com.tiny.framework.mvp.imvp.vu.IRecycleViewVu;
import com.tiny.framework.ui.common.OnErrorViewClickListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnEmptyListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnLastItemVisibleListener;

/**
 * Created by Administrator on 2015/5/5.
 */
public class RecyclerViewVuImpl<T extends AbstractVu.CallBack> extends BaseVuImpl<T> implements IRecycleViewVu   {
    protected View mProgressView;
    protected  RecyclerView mRecyclerView;

    SwipeRefreshLayout mSwipeLayout;

    OnLastItemVisibleListener mOnLastItemVisibleListener;



    View mEmptyView;

    OnEmptyListener mEmptyListener;

    View mErrorView;

    OnErrorViewClickListener mErrorViewListener;


    @Override
    protected void initView(Context context, int layoutId, ViewGroup container) {
        super.initView(context, layoutId, container);
        mRecyclerView = (RecyclerView) getContentView()
                .findViewById(R.id.recycler_view);
        mRecyclerView.addOnScrollListener(mScrollListener);
        mProgressView = getContentView().findViewById(com.tiny.framework.R.id.view_progressing);
        mSwipeLayout=(SwipeRefreshLayout)getContentView().findViewById(R.id.base_swipeLayout);
        mEmptyView=findViewById(R.id.empty_view);
        if(mEmptyView!=null){
            mEmptyView.setOnClickListener(this);
        }
        mErrorView=findViewById(R.id.error_view);
        if(mErrorView!=null){
            mErrorView.setOnClickListener(this);
        }

        if (mSwipeLayout != null) {
            mSwipeLayout.setColorSchemeColors(getContentView().getResources().getColor(android.R.color.holo_red_light), getContext().getResources().getColor(android.R.color.holo_purple), getContext().getResources().getColor(android.R.color.holo_green_light));
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.empty_view) {
            if (mEmptyListener != null) {
                mEmptyListener.onEmptyViewCilick(v);
            }else if(v.getId()==R.id.error_view){
                if(mErrorViewListener!=null){
                    mErrorViewListener.onErrorViewClick(mErrorView);
                }
            }
        }
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter mAdapter) {
        mRecyclerView.setAdapter(mAdapter);
    }



    RecyclerView.OnScrollListener mScrollListener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            String message=null;
            switch (newState){
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
                case RecyclerView.SCROLL_STATE_IDLE:
                    message="RecyclerView.SCROLL_STATE_IDLE";
                    int count= recyclerView.getChildCount();
                    View view= recyclerView.getChildAt(count-1);
                    int i=recyclerView.getChildAdapterPosition(view);
//                    int k=recyclerView.getChildLayoutPosition(view);
//                    int m=recyclerView.getChildPosition(view);
//                     Log.e("onScrollStateChanged", "RecyclerView.SCROLL_STATE_IDLE i = " + i + " k ="+k+" m ="+m);
                    if(i==recyclerView.getAdapter().getItemCount()-1){
//                        Log.e("onScrollStateChanged","the last item was presented");
                        if(mOnLastItemVisibleListener!=null){
                            mOnLastItemVisibleListener.onLastItemVisible();
                        }
                    }
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    message="RecyclerView.SCROLL_STATE_SETTLING";
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

        }
    };

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeLayout;
    }
    public void setRefreshListener(SwipeRefreshLayout.OnRefreshListener l){
        this.mSwipeLayout.setOnRefreshListener(l);
    }


    @Override
    public void setOnLastItemVisibleListener(OnLastItemVisibleListener mLastItemListener) {
        this.mOnLastItemVisibleListener=mLastItemListener;
    }

    @Override
    public void setEmptyListener(OnEmptyListener mEmptyListener) {
        this.mEmptyListener=mEmptyListener;
    }
    @Override
    public void makeEmptyVisibility() {
        if ( mEmptyView != null) {
            if (getRecyclerView().getAdapter().getItemCount() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                mEmptyView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showErrorView() {
        if(mErrorView!=null){
            mRecyclerView.setVisibility(View.GONE);
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideErrorView() {
        if(mErrorView!=null){
            mRecyclerView.setVisibility(View.VISIBLE);
            mErrorView.setVisibility(View.GONE);
        }
    }


    @Override
    public void setErrorViewListener(OnErrorViewClickListener mErrorListener) {

    }

    @Override
    public void showProgressView() {
        hander.sendEmptyMessage(SHOW_PROGRESS);
    }

    @Override
    public void hideProgressView() {
        hander.sendEmptyMessage(DISMISS_PROGRESS);
    }

    public View getEmptyView(){
        return mEmptyView;
    }
    private Handler hander = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_PROGRESS:
                    if (mProgressView != null) {
                        mProgressView.setVisibility(View.INVISIBLE);
                    }
                    if (mProgressView != null) {
                        mProgressView.setVisibility(View.VISIBLE);
                    }
                    break;
                case DISMISS_PROGRESS:
                    if (mProgressView != null) {
                        mProgressView.setVisibility(View.VISIBLE);
                    }
                    if (mProgressView != null) {
                        mProgressView.setVisibility(View.INVISIBLE);
                    }
                    break;
                case COMPLETE_REFRESH:
                    if(mSwipeLayout!=null){
                        mSwipeLayout.setRefreshing(false);
                    }
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public void postRefresh() {
        if(mSwipeLayout!=null){
            mSwipeLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void completeRefresh() {
        completeRefreshDelay(0);
    }

    @Override
    public void completeRefreshDelay(long delay) {
        hander.sendEmptyMessageDelayed(COMPLETE_REFRESH, delay);
    }
}
