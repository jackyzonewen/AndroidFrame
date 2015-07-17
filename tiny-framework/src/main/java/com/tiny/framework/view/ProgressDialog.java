package com.tiny.framework.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.tiny.framework.R;


/**
 * Created by Administrator on 2015/6/18.
 */
public class ProgressDialog extends AlertDialog {
    TextView mPromptView;

    protected ProgressDialog(Context context) {
        this(context, null);
    }

    protected ProgressDialog(Context context, String text) {
        super(context, 0);
        View view= View.inflate(context, R.layout.ui_dialog_progress,null);
        setView(view);
        mPromptView= (TextView) view.findViewById(R.id.txt_prompt_ui_dialog);
        if(text!=null){
            mPromptView.setText(text);
        }
    }

    public void setPromptText(String text){
        mPromptView.setText(text);
    }
    public void setPromptText(int text){
        this.setPromptText(getContext().getResources().getString(text));
    }
    public static ProgressDialog show(Context context){
        return show(context,null);
    }

    public static ProgressDialog show(Context context,String prompt){
        return new ProgressDialog(context,prompt);
    }

}
