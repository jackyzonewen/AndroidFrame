package com.tiny.framework.mvp.imvp.vu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tiny on 2015/4/14.
 */
public interface IVu {


    /**
     * fragment 里再onCreateView 调用
     * activity里再onCreate
     * @param context
     * @param layoutId
     * @param container
     */
    void onCreate(Context context, int layoutId, ViewGroup container);

    View getContentView();


    Context getContext();



    /**
     * 在fragment中的onViewCreate中调用
     * 在Activity中的onCreate调用
     */
    void onPostViewCreate();

    void onPostStart();
    void onAfterResume();

    void onBeforePause();

    void onPostStop();

    void onBeforeDestroy();





}
