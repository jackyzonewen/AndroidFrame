package com.tiny.framework.mvp.impl.presenter.fragment;

import android.widget.BaseAdapter;

import com.tiny.framework.R;
import com.tiny.framework.mvp.impl.vu.PtrListVuImpl;
import com.tiny.framework.mvp.imvp.presenter.IListViewPesenter;

/**
 * Created by tiny on 2015/4/14.
 */
public abstract class ListPresenterFragmentBase<V extends PtrListVuImpl> extends AdapterViewPresenterFragment<V > implements IListViewPesenter<V> {

    BaseAdapter mAdapter;
    protected int PAGE_COUNT = 20;

    /**
     * 重新该方法 返回 选择所要用的layout xml  id
     *
     * @return 所用的xml 文件id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_ptr_list;
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
}
