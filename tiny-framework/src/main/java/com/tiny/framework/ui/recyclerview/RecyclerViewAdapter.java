package com.tiny.framework.ui.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiny.framework.R;
import com.tiny.framework.ui.recyclerview.interfaces.IViewTypeResolve;
import com.tiny.framework.ui.recyclerview.interfaces.OnItemClickListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnItemLongClickListener;

import java.util.List;

/**
 * Created by Administrator on 2015/5/5.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements View.OnClickListener,View.OnLongClickListener {
    public static final int TYPE_HEADER = -1;


    Context mContext;
    List<T> mList;
    RecyclerView mRecyclerView;

    LayoutInflater mInflater;


    boolean hasHeadView;

    BaseViewHolder mHeaderViewHolder;
    OnItemClickListener mItemClickListener;
    OnItemLongClickListener mItemLongClickListener;


    /**
     * @param mContext
     * @param mList
     * @param mRecyclerView
     */
    public RecyclerViewAdapter(Context mContext, List<T> mList, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mList = mList;
        this.mRecyclerView = mRecyclerView;
        mInflater = LayoutInflater.from(mContext);
    }

    public RecyclerViewAdapter(Context mContext, List<T> mList) {
        this(mContext, mList, null);
    }

    public List<T> replaceList(List<T> newList){
        List<T> old=mList;
        mList=newList;
        return old;
    }
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int viewType=getItemViewType(position);
        if(viewType==TYPE_HEADER){
            return ;
        }
        bindViewData(holder, position, getItemViewType(position));
        View mClickView = holder.itemView.findViewById(R.id.root_clickable_view);
        if (mClickView != null) {
            mClickView.setTag(regulatePosition(position));
        } else
            holder.itemView.setTag(regulatePosition(position));
    }

    public void bindViewData(BaseViewHolder holder, int position, int mViewType) {
        T item = null;
        if (hasHeaderView() && isHeaderPosition(position)) {
            item = null;
        } else {
            item = getItem(position);
        }
        holder.bindData(position, item, mViewType);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (hasHeaderView() && viewType == TYPE_HEADER) {
            bindViewHolderItem(mHeaderViewHolder, viewType);
            return mHeaderViewHolder;
        }
        BaseViewHolder holder = createViewHolderByViewType(mContext, viewType);
        bindViewHolderItem(holder, viewType);
        return holder;
    }


    public abstract BaseViewHolder createViewHolderByViewType(Context context, int viewType);


    public void bindViewHolderItem(BaseViewHolder mHolder, int mViewType) {
        if (TYPE_HEADER != mViewType) {
            View mClickView = mHolder.itemView.findViewById(R.id.root_clickable_view);
            if(mClickView==null){
                mClickView=mHolder.itemView;
            }
            mClickView.setOnClickListener(this);
            if(mItemLongClickListener!=null){
                mClickView.setOnLongClickListener(this);
            }
        }
        mHolder.setRecyclerView(mRecyclerView);
        mHolder.bindView(mViewType);
        mHolder.itemView.setLayoutParams(createDefaultLayoutParams());
    }


    protected RecyclerView.LayoutParams createDefaultLayoutParams(){
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        return hasHeaderView() ? mList.size() + 1 : mList.size();
    }

    /**
     * @param position 如果有headView，该position应该是未校正过的position
     * @return
     */
    public T getItem(int position) {

        return getRelateItem(regulatePosition(position));
    }

    public T getRelateItem(int index) {
        return mList.get(index);
    }

    public int regulatePosition(int position) {
        if (hasHeaderView()) {
            if (position == 0) {
                return position;
            } else {
                return position - 1;
            }
        } else {
            return position;
        }
    }

    private boolean isHeaderPosition(int position) {
        return position == 0;
    }

    public boolean hasHeaderView() {
        return hasHeadView;
    }

    public void setHeaderView(BaseViewHolder mHeader) {
        this.hasHeadView = true;
        this.mHeaderViewHolder = mHeader;
    }


    @Override
    public int getItemViewType(int position) {
        if (hasHeaderView() && isHeaderPosition(position)) {
            return TYPE_HEADER;
        }
        if (getItem(position) instanceof IViewTypeResolve) {
            return ((IViewTypeResolve) (getItem(position))).getViewType();
        }
        return super.getItemViewType(position);
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        mItemClickListener = mListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener mListener){
        this.mItemLongClickListener=mListener;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onItemLongClick(v, (int) v.getTag());
            return true;
        }
        return false;
    }
}
