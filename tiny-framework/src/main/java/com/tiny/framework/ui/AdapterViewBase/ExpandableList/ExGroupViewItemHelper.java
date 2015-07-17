package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.content.Context;


/**
 * Created by tiny on 2015/4/11.
 */
public abstract  class ExGroupViewItemHelper<T extends IGroupItem> extends BaseExItemHelper<T> implements IGroupViewHolder<T> {



    public ExGroupViewItemHelper(Context context) {
        super(context);
    }


}
