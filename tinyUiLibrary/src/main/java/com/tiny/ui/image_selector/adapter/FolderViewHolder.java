package com.tiny.ui.image_selector.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiny.framework.ui.AdapterViewBase.ListView.ListViewItemHelper;
import com.tiny.framework.ui.AdapterViewBase.ListView.SingleCheckedCommonAdapter;
import com.tiny.ui.R;
import com.tiny.ui.image_selector.bean.Folder;

/**
 * Created by Administrator on 2015/6/11.
 */
public class FolderViewHolder extends ListViewItemHelper<Folder> implements SingleCheckedCommonAdapter.ISingleChecked{
    public FolderViewHolder(Context mContext) {
        super(mContext);
    }
    TextView tv_directory,tv_count;
    ImageView iv_conver,iv_tag;
    int mCheckedPosition;
    @Override
    public void bindView(View convertView, int mViewType) {
        tv_directory=findTextView(R.id.tv_name_item_dir);
        tv_count=findTextView(R.id.tv_count_item_dir);
        iv_tag=findImageView(R.id.iv_tag_item_dir);
    }

    @Override
    public void bindData(int position, Folder mItem, int mViewType) {
        tv_directory.setText(mItem.name);
        tv_count.setText(mItem.getImageCount()+"张");
        iv_tag.setVisibility(this.mCheckedPosition==position?View.VISIBLE:View.GONE);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_directory;
    }

    @Override
    public void setCheckedPosition(int mCheckedPosition) {
       this.mCheckedPosition=mCheckedPosition;
    }
}
