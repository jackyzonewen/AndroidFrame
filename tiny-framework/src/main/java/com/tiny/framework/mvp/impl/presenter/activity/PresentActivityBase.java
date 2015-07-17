package com.tiny.framework.mvp.impl.presenter.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tiny.framework.R;
import com.tiny.framework.mvp.imvp.presenter.IPresenter;
import com.tiny.framework.mvp.imvp.vu.IBaseVu;
import com.tiny.framework.util.StringUtil;

import java.util.List;

/**
 * Created by tiny on 2015/4/15.
 */
public abstract class PresentActivityBase<V extends IBaseVu> extends ActionBarActivity implements IPresenter<V> {

    V mVu;
    protected Toolbar mToolbar;
    DrawerLayout mDrawer;

    boolean isSavedInstanceState;
    ActionBarDrawerToggle mToggle;

    InputMethodManager im;

    /**
     * 重新该方法 返回 选择所要用的layout xml  id
     *
     * @return 所用的xml 文件id
     */
    protected abstract int getContentViewId();


    /**
     * 在mVu被实例化后调用该方法
     */
    protected abstract void initData();

    protected  void initData(Bundle savedInstanceState){

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSavedInstanceState = savedInstanceState != null;
        onPresenterCreated(savedInstanceState);
        beforeSetContentView();
        createVuInstance();
        setContentView(mVu.getContentView());
        createToolbar();
        onVuCreated();
        mVu.onPostViewCreate();
        initData();
        initData(savedInstanceState);
        setTransparentStatusBar();

    }

