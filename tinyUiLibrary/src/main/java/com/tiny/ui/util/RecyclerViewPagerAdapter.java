package com.tiny.ui.util;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import com.tiny.ui.BuildConfig;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/7/3.
 */
public abstract  class RecyclerViewPagerAdapter extends PagerAdapter   {
    public static String TAG="RecyclerViewPagerAdapter";
    ViewRecycler mRecycler;
    protected  ViewPager mViewPager;

    public RecyclerViewPagerAdapter() {
        this(null);
    }
    public RecyclerViewPagerAdapter(ViewPager mViewPager) {
        mRecycler=new ViewRecycler();
        this.mViewPager=mViewPager;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int viewType=getViewType(position);
        View view=mRecycler.getRecyclerView(viewType);
        if(view==null){
            Log("onCreateView");
            view= onCreateView(container,viewType);
            mRecycler.attachView(viewType,view);
        }
        Log("onBindView");
        onBindView(position,view);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewGroup)container).removeView((View)object);
        mRecycler.unAttachView(getViewType(position), (View) object);
    }



    public abstract  int getViewType(int position);
    public abstract  View onCreateView(ViewGroup container,int position);
    public abstract void onBindView(int position,View view);

    public   final class ViewRecycler{
         SparseArray<ArrayList<View>> mAttachedViews=new SparseArray<ArrayList<View>>();
        SparseArray<ArrayList<View>> mCacheViews=new SparseArray<ArrayList<View>>();
        ViewRecycler (){

        }
        public void unAttachView(int viewType,View view){
            ArrayList<View> list=mAttachedViews.get(viewType);
            if(list.remove(view)){
                cacheViewInternal( viewType, view);
            }
        }
        public void attachView(int viewType,View view){
            ArrayList<View> list=mAttachedViews.get(viewType);
            if(list==null){
                list=new ArrayList<>();
                mAttachedViews.put(viewType, list);
            }
            list.add(view);
        }
        public void cacheView(int viewtType,View view){
            cacheViewInternal(viewtType, view);
        }


        private void cacheViewInternal(int viewType,View view){
            ArrayList<View> list=mCacheViews.get(viewType);
            if(list==null){
                list=new ArrayList<>();
                mCacheViews.put(viewType,list);
            }
            list.add(view);
        }
        private View getCacheView(int viewType){
            ArrayList<View> list=mCacheViews.get(viewType);
            if(list==null){
                return null;
            }
            if(list.isEmpty()){
                return null;
            }
            Log("recycle View success");
            return list.remove(list.size()-1);
        }

        public View getRecyclerView(int viewType){
            return getCacheView(viewType);
        }
        public void clear(){
            mCacheViews.clear();
        }
    }
    public void Log(String message){
        if(BuildConfig.DEBUG){
            Log.e(TAG,message);
        }

    }
}
