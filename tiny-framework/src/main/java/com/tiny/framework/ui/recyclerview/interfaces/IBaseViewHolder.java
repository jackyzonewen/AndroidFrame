package com.tiny.framework.ui.recyclerview.interfaces;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015/5/5.
 */
public interface IBaseViewHolder{


    View getItemView();


    void setRecyclerView(RecyclerView mRecyclerView);

    RecyclerView getRecyclerView();



}
