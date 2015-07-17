package com.tiny.framework.ui.AdapterViewBase.ListView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.AdapterView;
import android.widget.GridView;

import com.tiny.framework.ui.AdapterViewBase.BaseItemViewHelper;

/**
 * Created by Administrator on 2015/3/26.
 */
public abstract class ListViewItemHelper<T> extends BaseItemViewHelper<T> implements IListViewHolder<T> {


    public ListViewItemHelper(Context mContext) {
        super(mContext);
    }


    /**
     * 这个position是数据mList里的position
     * 如果有header要做偏移校正
     *
     * @param position
     */
    public void updateItemView(int position) {
        mUpdateView.obtainMessage(UPDATE_ITEM, position, -1).sendToTarget();

    }

    public static final int UPDATE_ITEM = 0x1;


    Handler mUpdateView = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_ITEM:
                    int position = msg.arg1;
                    AdapterView mAdapterView = getAdapterView();
                    int first = mAdapterView.getFirstVisiblePosition();
                    T employee = (T) mAdapterView.getAdapter().getItem(position);
                    int viewType = mAdapterView.getAdapter().getItemViewType(position);
                    ListViewItemHelper itemView = (ListViewItemHelper) mAdapterView.getChildAt(position - first).getTag();
                    itemView.bindData(position, employee, viewType);
                    break;
            }

        }
    };
}
