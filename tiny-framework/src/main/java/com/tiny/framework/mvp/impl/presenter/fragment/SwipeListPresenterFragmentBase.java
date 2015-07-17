package com.tiny.framework.mvp.impl.presenter.fragment;

import android.widget.BaseAdapter;
import android.widget.ListView;

import com.tiny.framework.R;
import com.tiny.framework.mvp.impl.presenter.activity.PresenterFragmentBase;
import com.tiny.framework.mvp.imvp.presenter.ISwipeListPresenter;
import com.tiny.framework.mvp.imvp.vu.ISwipeListVu;
import com.tiny.framework.ui.bottomlayout.BaseBottomLayout;

/**
 * Created by tiny on 2015/4/18.
 */
public abstract class SwipeListPresenterFragmentBase<V extends ISwipeListVu> extends PresenterFragmentBase<V> implements ISwipeListPresenter<V> {


    BaseAdapter mAdapter;


    boolean isLoading;


    /**
     * 重新该方法 返回 选择所要用的layout xml  id
     *
     * @return 所用的xml 文件id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_swipe_list;
    }


    protected boolean isLastPage() {
        return false;
    }

    protected void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    protected boolean isLoading() {
        return isLoading;
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
    public ListView getListView(){
        return getVuInstance().getListView();
    }
    public void showProgressView(){
        getVuInstance().showProgressView();
    }
    public void hideProgressView(){
        getVuInstance().hideProgressView();
    }


    public void refreshBottomView() {
        if (isLastPage()) {
            getVuInstance().setBottomState(BaseBottomLayout.State.Over);
        } else {
            getVuInstance().setBottomState(BaseBottomLayout.State.Loaded);
        }
        getVuInstance().refreshBottomView();
    }

}
