package com.tiny.framework.ui.recyclerview.interfaces;

/**
 * Created by Administrator on 2015/5/5.
 * if your RecyclerView has multi ViewTypes,
 * please make your model class implements this interface
 */
public interface IViewTypeResolve {

    int getViewType();
}
