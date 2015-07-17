package com.tiny.framework.ui.base;

import android.view.View;

/**
 * Created by Administrator on 2015/2/28.
 */
public interface IPresenter {


    int DIALOG_SHOW = 1;

    int DIALOG_DISMISS = 2;

    int TOAST_SHOW = 3;

    int FINISH_DELAY = 4;

    void showDialog();

    void dismissDialog();

    void dismissDialogDelay(long delay);

    void showToast(int resId);

    void showToast(String message);

    void L(String tag, String message);

    void postViewEnableRunnable(final View v);


}
