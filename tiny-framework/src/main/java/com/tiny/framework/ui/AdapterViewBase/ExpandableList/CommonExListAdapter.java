package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.content.Context;
import android.widget.ExpandableListView;

import java.util.List;

/**
 *
 *  *<p>是用List和List中的ChildList，两级list实现的</p>
 * Created by tiny on 2015/4/18.
 */
public class CommonExListAdapter<T extends IGroupItemWithChild<V>, V extends IChildItem> extends CommonExpandableListAdapter<T,V>{

    public CommonExListAdapter(Context context, List<T> mGroupList, Class<? extends IGroupViewHolder<T>> mGroupClass, Class<? extends IChildViewHolder<V>> mChildClass, ExpandableListView mExpandableList, Object mConstructContext) {
        super(context, mGroupList, null, mGroupClass, mChildClass, mExpandableList, mConstructContext);
    }


    @Override
    public V getChild(int groupPosition, int childPosition) {
        return getGroup(groupPosition).getChildList().get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getChildList().size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getChild(groupPosition,childPosition).getChildId();
    }
}
