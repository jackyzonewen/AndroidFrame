package com.tiny.ui.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.ViewTreeObserver;

/**
 * Created by Administrator on 2015/6/15.
 */
public class GlobalLayoutCompat {


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(ViewTreeObserver mViewTreeObserver,ViewTreeObserver.OnGlobalLayoutListener mListener){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            mViewTreeObserver.removeOnGlobalLayoutListener(mListener);
        }else{
            mViewTreeObserver.removeGlobalOnLayoutListener(mListener);
        }

    }
}
