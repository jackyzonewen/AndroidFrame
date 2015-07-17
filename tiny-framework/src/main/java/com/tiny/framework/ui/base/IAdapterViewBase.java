package com.tiny.framework.ui.base;

import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

/**
 * Created by tiny on 2015/4/11.
 */
public interface IAdapterViewBase {
    int PAGE_SIZE = 20;


    int SHOW_PROGRESS = 1;
    int DISMISS_PROGRESS = 0;



    void setAdapter(BaseAdapter adapter);


    AdapterView getAdapterView();

    BaseAdapter getAdapter();

    void notifyDataSetChanged();

    void showProgressView();

    void hideProgressView();

    boolean isLoading();

    boolean isLastPage();

}
