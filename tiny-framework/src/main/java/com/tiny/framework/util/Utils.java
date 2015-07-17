package com.tiny.framework.util;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by Administrator on 2015/6/8.
 */
public class Utils {


    /**
     *
     * @param context
     * @return size in MB
     */
    public static  int calculateMaxMemrySize(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return am.getMemoryClass();
    }
}
