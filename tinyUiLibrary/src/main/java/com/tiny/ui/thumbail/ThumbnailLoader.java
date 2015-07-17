package com.tiny.ui.thumbail;

/**
 * Created by Administrator on 2015/5/26.
 */
public interface ThumbnailLoader  {

    void loadThumbnail(ThumbnailGridLayout parent, ThumbnailImageView mThumbnailView, Thumbnail source);
    void onRemoveThumbnail(ThumbnailGridLayout parent, ThumbnailImageView mThumbnailView);
}
