package com.tiny.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tiny.ui.R;

/**
 * Created by Administrator on 2015/7/6.
 */
public class ListCompatDialog extends BaseCompatDialog {
    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    public ListCompatDialog(Context context) {
        this(context, 0);
    }

    public ListCompatDialog(Context context, int theme) {
        super(context, theme);
        WindowManager.LayoutParams lp =getWindow().getAttributes();
        lp.width=WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height=context.getResources().getDisplayMetrics().heightPixels/2;
        getWindow().setAttributes(lp);
        mRecyclerView= (RecyclerView) View.inflate(mContext, R.layout.recyceler_view, null);
        setView(mRecyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setLayoutManager(RecyclerView.LayoutManager lm){
        mRecyclerView.setLayoutManager(lm);
    }
    public void setListAdapter(RecyclerView.Adapter adapter){
        mAdapter=adapter;
        mRecyclerView.setAdapter(mAdapter);
    }
    public interface  OnClickListener {
        void onClick(ListCompatDialog dialog,int which);
    }

}
