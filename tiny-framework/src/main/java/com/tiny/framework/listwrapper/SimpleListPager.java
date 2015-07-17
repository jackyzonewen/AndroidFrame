package com.tiny.framework.listwrapper;

import android.support.annotation.NonNull;

import com.tiny.framework.listwrapper.impl.ArrayListImpl;
import com.tiny.framework.listwrapper.impl.SparseArrayImpl;
import com.tiny.framework.listwrapper.util.Filterable;
import com.tiny.framework.listwrapper.util.ListWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Administrator on 2015/5/22.
 */
public class SimpleListPager<T> extends BasePagerHelper implements List<T>, ListWrapper<T> {


    public static <E> SimpleListPager<E> createListImplPager(Class<E> cls) {
        return new SimpleListPager<E>();
    }

    public static <E> SimpleListPager<E> createSparseImplPager(Class<E> cls) {
        return new SimpleListPager<E>(true);
    }

    ListWrapper<T> mListWrapperImpl;


    public SimpleListPager() {
        this(false);
    }
    public SimpleListPager(int mFirstPage) {
        this(false,mFirstPage);
    }
    public SimpleListPager(boolean withSparseImpl) {
        this(withSparseImpl,0);

    }
    public SimpleListPager(boolean withSparseImpl,int mFirstPage){
        super(PAGE_SIZE,mFirstPage);
        if (withSparseImpl) {
            mListWrapperImpl = new SparseArrayImpl<T>();
        }else{
            mListWrapperImpl=new ArrayListImpl<T>();
        }
    }


    public ListWrapper<T> getListWrapperImpl(){
        return mListWrapperImpl;
    }
    public ArrayList<T>  getArrayList(){
       if(mListWrapperImpl instanceof ArrayListImpl){
          return  (ArrayListImpl)mListWrapperImpl;
       } else{
           return null;
       }

    }



    @Override
    public void addElement(T e) {
        mListWrapperImpl.addElement(e);
    }

    @Override
    public void addElementAt(int position, T e) {
        mListWrapperImpl.addElementAt(position, e);
    }

    @Override
    public void addElementWithKey(int key, T e) {
        mListWrapperImpl.addElementWithKey(key, e);
    }

    @Override
    public void addElements(Collection<? extends T> collection) {
        mListWrapperImpl.addElements(collection);
    }

    @Override
    public void removeElementAt(int position) {
        mListWrapperImpl.removeElementAt(position);
    }

    @Override
    public void removeElementValue(T obj) {
        mListWrapperImpl.removeElementValue(obj);
    }

    @Override
    public void removeElementByKey(int key) {
        mListWrapperImpl.removeElementByKey(key);
    }

    @Override
    public void setElementAt(int position, T e) {
        mListWrapperImpl.setElementAt(position, e);
    }

    @Override
    public void setElementWithKey(int key, T e) {
        mListWrapperImpl.setElementWithKey(key, e);
    }

    @Override
    public void updateElementAt(int position, T value) {
        mListWrapperImpl.updateElementAt(position, value);
    }

    @Override
    public void updateElementByKey(int key, T value) {
        mListWrapperImpl.updateElementByKey(key, value);
    }

    @Override
    public T getElementAt(int position) {
        return mListWrapperImpl.getElementAt(position);
    }

    @Override
    public T getElementByKey(int key) {
        return mListWrapperImpl.getElementByKey(key);
    }

    @Override
    public boolean containsElement(T obj) {
        return contains(obj);
    }

    @Override
    public int indexOfValue(T value) {
        return mListWrapperImpl.indexOfValue(value);
    }

    @Override
    public int indexOfKey(int key) {
        return mListWrapperImpl.indexOfKey(key);
    }

    @Override
    public void add(int location, T object) {
        mListWrapperImpl.addElementAt(location, object);
    }

    @Override
    public boolean add(T object) {
        mListWrapperImpl.addElement(object);
        return true;
    }

    @Override
    public boolean addAll(int location, Collection<? extends T> collection) {
        mListWrapperImpl.addElements(collection);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        mListWrapperImpl.addElements(collection);
        return true;
    }

    @Override
    public int getLastIndex() {
        return mListWrapperImpl.getLastIndex();
    }

    @Override
    public List<T> getFilterList(Object filter) {
        return mListWrapperImpl.getFilterList(filter);
    }

    @Override
    public List<T> getFilterList(int from, Object filter) {
        return mListWrapperImpl.getFilterList(from,filter);
    }

    @Override
    public List<T> getFilterList(int from, int to, Object filter) {
        return mListWrapperImpl.getFilterList(from,to,filter);

    }

    @Override
    public void clear() {
        synchronized (mListWrapperImpl){
            mListWrapperImpl.clear();
            mCurrentIndex = mFirstPage;
            pageCount=0;
        }
    }

    @Override
    public boolean contains(Object object) {
        try {
            return mListWrapperImpl.containsElement((T) object);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override
    public T get(int location) {
        return getElementAt(location);
    }

    @Override
    public int indexOf(Object object) {
        try {
            return mListWrapperImpl.indexOfValue((T) object);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Deprecated
    @NonNull
    @Override
    public Iterator<T> iterator() {
        if(mListWrapperImpl instanceof ArrayListImpl ){
            ((ArrayListImpl)mListWrapperImpl).iterator();
        }
        return null;
    }

    @Override
    public int lastIndexOf(Object object) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int location) {
        return null;
    }

    @Override
    public T remove(int location) {
        removeElementAt(location);
        return null;
    }

    @Override
    public boolean remove(Object object) {
        removeElementValue((T) object);
        return false;
    }

    @Deprecated
    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Deprecated
    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public T set(int location, T object) {
        setElementAt(location,object);
        return object;
    }

    @Override
    public int size() {
        return mListWrapperImpl.size();
    }

    @Deprecated
    @NonNull
    @Override
    public List<T> subList(int start, int end) {
        return null;
    }
    @Deprecated
    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Deprecated
    @NonNull
    @Override
    public <T1> T1[] toArray(T1[] array) {
        return null;
    }


}
