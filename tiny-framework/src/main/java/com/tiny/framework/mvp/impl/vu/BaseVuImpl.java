package com.tiny.framework.mvp.impl.vu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tiny.framework.mvp.imvp.vu.IBaseVu;
import com.tiny.framework.ui.common.OnErrorViewClickListener;
import com.tiny.framework.ui.recyclerview.interfaces.OnEmptyListener;
import com.tiny.framework.view.ProgressDialog;

/**
 * 实现原先FragmentBase和ActivityBase的一些通用api
 * Created by tiny on 2015/4/15.
 */
public class BaseVuImpl<T extends AbstractVu.CallBack> extends AbstractVu<T> implements IBaseVu ,View.OnClickListener {
    private ProgressDialog mProgressDialog;
    private Toast mToast;
    private View.OnClickListener mClickCallBack;


    @Override
    public void onCreate(Context context, int layoutId, ViewGroup container) {
        super.onCreate(context, layoutId, container);
        mProgressDialog = ProgressDialog.show(context);
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
                        mToast = Toast.makeText(getContext(), message,
                                Toast.LENGTH_LONG);
                        mToast.setText(message);
                    } else {
                        mToast.setText(message);
                    }
                    mToast.show();
                    break;
            }

        }

    };

    @Override
    public final void setClickCallBack(View.OnClickListener mListener) {
        this.mClickCallBack=mListener;
    }

    @Override
    public void onClick(View v) {
        if(mClickCallBack!=null){
            mClickCallBack.onClick(v);
        }
    }

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
        msg.obj = getContext().getResources().getString(resId);
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

    @Override
    public void d(String tag, String message) {
        if(DEBUG){
            Log.d(tag, message);
        }
    }

    @Override
    public void d(String message) {
        if(DEBUG){
            d(getClass().getSimpleName(), message);
        }
    }


    @Override
    public void e(String tag, String message) {
        if(DEBUG){
            Log.e(tag, message);
        }
    }

    @Override
    public void e(String message) {
        e(getClass().getSimpleName(), message);
    }


    public Resources getResources(){
        return getContext().getResources();
    }
    public String getString (int id){
        return getContext().getResources().getString(id);
    }

    public void setDialogCancelable(boolean cancelable){
        mProgressDialog.setCancelable(cancelable);
    }

}
