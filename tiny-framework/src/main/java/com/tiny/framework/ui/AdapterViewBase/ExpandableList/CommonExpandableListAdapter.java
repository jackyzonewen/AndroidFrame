package com.tiny.framework.ui.AdapterViewBase.ExpandableList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/**
 * T 和V 声明GroupList和ChildList的泛型
 * 同时GroupItemViewHelper必须声明 泛型T，ChildItemViewHelper必须声明V
 * 这里的Model是用List和HashMap实现的
 *<p>{@link CommonExListAdapter} 是用List和List中的ChildList，两级list实现的</p>
 * Created by tiny on 2015/4/11.
 */
public class CommonExpandableListAdapter<T extends IGroupItem, V extends IChildItem> extends BaseExpandableListAdapter {


    List<T> mGroupList;
    Context mContext;
    /**
     * 作为 Class<? extends IViewHolder> cls 构造参数context
     * 如果 该IViewHolder是在Activity内部类，则该参数为该Actiivty实例
     * 如果是Fragment，则是Fragment实例
     */
    Object mConstructContext;
    /**
     * 构造group和child Viewholder的class
     */
    Class mGroupClass, mChildClass;
    ExpandableListView mExpandableList;
    HashMap<Integer, List<V>> mChildData;

    public CommonExpandableListAdapter(Context context, List<T> mGroupList, HashMap<Integer, List<V>> mChildData, Class<? extends IGroupViewHolder<T>> mGroupClass, Class<? extends IChildViewHolder<V>> mChildClass,
                                       ExpandableListView mExpandableList, Object mConstructContext) {
        this.mContext = context;
        this.mGroupList = mGroupList;
        this.mGroupClass = mGroupClass;
        this.mChildClass = mChildClass;
        this.mExpandableList = mExpandableList;
        this.mConstructContext = mConstructContext;
        this.mChildData = mChildData;
        mChildData=new HashMap<Integer, List<V>>();
    }

    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }


    public List<T> getGroupList() {
        return mGroupList;
    }

    public HashMap<Integer, List<V>> getCHildData() {
        return mChildData;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int id = mGroupList.get(groupPosition).getGroupId();

        return mChildData.get(id).size();
    }

    @Override
    public T getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public V getChild(int groupPosition, int childPosition) {
        int id = mGroupList.get(groupPosition).getGroupId();

        return mChildData.get(id).get(childPosition);
    }


    @Override
    public long getGroupId(int groupPosition) {
        return mGroupList.get(groupPosition).getGroupId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        int id = mGroupList.get(groupPosition).getGroupId();

        return mChildData.get(id).get(childPosition).getChildId();
    }

    /**
     * ？未知
     *
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        IGroupViewHolder<T> mHelper;
        if (convertView == null) {
            mHelper = getGroupViewHolderInstance();
            mHelper.setExpandListView(mExpandableList);
            convertView = View.inflate(mContext, mHelper.getLayoutId(), null);
            mHelper.setItemView(convertView);
            bindGroupViewHolder(mHelper, isExpanded, convertView, parent);
            convertView.setTag(mHelper);
        } else {
            mHelper = (IGroupViewHolder<T>) convertView.getTag();
        }
        T mItem = getGroup(groupPosition);
        bindGroupViewData(mHelper, groupPosition, mItem, isExpanded);
        return convertView;
    }

    /**
     * 用以重载
     *
     * @param mHelper     helper
     * @param isExpanded  是否展开
     * @param convertView View
     * @param parent      parent
     */
    public void bindGroupViewHolder(IGroupViewHolder<T> mHelper, boolean isExpanded, View convertView, ViewGroup parent) {
        mHelper.bindView(isExpanded, convertView, parent);
    }

    /**
     * 用以重载
     *
     * @param mHelper    helper
     * @param position   position
     * @param mItem      数据 T
     * @param isExpanded 是否展开
     */
    public void bindGroupViewData(IGroupViewHolder<T> mHelper, int position, T mItem, boolean isExpanded) {
        mHelper.bindData(position, mItem, isExpanded);

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        IChildViewHolder<V> mHelper;
        if (convertView == null) {
            mHelper = getChildViewHolderInstance();
            mHelper.setExpandListView(mExpandableList);
            convertView = View.inflate(mContext, mHelper.getLayoutId(), null);
            mHelper.setItemView(convertView);
            bindChildViewHolder(mHelper, isLastChild, convertView, parent);
            convertView.setTag(mHelper);
        } else {
            mHelper = (IChildViewHolder<V>) convertView.getTag();
        }
        V mItem = getChild(groupPosition, childPosition);
        bindChildViewData(mHelper, groupPosition, childPosition, isLastChild, mItem);
        return convertView;
    }

    public void bindChildViewHolder(IChildViewHolder mHerlper, boolean isLastChild, View convertView, ViewGroup parent) {
        mHerlper.bindView(isLastChild, convertView, parent);
    }

    public void bindChildViewData(IChildViewHolder<V> mHelper, int groupPosition, int childPosition, boolean isLastChild, V mItem) {
        mHelper.bindData(groupPosition, childPosition, isLastChild, mItem);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public IGroupViewHolder<T> getGroupViewHolderInstance() {
        return (IGroupViewHolder<T>) createViewItemInstance(getViewHolderClass(true));
    }

    public IChildViewHolder<V> getChildViewHolderInstance() {
        return (IChildViewHolder<V>) createViewItemInstance(getViewHolderClass(false));
    }

    public Class getViewHolderClass(boolean isGroup) {
        return isGroup ? mGroupClass : mChildClass;
    }

    /**
     * 反射取得ViewItemHelper的实例
     * <p>这里在反射构造的时候，如果ViewItemHelper是static class 则不需要activity实例作为构造参数</p>
     * 构造方法必须定义为public
     * 如果是Activity或者fragment的内部类，则需要activity或者fragm的实例作为第一个参数，这里mConstructContext就是activity
     * <p>如果不能满足ViewItemHelper实现类的构造，重写该方法</p>
     *
     * @return
     */
    public IExpandableViewHolder createViewItemInstance(Class cls) {
        try {
            Constructor<IExpandableViewHolder>[] constructors = cls.getConstructors();
            if (constructors == null && constructors.length == 0) {
                return null;
            }
            for (Constructor<IExpandableViewHolder> mCons : constructors) {
                Class[] mParameterTypes = mCons.getParameterTypes();
                Object[] params = null;
                switch (mParameterTypes.length) {
                    case 1:
                        params = new Object[]{mContext};
                        return mCons.newInstance(params);
                    case 2:
                        params = new Object[]{mConstructContext, mContext};
                        return mCons.newInstance(params);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
