package com.tiny.framework.ui.AdapterViewBase.ListView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/3/26.
 */
public abstract  class CommonAdapter<T> extends BaseAdapter {


    Context mContext;

    List<T> mList;
    AdapterView mAdapterView;

    public CommonAdapter(Context mContext, List<T> mList) {
       this(mContext,mList,null);
    }
    /**
     *
     * @param mContext
     * @param mList T和CommonAdapter 声明的泛型T一致
     * @param mAdapterView ListView 或者GridView
     */
    public CommonAdapter(Context mContext, List<T> mList, AdapterView mAdapterView) {
        this.mContext = mContext;
        this.mList = mList;
        this.mAdapterView = mAdapterView;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IListViewHolder mHelper;
        int mViewType=getItemViewType(position);
        if (convertView == null) {
            mHelper = getViewItemInstanceByViewType(mContext,mViewType);
            mHelper.setAdapterView(mAdapterView);
            convertView = View.inflate(mContext, mHelper.getLayoutId(), null);
            mHelper.setItemView(convertView);
            bindViewHolder(mHelper, convertView, mViewType);
            convertView.setTag(mHelper);
        } else {
            mHelper = (ListViewItemHelper) convertView.getTag();
        }
        T mItem = (T) getItem(position);
        bindViewData(mHelper,convertView,position,mItem,mViewType);
        return convertView;
    }

    protected void bindViewHolder(IListViewHolder mHelper,View convertView,int mViewType){
        mHelper.bindView(convertView, mViewType);
    }
    protected void bindViewData(IListViewHolder mHelper,View convertView,int position,T mItem,int mViewType){
        mHelper.bindData(position, mItem, mViewType);
    }


    public abstract  IListViewHolder getViewItemInstanceByViewType(Context context,int mViewType);



    /**
     * 这个position是数据mList里的position
     * 如果有header要做偏移校正
     *
     * @param position
     */
    public void updateItemView(int position) {
        mUpdateView.obtainMessage(UPDATE_ITEM, position, -1).sendToTarget();

    }

    public void updateData(List<T> data){
        this.mList = data;
        notifyDataSetChanged();
    }
    public void setAdapterView(AdapterView view){
        this.mAdapterView=view;
    }

    public static final int UPDATE_ITEM = 0x1;


    Handler mUpdateView = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_ITEM:
                    int position = msg.arg1;
                    int first = mAdapterView.getFirstVisiblePosition();
                    T employee = (T)getItem(position);
                    int viewType =getItemViewType(position);
                    ListViewItemHelper itemView = (ListViewItemHelper) mAdapterView.getChildAt(position - first).getTag();
                    itemView.bindData(position, employee, viewType);
                    break;
            }

        }
    };


}
