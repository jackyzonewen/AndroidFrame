package com.tiny.framework.mvp.impl.vu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiny.framework.mvp.imvp.vu.IVu;

/**
 * 底层抽象
 * Created by tiny on 2015/4/14.
 */
public abstract class AbstractVu<T extends AbstractVu.CallBack> implements IVu {

    public static boolean DEBUG;

    private View mContentView;
    private Context mContext;

    protected  T mCallBack;

    @Override
    public View getContentView() {
        return mContentView;
    }


    @Override
    public void onCreate(Context context, int layoutId, ViewGroup container) {
        initView(context, layoutId, container);
        onViewCreate();
    }


    public View findViewById(int id){
        return mContentView.findViewById(id);
    }
    @Override
    public Context getContext() {
        return mContext;
    }


    protected void initView(Context context, int layoutId, ViewGroup container) {
        mContext = context;
        mContentView = View.inflate(context, layoutId, null);
    }

    public void setCallback(T callback){
        this.mCallBack=callback;
    }
    public T getCallBack(){
        return mCallBack;
    }
    protected void onViewCreate() {

    }

    @Override
    public void onPostViewCreate() {

    }

    @Override
    public void onPostStart() {

    }


    @Override
    public void onBeforePause() {

    }

    @Override
    public void onAfterResume() {

    }

    @Override
    public void onPostStop() {

    }

    @Override
    public void onBeforeDestroy() {

    }

    protected TextView findTextView(int id) {
        return (TextView) mContentView.findViewById(id);
    }

    protected ImageView findImageView(int id) {
        return (ImageView) mContentView.findViewById(id);
    }

    protected Button findButton(int id) {
        return (Button) mContentView.findViewById(id);
    }

    protected EditText findEdiText(int id) {
        return (EditText) mContentView.findViewById(id);
    }

    public interface CallBack{

    }

}
