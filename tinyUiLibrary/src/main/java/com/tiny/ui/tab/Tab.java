package com.tiny.ui.tab;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2015/6/11.
 */
public  class Tab  {


    protected  String text;

    protected  Object tag;

    protected int position;

    protected Drawable icon;
    protected  int iconRes;



    public Tab(int position,String text) {
        this.text = text;
        this.position=position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}
