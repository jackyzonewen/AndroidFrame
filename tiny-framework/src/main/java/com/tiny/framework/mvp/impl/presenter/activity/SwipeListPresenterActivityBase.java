package com.tiny.framework.mvp.impl.presenter.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.tiny.framework.R;
import com.tiny.framework.mvp.imvp.presenter.ISwipeListPresenter;
import com.tiny.framework.mvp.imvp.vu.ISwipeListVu;

/**
 * Created by tiny on 2015/4/18.
 */
public abstract class SwipeListPresenterActivityBase<V extends ISwipeListVu> extends PresentActivityBase<V> implements ISwipeListPresenter<V> {


    BaseAdapter mAdapter;




    /**
     * 重新该方法 返回 选择所要用的layout xml  id
     *
     * @return 所用的xml 文件id
     */
    @Override
    public int getContentViewId() {
        return R.layout.activity_list;
    }




    @Override
    public void setAdapter(BaseAdapter mAdapter) {
        this.mAdapter = mAdapter;
        V mVuImpl = getVuInstance();
        mVuImpl.setAdapter(mAdapter);
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }


    public void setOnItemClickListener(AdapterView.OnItemClickListener l){
        getVuInstance().setOnItemClickListener(l);
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener l){
        getVuInstance().getSwipeRefreshLayout().setOnRefreshListener(l);
    }


    public void completeRefresh() {
        getVuInstance().completeRefreshDelay(0);
    }


    public void completeRefreshDelay(long delay) {
        getVuInstance().completeRefreshDelay(delay);
    }

    public void postViewEnableRunnable(View v){
        getVuInstance().postViewEnableRunnable(v);
    }

    public ListView getListView(){
        return getVuInstance().getListView();
    }

}
