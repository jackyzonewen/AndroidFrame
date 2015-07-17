package com.tiny.framework.mvp.impl.presenter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiny.framework.mvp.imvp.presenter.IPresenter;
import com.tiny.framework.mvp.imvp.vu.IBaseVu;
import com.tiny.framework.util.StringUtil;

import java.util.List;

/**
 * Created by tiny on 2015/4/14.
 */
public abstract class PresenterFragmentBase<V extends IBaseVu> extends Fragment implements IPresenter<V> {


    static final  String SAVE_LAZYDATE="isDataInitializedInViewPager";
    V mVu;

    boolean isSavedInstanceState;


    /**
     * 重新该方法 返回 选择所要用的layout xml  id
     *
     * @return 所用的xml 文件id
     */
    public abstract int getContentViewId();


    /**
     * called in onViewCreate
     */
    public abstract void initData();

    public  void initData(Bundle saveState){

    };

    public abstract String getFragmentTag();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSavedInstanceState = savedInstanceState != null;
        if(savedInstanceState!=null){
            isDataInitializedInViewPager=savedInstanceState.getBoolean(SAVE_LAZYDATE,false);
        }
        onPresenterCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            mVu = getVuClass().newInstance();
            mVu.onCreate(getActivity(), getContentViewId(), container);
            if(this instanceof View.OnClickListener){
                getVuInstance().setClickCallBack((View.OnClickListener)this);
            }
            onVuCreated();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (mVu != null) {
            return mVu.getContentView();
        } else
            return super.onCreateView(inflater, container, savedInstanceState);
    }

    //该方法用于在同一布局里添加fragment
    public void addFragment(int layoutId, int showPosition, PresenterFragmentBase[] mFragments) {
        if (mFragments == null)
            return;
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.length; i++) {
            PresenterFragmentBase fragment = mFragments[i];
            ft.add(layoutId, fragment, fragment.getFragmentTag());
            if (i == showPosition) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
    }

    public void replaceFragment(int layoutId, Fragment mFragment, String tag, boolean executePendingTransactions) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(layoutId, mFragment, tag).commitAllowingStateLoss();
        if (executePendingTransactions) {
            getChildFragmentManager().executePendingTransactions();
        }

    }

    public void replaceFragment(int layoutId, Fragment mFragment, String tag) {
        replaceFragment(layoutId, mFragment, tag, false);
    }

    /**
     * show fragment  ，hide other fragments
     *
     * @param mShowFragment
     */
    public void showFragment(Fragment mShowFragment,Fragment mIgnoreFragment) {
        if (mShowFragment == null) {
            return;
        }
        FragmentManager mFm = getChildFragmentManager();
        List<Fragment> mFragments = mFm.getFragments();
        if (mFragments == null || mFragments.size() == 0) {
            return;
        }
        FragmentTransaction ft = mFm.beginTransaction();
        for (Fragment mFragment : mFragments) {
            if(mIgnoreFragment!=null&&mIgnoreFragment.equals(mFragment)){
                continue;
            }
            if (mFragment.equals(mShowFragment)) {
                ft.show(mFragment);
            } else {
                ft.hide(mFragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 当用fragment作为索引来show目标fragment 的时候
     * fragmentManager不能有相同tag的fragment
     *
     * @param tag
     */
    public void showFragment(String tag,String ignoreFragmentTag) {
        if (StringUtil.isEmpty(tag)) {
            return;
        }
        Fragment mIgnoreFragment=null;
        if(ignoreFragmentTag!=null){
            mIgnoreFragment=getChildFragmentManager().findFragmentByTag(ignoreFragmentTag);
        }
        showFragment(getChildFragmentManager().findFragmentByTag(tag), mIgnoreFragment);
    }


    @Override
    public void onPresenterCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        getVuInstance().onPostStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getVuInstance().onAfterResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getVuInstance().onBeforePause();
    }

    @Override
    public void onStop() {
        super.onStop();
        getVuInstance().onPostStop();
    }

    @Override
    public void onDestroy() {
        getVuInstance().onBeforeDestroy();
        super.onDestroy();
    }

    @Override
    public V getVuInstance() {
        return mVu;
    }

    /**
     * @return 如果onCreate的时候的savedInstanceState ！=null
     * return true
     */
    public boolean isSavedInstanceState() {
        return isSavedInstanceState;
    }

    /**
     * 以下代码处理Viewpager里的懒加载和在同一布局里防止fragment时的懒加载
     * 如果是在Viewpager里懒加载在LazyLoadInViewPager()处理
     * 如果是在多个Fragment里再Fetch里处理
     * 并且如果是第一个add进Fragment layout 或者ViewPager的第一个Fragment
     * 需要分别覆写  isFirstAddToFragmentManager() ,isFirstItemInViewPager() ，返回true
     *
     * @param view
     * @param savedInstanceState
     */


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mVu.onPostViewCreate();
        initData(savedInstanceState);
        initData();
        //处理在Viewpager里的lazyLoad
        isViewCreated = true;
        if (isFirstItemInViewPager()) {
            if (!isDataInitializedInViewPager) {
                LazyLoadInViewPager();
                isDataInitializedInViewPager = true;
            }
        }
        /**
         *在Viewpager里的lazyLoad结束
         *处理不是在viewPager里的时候的lazyload
         *当是第一个fragment的时候 手动调用下fetchData方法
         *因为第一个fragment如果addFragment的时候设为showFragment 不会回调 onHiddenChanged(false)
         */
        if (!isHidden() && isFirstAddToFragmentManager()) {
            onHiddenChanged(false);
        }
    }

    /**
     * 重置isDataInitializedInViewPager的值
     *
     * @param initialized
     */
    protected  void setDataInitializedInViewPager(boolean initialized){
        this.isDataInitializedInViewPager=initialized;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isViewCreated && !isDataInitializedInViewPager) {
            LazyLoadInViewPager();
            isDataInitializedInViewPager = true;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    //以下处理fragment在viewpage里的时候的lazyLoad
    // 只有当fragment第一次被显示出来的时候才去加载数据
    private boolean isViewCreated; //视图内容是否已准备好
    private boolean isDataInitializedInViewPager;

    /**
     * 控制fetchData的调用次数
     */
    private boolean isDataFetched;

    protected void setDataFetchedState(boolean isFetched) {
        isDataFetched = isFetched;
    }

    /**
     * 以下处理fragment以add的方法加载到fragmentManagaer里时的lazyLoad
     * 不在viewPager里
     * 重写该方法 lazyload数据
     * 不是在viewPager里的时候
     */
    public void fetchData() {


    }

    /**
     * fragment在viewPager里的时候 这个方法可以做数据初始化加载
     */
    protected void LazyLoadInViewPager() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVE_LAZYDATE,isDataInitializedInViewPager);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //addFragment的时候 第一个fragment为showFragment加载
        //不会回调onHiddenChanged（false） 所以在onViewCreated里手动调用下 见onViewCreated
        if (!hidden) {
            if (!isDataFetched) {
                fetchData();
                isDataFetched = true;
            }
        }
        super.onHiddenChanged(hidden);
    }

    /**
     * 如果是第一个add进fragmentManager的  重写该方法 并返回true
     *
     * @return
     */
    public boolean isFirstAddToFragmentManager() {
        return false;
    }

    /**
     * 这里如果是viewpager里的第一个页面 重写该方法 并返回 true
     * 会在onViewCreated里 手动调用LazyLoadInViewPager();
     *
     * @return
     */
    public boolean isFirstItemInViewPager() {
        return false;
    }

    /**
     * 在onViewCreated以后调用该方法
     * 否则返回返回null
     *
     * @return
     */

    public void showToast(String message) {
        getVuInstance().showToast(message);
    }

    public void showToast(int message) {
        getVuInstance().showToast(message);
    }

    public void showDialog() {
        getVuInstance().showDialog();
    }

    public void dismissDialog() {
        getVuInstance().dismissDialog();
    }

    public void dismissDialogDelay(long delay) {
        getVuInstance().dismissDialogDelay(delay);
    }

    public void postViewEnableRunnable(final View v) {
        getVuInstance().postViewEnableRunnable(v);
    }

    /**
     * 实现该接口的fragment，可以在需要的时候调用这个接口已刷新ui
     * 比如重新登录成功的时候,同一调用子fragment的该方法
     */
    public interface OnNewState {
        void onPostNewState(Object state);
    }
    public void d(String tag, String message) {
        getVuInstance().d(tag,message);
    }

    public void d(String message) {
        getVuInstance().d(message);
    }
    public void e(String tag, String message) {
        getVuInstance().e(tag,message);
    }

    public void e(String message) {
        getVuInstance().e(message);
    }
}
