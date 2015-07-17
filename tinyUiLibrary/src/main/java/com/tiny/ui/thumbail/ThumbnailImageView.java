package com.tiny.ui.thumbail;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/5/26.
 */
public class ThumbnailImageView extends ImageView {


    private int rowSpec;

    private int columnSpec;

    private ThumbnailLoader mLoader;

    private Thumbnail mThumbnail;

    private ThumbnailGridLayout mGridLayout;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(getParent() instanceof ThumbnailGridLayout){
            this.mGridLayout= (ThumbnailGridLayout) getParent();
        }
    }

    public ThumbnailImageView(Context context) {
        super(context);
    }
    public void setSpec(int rowSpec,int columnSpec){
        this.rowSpec=rowSpec;
        this.columnSpec=columnSpec;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       int spcMode= MeasureSpec.getMode(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    public void setThumbnailData(Thumbnail item){
        if(mThumbnail==null||!this.mThumbnail.equals(item)){
            this.mThumbnail=item;
            if(mLoader!=null){
                mLoader.loadThumbnail(mGridLayout,this,item);
            }
        }

    }
    public void removeThumbnailData(){
        this.mThumbnail=null;
        this.setTag(null);
    }
    public void setThumbnailLoader(ThumbnailLoader loader){
        this.mLoader=loader;
    }
}
