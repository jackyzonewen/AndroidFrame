package com.tiny.ui.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tiny.framework.ui.recyclerview.ArrayAdapter;
import com.tiny.framework.ui.recyclerview.RecyclerViewAdapter;
import com.tiny.framework.ui.recyclerview.interfaces.OnItemClickListener;
import com.tiny.ui.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/7/1.
 */
public class ItemDialog extends BaseCompatDialog implements OnItemClickListener{


    protected RecyclerView mRecyclerView;

    protected RecyclerViewAdapter<String> mAdapter;

    protected ActionMode mode;

    protected OnClickListener mListener;

    protected OnMultiSelectedListener mMultiListener;

    protected ItemDialog(Context context,ActionMode mode,List<String> mList) {
        this(context, 0,mode,mList);
    }

    protected ItemDialog(Context context, int theme,ActionMode mode,List<String> mList) {
        super(context, theme);
        this.mode=mode;
        mRecyclerView= (RecyclerView) View.inflate(context, R.layout.recyceler_view,null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        if(mode==ActionMode.SINGLE){
            mAdapter=new ArrayAdapter<String>(context,mList,android.R.layout.simple_list_item_1);
            mAdapter.setOnItemClickListener(this);
        }else{

        }
        mRecyclerView.setAdapter(mAdapter);
        setView(mRecyclerView);
    }

    public void setClickListener(OnClickListener l){
        this.mListener=l;
    }
    public void setMultiChoiceListener(OnMultiSelectedListener l){
        this.mMultiListener=l;
    }


    @Override
    public void onItemClick(View mItemView, int position) {
        if(mListener!=null){
            mListener.onClick(this,position);
        }
    }

    public static  class Builder{
        Context context;
        String[] items;
        List<String> mList;
        ActionMode mode;
        OnClickListener mListener;
        OnMultiSelectedListener mMultiListener;
        String mTitle;
        int mTitleId;


        protected Builder(Context context){
            this.context=context;
            mode=ActionMode.SINGLE;
        }
        public Builder setItems(String[] items){
            this.items=items;
            return this;
        }
        public Builder setItems(int items){
            this.items=context.getResources().getStringArray(items);
            return this;
        }

        public Builder setItems(List<String> mList){
            this.mList=mList;
            return this;

        }
        public Builder setActionMode(ActionMode mode){
            this.mode=mode;
            return this;

        }
        public Builder setTitle(String title){
            this.mTitle=title;
            return this;

        }
        public Builder setTitle(int title){
            this.mTitleId=title;
            return this;
        }
        public ItemDialog create(){
            if(mList==null){
                mList=Arrays.asList(items);
            }
            ItemDialog dialog=new ItemDialog(context,mode,mList);
            dialog.setClickListener(mListener);
            dialog.setMultiChoiceListener(mMultiListener);
            if(mTitleId!=0){
                mTitle=context.getString(mTitleId);
            }
            if(mTitle!=null){
                dialog.setTitle(mTitle);
            }
            return dialog;
        }
        public void show(){
            create().show();
        }
    }



    public enum ActionMode{
        SINGLE,MULTI
    }
    public interface  OnClickListener {
        void onClick(ItemDialog dialog,int which);
    }
    public interface OnMultiSelectedListener{

    }
}
