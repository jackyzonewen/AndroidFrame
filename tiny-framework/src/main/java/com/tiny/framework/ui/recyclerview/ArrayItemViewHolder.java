package com.tiny.framework.ui.recyclerview;

import android.view.View;
import android.widget.TextView;

import com.tiny.framework.R;

/**
 * Created by Administrator on 2015/7/1.
 */
public class ArrayItemViewHolder<T> extends BaseViewHolder<T> {


    int mTextId;
    TextView mTextView;

    public ArrayItemViewHolder(View itemView,int textId) {
        super(itemView);
        mTextId=textId;
    }

    @Override
    public void bindView(int mViewType) {
        if(mTextId==0){
              mTextView= (TextView) itemView;
        }else{
            mTextView= (TextView) itemView.findViewById(mTextId);
        }
    }

    @Override
    public void bindData(int position, T mItem, int mViewType) {
        mTextView.setText(mItem.toString());
    }
}
