package com.tiny.ui.image_selector.ui;

import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tiny.framework.mvp.impl.vu.RecyclerViewVuImpl;
import com.tiny.framework.ui.AdapterViewBase.ListView.CommonAdapter;
import com.tiny.framework.ui.recyclerview.RecyclerViewAdapter;
import com.tiny.framework.ui.recyclerview.interfaces.OnItemClickListener;
import com.tiny.ui.R;
import com.tiny.ui.image_selector.bean.Folder;
import com.tiny.ui.view.ListPopupWindow;

/**
 * Created by Administrator on 2015/6/10.
 */
public class MultiImageSelectorVu extends RecyclerViewVuImpl<MultiImageSelectorCallBack> implements AdapterView.OnItemClickListener,OnItemClickListener{

    TextView tv_directory;
    ListPopupWindow mListPop;
    TextView tv_preview,tv_finish;

    @Override
    protected void onViewCreate() {
        super.onViewCreate();
        tv_preview =findTextView(R.id.tv_preview_image_selector);
        tv_directory=findTextView(R.id.tv_dir_image_selector);
        tv_finish=findTextView(R.id.tv_finish_image_selector);
        tv_finish.setOnClickListener(this);
        tv_directory.setOnClickListener(this);
        tv_preview.setOnClickListener(this);
        intPopWindow();
    }

    public void setUiVisibility(boolean isShow){
//        tv_preview.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        tv_finish.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    private void intPopWindow() {
        mListPop=new ListPopupWindow(getContext(),null);
        DisplayMetrics dm=getContext().getResources().getDisplayMetrics();
        mListPop.setHeight(dm.heightPixels * 5 / 8);
        mListPop.setOnItemClickListener(this);
    }

    public void setListPopAdapter(CommonAdapter<Folder> mAdapter){
        mListPop.setListAdapter(mAdapter);
        mAdapter.setAdapterView(mListPop.getListView());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getCallBack().onDirectorySelected(position);
        mListPop.setSelection(position);
        mListPop.dismiss();
    }
    public void updateDirTextView(String text){
        tv_directory.setText(text);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter mAdapter) {
        super.setAdapter(mAdapter);
        if(mAdapter!=null&&mAdapter instanceof RecyclerViewAdapter){
            ((RecyclerViewAdapter)mAdapter).setOnItemClickListener(this);
        }
    }


    @Override
    public void onItemClick(View mItemView, int position) {
        getCallBack().onDisplayImageClick(position);
    }
    public void updateSelectedItemCount(int max,int count){
        if(count==0){
            tv_finish.setText("完成");
            tv_finish.setEnabled(false);
            tv_preview.setText("预览");
            tv_preview.setEnabled(false);
        }else{
            tv_finish.setText("完成("+count+"/"+max+")");
            tv_finish.setEnabled(true);
            tv_preview.setEnabled(true);
            tv_preview.setText("预览( " + count + " )");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id=v.getId();
        if(id==R.id.tv_dir_image_selector){
            if(mListPop!=null){
//                mListPop.showAsDropDown(tv_directory);
                PopupWindowCompat.showAsDropDown(mListPop,tv_directory,0,0, Gravity.TOP);
            }
        }else if(id==R.id.tv_preview_image_selector){
            getCallBack().preView();
        }else if(id==R.id.tv_finish_image_selector){
            getCallBack().finishTask(false);
        }
    }
}
