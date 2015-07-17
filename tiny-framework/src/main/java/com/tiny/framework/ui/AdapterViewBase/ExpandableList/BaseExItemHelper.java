package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.content.Context;
import android.view.View;
import android.widget.ExpandableListView;

import com.tiny.framework.ui.AdapterViewBase.BaseItemViewHelper;

/**
 * Created by tiny on 2015/4/11.
 */
public abstract class BaseExItemHelper<T> extends BaseItemViewHelper<T> implements IExpandableViewHolder {


    ExpandableListView mExListView;

    protected BaseExItemHelper(Context context) {
        super(context);
    }

    @Override
    public void setExpandListView(ExpandableListView mExListView) {
        this.mExListView = mExListView;
        setAdapterView(mExListView);
    }

    @Override
    public ExpandableListView getExpandListView() {
        return mExListView;
    }


}
