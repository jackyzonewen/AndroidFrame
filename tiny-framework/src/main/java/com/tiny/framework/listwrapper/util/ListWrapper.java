package com.tiny.framework.listwrapper.util;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/5/22.
 */
 public interface ListWrapper<T>{


     void addElement(T e);


     void addElementAt(int position, T e);


     void addElementWithKey(int key, T e);


     void addElements(Collection<? extends T> collection);


     void removeElementAt(int position);

     void removeElementValue(T obj);

     void removeElementByKey(int key);


     void setElementAt(int position, T e);

     void setElementWithKey(int key, T e);


     void updateElementAt(int position, T value);

     void updateElementByKey(int key, T value);

     T getElementAt(int position);

     T getElementByKey(int key);

     int indexOfValue(T value);

     int indexOfKey(int key);

     void clear();


     boolean containsElement(T obj);

    boolean isEmpty();

    int size();

    int getLastIndex();
    
    
     List<T> getFilterList(Object filter);

     List<T> getFilterList(int from,Object filter);

     List<T> getFilterList(int from,int to,Object filter);
}

