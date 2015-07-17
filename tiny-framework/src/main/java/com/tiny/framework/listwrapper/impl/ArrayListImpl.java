package com.tiny.framework.listwrapper.impl;

import com.tiny.framework.listwrapper.util.Filterable;
import com.tiny.framework.listwrapper.util.ListWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 */
public class ArrayListImpl<E> extends ArrayList<E> implements ListWrapper<E> {

    final Object mLock = new Object();

    @Override
    public void addElement(E e) {
        add(e);
    }

    @Override
    public void addElementAt(int position, E e) {
        add(position, e);
    }

    @Deprecated
    @Override
    public void addElementWithKey(int key, E e) {

    }

    @Override
    public void addElements(Collection<? extends E> collection) {
        addAll(collection);
    }

    @Override
    public void removeElementAt(int position) {
        remove(position);
    }

    @Override
    public void removeElementValue(E obj) {
        remove(obj);
    }

    @Deprecated
    @Override
    public void removeElementByKey(int key) {
    }

    @Override
    public void setElementAt(int position, E e) {
        set(position, e);
    }

    @Deprecated
    @Override
    public void setElementWithKey(int key, E e) {

    }


    @Override
    public void updateElementAt(int position, E value) {
        set(position, value);
    }

    @Deprecated
    @Override
    public void updateElementByKey(int key, E value) {

    }

    @Override
    public E getElementAt(int position) {
        return get(position);
    }

    @Deprecated
    @Override
    public E getElementByKey(int key) {
        return null;
    }

    @Override
    public int indexOfValue(E value) {
        return indexOf(value);
    }

    @Deprecated
    @Override
    public int indexOfKey(int key) {
        return 0;
    }

    @Override
    public boolean containsElement(E obj) {
        return contains(obj);
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
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
    public List<E> getFilterList(Object filter) {
        return getFilterList(0,getLastIndex(),filter);
    }

    @Override
    public List<E> getFilterList(int from, Object filter) {
        return getFilterList(from,getLastIndex(),filter);
    }

    @Override
    public List<E> getFilterList(int from, int to, Object filter) {
        List<E> list=new ArrayList<E>();
        synchronized (mLock){
            for (int i = from; i <= to; i++) {
                E obj = getElementAt(i);
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
