package com.tiny.framework.ui.AdapterViewBase.ListView;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by tiny on 2015/4/9.
 */
public abstract  class SingleCheckedCommonAdapter<T> extends CommonAdapter<T> {

    int mCheckedPosition = -1;
    public SingleCheckedCommonAdapter(Context mContext, List<T> mList) {
        super(mContext, mList);
    }

    public SingleCheckedCommonAdapter(Context mContext, List<T> mList, AdapterView mListView) {
        super(mContext, mList, mListView);
    }

    protected void bindViewData(IListViewHolder mHelper, View convertView, int position, T mItem, int mViewType) {
        ((ISingleChecked) mHelper).setCheckedPosition(mCheckedPosition);
        mHelper.bindData(position, mItem, mViewType);
    }

    public int getCheckedPosition() {
        return mCheckedPosition;
    }

    public void setCheckedPosition(int mCheckedPosition) {
        this.mCheckedPosition = mCheckedPosition;
    }

    public interface ISingleChecked {


        void setCheckedPosition(int mCheckedPosition);

    }



}
