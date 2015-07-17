package com.tiny.framework.mvp.impl.presenter.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.tiny.framework.mvp.imvp.presenter.IRecyclerViewPresenter;
import com.tiny.framework.mvp.imvp.vu.IRecycleViewVu;
import com.tiny.framework.ui.recyclerview.interfaces.OnItemClickListener;

/**
 * Created by Administrator on 2015/5/5.
 */
public  abstract  class RecyclerViewPresentActivity<V extends IRecycleViewVu> extends PresentActivityBase<V> implements IRecyclerViewPresenter<V>{
    RecyclerView.Adapter mAdapter;


    @Override
    public void setAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter=mAdapter;
        getVuInstance().setAdapter(mAdapter);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void notifyDataSetChanged() {
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public RecyclerView getRecyclerView(){
        return getVuInstance().getRecyclerView();
    }
    public void  setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener l){
        getVuInstance().getSwipeRefreshLayout().setOnRefreshListener(l);
    }

    public void completeRefresh(){
        getVuInstance().completeRefresh();
    }
    public void completeRefreshDelay(long delay){
        getVuInstance().completeRefreshDelay(delay);
    }
}
