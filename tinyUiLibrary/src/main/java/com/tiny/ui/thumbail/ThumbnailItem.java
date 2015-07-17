package com.tiny.ui.thumbail;

/**
 * Created by Administrator on 2015/6/15.
 */
public class ThumbnailItem implements Thumbnail {


    int width;
    int height;
    String url;

    public ThumbnailItem(String url) {
        this.url=url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getThumbnailWidth() {
        return width;
    }

    @Override
    public int getThumbnailHeight() {
        return height;
    }

    @Override
    public String getThumbnailUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null||!(o instanceof ThumbnailItem)){
            return false;
        }
        ThumbnailItem out= (ThumbnailItem) o;
        if(this.getThumbnailUrl()==null){
            return false;
        }
        if((getThumbnailWidth()==out.getThumbnailWidth()&&(getThumbnailHeight()==out.getThumbnailHeight())&&(getThumbnailUrl().equals(out.getThumbnailUrl())))){
            return true;
        }
        return false;
    }
}
