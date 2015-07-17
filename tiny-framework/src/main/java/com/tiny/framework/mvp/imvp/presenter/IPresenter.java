package com.tiny.framework.mvp.imvp.presenter;

import android.os.Bundle;

import com.tiny.framework.mvp.imvp.vu.IVu;

/**
 * Created by tiny on 2015/4/14.
 */
public interface IPresenter<V extends IVu> {

    Class<V> getVuClass();

    V getVuInstance();


    void onVuCreated();

    void onPresenterCreated(Bundle savedInstanceState);


}
