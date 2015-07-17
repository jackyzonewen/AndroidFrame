package com.tiny.ui.image_selector.adapter;

import android.content.Context;
import android.widget.AdapterView;

import com.tiny.framework.ui.AdapterViewBase.ListView.IListViewHolder;
import com.tiny.framework.ui.AdapterViewBase.ListView.SingleCheckedCommonAdapter;
import com.tiny.ui.image_selector.bean.Folder;

import java.util.List;

/**
 * Created by Administrator on 2015/6/11.
 */
public class FolderListAdapter extends SingleCheckedCommonAdapter<Folder> {

    public FolderListAdapter(Context mContext, List<Folder> mList) {
        super(mContext, mList);
    }

    public FolderListAdapter(Context mContext, List<Folder> mList, AdapterView mAdapterView) {
        super(mContext, mList, mAdapterView);
    }

    @Override
    public IListViewHolder getViewItemInstanceByViewType(Context context, int mViewType) {
        return new FolderViewHolder(context);
    }
}
