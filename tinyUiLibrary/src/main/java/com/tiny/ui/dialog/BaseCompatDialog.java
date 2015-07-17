package com.tiny.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.tiny.ui.R;


/**
 * Created by Administrator on 2015/6/6.
 */
public class BaseCompatDialog extends AlertDialog {


    protected TextView mTitleView;

    protected Context mContext;

    public BaseCompatDialog(Context context) {
        this(context, 0);
    }

    public BaseCompatDialog(Context context, int theme) {
        super(context, theme);
        this.mContext=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getContext().getResources().getColor(R.color.black_50));
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if(mTitleView==null){
            addCustomTitle();
        }
        mTitleView.setText(title);
    }
    void addCustomTitle(){
        mTitleView = (TextView) View.inflate(mContext, R.layout.text_title, null);
        setCustomTitle(mTitleView);
    }

    @Override
    public void setTitle(int titleId) {
        this.setTitle(getContext().getResources().getString(titleId));

    }

}
