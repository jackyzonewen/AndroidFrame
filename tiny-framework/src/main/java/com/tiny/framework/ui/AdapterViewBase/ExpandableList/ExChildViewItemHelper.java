package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.content.Context;

/**
 * Created by tiny on 2015/4/11.
 */
public abstract  class ExChildViewItemHelper<T extends IChildItem> extends BaseExItemHelper<T> implements IChildViewHolder<T> {
    public ExChildViewItemHelper(Context context) {
        super(context);
    }
}
