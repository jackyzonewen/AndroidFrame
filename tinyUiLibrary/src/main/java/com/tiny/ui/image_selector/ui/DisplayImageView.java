package com.tiny.ui.image_selector.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/6/16.
 */
public class DisplayImageView extends ImageView {

    private boolean mChecked;

    private BitmapDrawable mDrawable;


    public DisplayImageView(Context context) {
        super(context);
    }

    public DisplayImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setChecked(boolean check){
        if(mChecked==check){
            return ;
        }
        mChecked=check;
        updateDrawableState();
    }
    public boolean isChecked(){
        return mChecked;
    }

    public void setBitmap(Bitmap bitmap){
        if(bitmap==null||bitmap.isRecycled()){
            if(mDrawable!=null){
                mDrawable.clearColorFilter();
                mDrawable=null;
            }
            return ;
        }
        mDrawable=new BitmapDrawable(getResources(),bitmap);
        updateDrawableState();

    }
    private void updateDrawableState(){
        if(mDrawable==null){
            return;
        }
        if(mChecked){
            mDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        }else{
            mDrawable.clearColorFilter();
        }
        setImageDrawable(mDrawable);
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if(mDrawable!=null){
            this.mDrawable.clearColorFilter();
            this.mDrawable=null;
        }

    }
}