    protected void beforeSetContentView(){

    }
    protected void createVuInstance() {

        try {
            mVu = getVuClass().newInstance();
            mVu.onCreate(this, getContentViewId(), null);
            if(this instanceof View.OnClickListener){
                getVuInstance().setClickCallBack((View.OnClickListener)this);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public void setTransparentStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
    }
    protected void createToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if ( mToolbar != null) {
            if(hasNavigationDrawer()){
                createDrawLayout();
            }
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    protected boolean hasNavigationDrawer(){
        return false;
    }
    protected void createDrawLayout(){
        mDrawer=(DrawerLayout)findViewById(R.id.draw_layout);
        mDrawer.setDrawerShadow(R.drawable.shadow_left,Gravity.START);
        mToggle = createDrawerToggle(mDrawer);
        mDrawer.setDrawerListener(mToggle);
        mDrawer.post(new Runnable() {
            @Override
            public void run() {
                mToggle.syncState();
            }
        });
    }
    protected ActionBarDrawerToggle createDrawerToggle(DrawerLayout drawer){
        return new ActionBarDrawerToggle(this, drawer, getToolBar(), R.string.drawer_open, R.string.drawer_close);
    }
    public ActionBarDrawerToggle getDrawerToggle(){
        return mToggle;
    }
    public  boolean isDrawerOpened(int gravity){
        return    mDrawer.isDrawerOpen(gravity);
    }
    public void closeDrawer(int gravity){
        mDrawer.closeDrawer(gravity);
    }
    public void openDrawer(int gravity){
        mDrawer.openDrawer(gravity);
    }
    public boolean toggleDrawer(int gravity) {
        if (mDrawer != null) {
            if(isDrawerOpened(gravity)){
                closeDrawer(gravity);
            }else{
                openDrawer(gravity);
            }
            return true;
        }
        return false;
    }
    @Override
    protected void onStart() {
        super.onStart();
        getVuInstance().onPostStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getVuInstance().onAfterResume();
    }

    @Override
    protected void onPause() {
        getVuInstance().onBeforePause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getVuInstance().onPostStop();
    }

    @Override
    protected void onDestroy() {
        getVuInstance().onBeforeDestroy();
        super.onDestroy();

    }

    /**
     * @return 如果onCreate的时候的savedInstanceState ！=null
     * return true
     */
    public boolean isSavedInstanceState() {
        return isSavedInstanceState;
    }


    /**
     * 在onViewCreated以后调用该方法
     * 否则返回返回null
     *
     * @return
     */
    @Override
    public V getVuInstance() {
        return mVu;
    }

    @Override
    public void onPresenterCreated(Bundle savedInstanceState) {

    }

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }
    protected void setToolBarTitle(String title){
        mToolbar.setTitle(title);
    }
    protected void setToolBarTitle(int title){
        mToolbar.setTitle(title);
    }
    protected Toolbar getToolBar() {
        return mToolbar;
    }

    @Override
    public abstract void onVuCreated();


    //该方法用于在同一布局里添加fragment
    public void addFragment(int layoutId, int showPosition, PresenterFragmentBase[] mFragments) {
        if (mFragments == null)
            return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.length; i++) {
            PresenterFragmentBase fragment = mFragments[i];
            if(getSupportFragmentManager().findFragmentByTag(fragment.getFragmentTag())!=null){
                ft.remove(fragment);
            }
            ft.add(layoutId, fragment, fragment.getFragmentTag());
            if (i == showPosition) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    public void replaceFragment(int layoutId, Fragment mFragment, String tag, boolean executePendingTransactions) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(layoutId, mFragment, tag).commitAllowingStateLoss();
        if (executePendingTransactions) {
            getSupportFragmentManager().executePendingTransactions();
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
        FragmentManager mFm = getSupportFragmentManager();
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
            mIgnoreFragment=getSupportFragmentManager().findFragmentByTag(ignoreFragmentTag);
        }
        showFragment(getSupportFragmentManager().findFragmentByTag(tag), mIgnoreFragment);
    }


    /**
     * 用于定制dialog Theme的activity的宽高
     *
     * @param mWidthSize
     * @param mHeightSize
     * @param gravity
     */
    public WindowManager.LayoutParams initWindow(double mWidthSize, double mHeightSize, int gravity) {
        WindowManager m = getWindowManager();

        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高

        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.gravity = gravity;
        if (mWidthSize > 0) {
            p.width = (int) (d.getWidth() * mWidthSize); // 宽度设置为屏幕
        } else {
            p.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        if (mHeightSize > 0) {
            p.height = (int) (d.getHeight() * mHeightSize); // 高度设置为屏幕
        } else {
            p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
        p.alpha = 1.0f; // 设置本身透明度

        p.dimAmount = 0.3f; // 设置黑暗度
        return p;
    }

    public void initWindowSize(double mWidthSize, double mHeightSize, int Gravity) {
        getWindow().setAttributes(initWindow(mWidthSize, mHeightSize, Gravity));
    }

    public void initWindowSize(double mWidthSize, double mHeightSize) {
        getWindow().setAttributes(initWindow(mWidthSize, mHeightSize, Gravity.CENTER));
    }

    public WindowManager.LayoutParams initWindow(double mWidthSize, double mHeightSize) {
        return initWindow(mWidthSize, mHeightSize, Gravity.CENTER);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null
                && ev.getAction() == MotionEvent.ACTION_DOWN && willHideSoftKeyBoard()) {
            if (isShouldHideInputSoft(ev, getCurrentFocus())) {
                hideSoftKeyBoard();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // 判断隐藏软键盘
    public boolean isShouldHideInputSoft(MotionEvent ev, View v) {
        if (v != null && v instanceof EditText) {
            int[] location = {0, 0};
            v.getLocationInWindow(location);
            int left = location[0];
            int top = location[1];
            int right = left + v.getWidth();
            int bottom = top + v.getHeight();
            return !(ev.getX() > left && ev.getX() < right && ev.getX() > top
                    && ev.getX() < bottom);
        } else {
            return false;
        }
    }
    /**
     * 只有返回true 才会在点击输入法以外的区域时，会隐藏输入法
     *
     * @return
     */
    public boolean willHideSoftKeyBoard() {
        return false;
    }

    // 隐藏输入法
    public boolean hideSoftKeyBoard() {
        if (getCurrentFocus() == null) {
            return false;
        }
        if(im==null){
            im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        return true;
    }
    public void d(String message) {
        getVuInstance().d(getClass().getSimpleName(), message);
    }

    public void d(String tag, String message) {
        getVuInstance().d(tag, message);
    }

    public void e(String message) {
        getVuInstance().e(getClass().getSimpleName(), message);
    }

    public void e(String tag, String message) {
        getVuInstance().e(tag, message);
    }

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

}
