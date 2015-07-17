package com.tiny.framework.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tiny.framework.R;
import com.tiny.framework.ui.base.IPresenter;
import com.tiny.framework.ui.base.IUniqueTask;
import com.tiny.framework.util.StringUtil;
import com.tiny.framework.view.ProgressDialog;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ActivityBase extends AppCompatActivity implements IPresenter, IUniqueTask {

    public static boolean DEBUG;

    protected Toolbar mToolbar;

    private ProgressDialog mProgressDialog;
    private Toast mToast;
    InputMethodManager im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeViewInflated();
        im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        setContentView(getContentRes());
        mProgressDialog = ProgressDialog.show(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if ( mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getContentRes();

    protected abstract void initView();

    protected abstract void initData();

    protected void setActionBarIcon(int iconRes) {
        mToolbar.setNavigationIcon(iconRes);
    }

    protected Toolbar getToolBar() {
        return mToolbar;
    }

    protected void beforeViewInflated(){

    }
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DIALOG_SHOW:
                    if (mProgressDialog != null && !mProgressDialog.isShowing())
                        mProgressDialog.show();
                    break;
                case DIALOG_DISMISS:
                    if (mProgressDialog != null && mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    break;
                case TOAST_SHOW:
                    String message = (String) msg.obj;
                    if (mToast == null) {
                        mToast = Toast.makeText(ActivityBase.this, message,
                                Toast.LENGTH_LONG);
                        mToast.setText(message);
                    } else {
                        mToast.setText(message);
                    }
                    mToast.show();
                    break;
                case FINISH_DELAY:
                    finish();
                    break;
            }

        }

    };

    @Override
    public final void showDialog() {
        mHandler.sendEmptyMessage(DIALOG_SHOW);
    }

    @Override
    public final void dismissDialog() {
        mHandler.sendEmptyMessage(DIALOG_DISMISS);
    }

    @Override
    public final void dismissDialogDelay(long delay) {
        mHandler.sendEmptyMessageDelayed(DIALOG_DISMISS, delay);
    }

    public void finishDelay(long delay) {
        mHandler.sendEmptyMessageDelayed(FINISH_DELAY, delay);

    }

    public final void setDialogCancleable(boolean flag) {
        mProgressDialog.setCancelable(flag);
    }

    @Override
    public final void showToast(String text) {
        Message msg = new Message();
        msg.what = TOAST_SHOW;
        msg.obj = text;
        mHandler.sendMessage(msg);
    }

    @Override
    public final void showToast(int resId) {
        Message msg = new Message();
        msg.what = TOAST_SHOW;
        msg.obj = getString(resId);
        mHandler.sendMessage(msg);
    }

    @Override
    public void postViewEnableRunnable(final View v) {
        v.setEnabled(false);
        v.postDelayed(new Runnable() {

            @Override
            public void run() {
                v.setEnabled(true);
            }
        }, 1000);
    }


    public String getTag() {
        return this.getClass().getSimpleName();
    }

    // 屏幕物理菜单键
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFragment(int layoutId, int showPosition, Fragment[] mFragments, String[] tags) {
        if (mFragments == null)
            return;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.length; i++) {
            Fragment fragment = mFragments[i];
            ft.add(layoutId, fragment, tags[i]);
            if (i == showPosition) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }


    /**
     * 该方法用于在不同布局里添加fragment
     *
     * @param ids        不同布局的id
     * @param mFragments 要添加的fragment
     * @param tags       fragment 对应的tag 可为null
     */
    public void addMultiFragment(int[] ids, Fragment[] mFragments, String[] tags) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < ids.length; i++) {
            Fragment mFragment = mFragments[i];
            String tag = null;
            if (tags != null) {
                tag = tags[i];
            }
            ft.add(ids[i], mFragment, tag);
        }
        //ft.commit();
        ft.commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }


    /**
     * show fragment  ，hide other fragments
     *
     * @param mShowFragment
     */
    public void showFragment(Fragment mShowFragment) {
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
    public void showFragment(String tag) {
        if (StringUtil.isEmpty(tag)) {
            return;
        }

        showFragment(getSupportFragmentManager().findFragmentByTag(tag));

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
        im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        L("hideSoftKeyBoard");
        return true;
    }

    // 拦截点击事件 ，如果点击在EditText外 则隐藏输入法
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


    public void initWindowSize(double mWidthSize, double mHeightSize, int Gravity) {
        getWindow().setAttributes(initWindow(mWidthSize, mHeightSize, Gravity));
    }

    public void initWindowSize(double mWidthSize, double mHeightSize) {
        getWindow().setAttributes(initWindow(mWidthSize, mHeightSize, Gravity.CENTER));
    }

    public WindowManager.LayoutParams initWindow(double mWidthSize, double mHeightSize) {
        return initWindow(mWidthSize, mHeightSize, Gravity.CENTER);
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

    /**
     * 这里处理IUniqueTask
     */
    public static int fragmentId = 1000;
    int uniqueId;

    public synchronized static int getNextUniqueId() {
        if (fragmentId > 1100) {
            fragmentId = 1000;
        }

        fragmentId++;
        return fragmentId;
    }


    @Override
    public String getUniqueTaskId(Integer transactionCode) {

        return "" + uniqueId + "" + df.format(transactionCode);
    }

    String pattern = "0000";
    java.text.DecimalFormat df = new java.text.DecimalFormat(pattern);

    public void L(String message) {
        L(getTag(), message);
    }

    @Override
    public void L(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        L("onTrimMemory:level" + level);
    }
}
