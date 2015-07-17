package com.tiny.framework.listwrapper.impl;

import android.util.SparseArray;


import com.tiny.framework.listwrapper.util.Filterable;
import com.tiny.framework.listwrapper.util.ListWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 */
public class SparseArrayImpl<T> extends SparseArray<T> implements ListWrapper<T> {


    Object mLock=new Object();
    private int mKeyFrom=Integer.MAX_VALUE<<2;


    public SparseArrayImpl() {

    }

    public SparseArrayImpl(int initialCapacity) {
        super(initialCapacity);
    }


    @Override
    public void addElement(T e) {
        int key=mKeyFrom++;
        put(key, e);

    }

    @Override
    public void addElementAt(int position, T e) {
        setValueAt(position,e);
    }

    @Override
    public void addElementWithKey(int key, T e) {
        put(key,e);
    }

    @Override
    public void addElements(Collection<? extends T> collection) {
        Iterator<? extends  T> iterator=collection.iterator();
        while(iterator.hasNext()){
            mKeyFrom++;
            this.addElementWithKey(mKeyFrom, iterator.next());
        }
    }

    @Override
    public void removeElementAt(int position) {
        removeAt(position);
    }

    @Deprecated
    @Override
    public void removeElementValue(T obj) {
    }

    @Override
    public void removeElementByKey(int key) {
            remove(key);
    }

    @Override
    public void setElementAt(int position, T e) {
        this.setValueAt(position,e);
    }

    @Override
    public void setElementWithKey(int key, T e) {
        put(key, e);
    }

    @Override
    public void updateElementAt(int position, T value) {
        setValueAt(position,value);
    }

    @Override
    public void updateElementByKey(int key, T value) {
        put(key, value);
    }

    @Override
    public T getElementAt(int position) {
        return valueAt(position);
    }

    @Override
    public T getElementByKey(int key) {
        return get(key);
    }

    @Override
    public int indexOfKey(int key) {
        return super.indexOfKey(key);
    }

    @Override
    public int indexOfValue(T value) {
        return super.indexOfValue(value);
    }

    @Override
    public boolean containsElement(T obj) {
        if(indexOfValue(obj)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size()==0?true:false;
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public int getLastIndex() {
        return size()-1;
    }
    @Override
    public List<T> getFilterList(Object filter) {
        return getFilterList(0,getLastIndex(),filter);
    }

    @Override
    public List<T> getFilterList(int from, Object filter) {
        return getFilterList(from,getLastIndex(),filter);
    }

    @Override
    public List<T> getFilterList(int from, int to, Object filter) {
        List<T> list=new ArrayList<T>();
        synchronized (mLock){
            for (int i = from; i <= to; i++) {
                T obj = getElementAt(i);
                if (obj instanceof Filterable) {
                    if (((Filterable) obj).filter(filter)) {
                        list.add(obj);
                    }
                }
            }
        }
        return list;
    }
}
