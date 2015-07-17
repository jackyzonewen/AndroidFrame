package com.tiny.framework.mvp.imvp.vu;

import android.util.Log;
import android.view.View;

import com.tiny.framework.ui.common.OnErrorViewClickListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnEmptyListener;

/**
 * Created by tiny on 2015/4/15.
 */
public interface IBaseVu extends IVu {



     int DIALOG_SHOW = 1;

    int DIALOG_DISMISS = 2;

    int TOAST_SHOW = 3;

    int FINISH_DELAY = 4;



    void setClickCallBack(View.OnClickListener mListener);
     void showDialog();

     void dismissDialog();

     void dismissDialogDelay(long delay);

     void showToast(int resId);

     void showToast(String message);


     void postViewEnableRunnable(final View v);



     void d(String tag, String message);

     void d(String message);
     void e(String tag, String message);

     void e(String message);


}
