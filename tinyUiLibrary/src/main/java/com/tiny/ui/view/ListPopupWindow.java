package com.tiny.ui.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.tiny.ui.R;

/**
 * Created by Administrator on 2015/7/9.
 */
public class ListPopupWindow extends PopupWindow {

    ListView mListView;


    public ListPopupWindow(Context context,AdapterView.OnItemClickListener mListener) {
        super(context);
        setOutsideTouchable(true);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view=View.inflate(context, R.layout.layout_list_pop,null);
        mListView= (ListView) view.findViewById(R.id.list_pop);
        mListView.setOnItemClickListener(mListener);
        setContentView(view);
    }
    public void setOnItemClickListener(AdapterView.OnItemClickListener mListener){
        mListView.setOnItemClickListener(mListener);
    }

    public void setListAdapter(BaseAdapter mAdapter){
        mListView.setAdapter(mAdapter);
    }
    public ListView getListView(){
        return mListView;
    }
    public void setSelection(int position){
        mListView.setSelection(position);
    }

}
