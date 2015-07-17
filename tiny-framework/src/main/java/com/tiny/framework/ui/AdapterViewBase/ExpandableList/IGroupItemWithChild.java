package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import java.util.List;

/**
 * Created by tiny on 2015/4/18.
 */
public interface IGroupItemWithChild<C> extends IGroupItem {

    List<C> getChildList();

}
