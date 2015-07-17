package com.tiny.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tiny.ui.R;

/**
 * Created by Administrator on 2015/7/1.
 */
public class CompatDialogWrapper {

    public static  AlertDialog  wrap(Context context,AlertDialog dialog,String title){
        Button button=dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if(button!=null){
            button.setTextColor(context.getResources().getColor(R.color.black_50));
        }
        if(title!=null){
            TextView mTitleView = (TextView) View.inflate(context, R.layout.text_title, null);
            mTitleView.setText(title);
            dialog.setCustomTitle(mTitleView);
        }
        return dialog;

    }
    public static AlertDialog wrap(Context context,AlertDialog dialog,int title){
        return  wrap(context,dialog,context.getString(title));
    }
}
