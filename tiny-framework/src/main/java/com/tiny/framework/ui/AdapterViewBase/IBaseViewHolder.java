package com.tiny.framework.ui.AdapterViewBase;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by tiny on 2015/4/11.
 */
public interface IBaseViewHolder{


      View getItemView();

     int getLayoutId();


    void setItemView(View mItemView);

    void setAdapterView(AdapterView mAdapterView);

    AdapterView getAdapterView();
    

}
