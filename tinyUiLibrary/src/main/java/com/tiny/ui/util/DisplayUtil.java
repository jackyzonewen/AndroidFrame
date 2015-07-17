package com.tiny.ui.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.widget.RadioButton;

import com.tiny.ui.R;

/**
 * Created by Administrator on 2015/6/11.
 */
public class DisplayUtil {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int getPrimaryColor(Context context){
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, tv, true);
        return context.getResources().getColor(tv.resourceId);
    }
    public static int getPrimaryDarkerColor(Context context){
        TypedValue tv = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, tv, true);
        return context.getResources().getColor(tv.resourceId);
    }
    public static GradientDrawable createShape(int strokeWidth,int strokeColor,float radius,int solid){
        GradientDrawable dr=new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,null);
        dr.setColor(solid);
        dr.setCornerRadius(radius);
        dr.setStroke(strokeWidth, strokeColor);
        return dr;
    }
    public static GradientDrawable createShape(int strokeWidth,int strokeColor,int solid){

        return createShape(strokeWidth,strokeColor,0,solid);
    }
    public static GradientDrawable createShape(int solid){
        return createShape(0,0,0,solid);
    }


}
