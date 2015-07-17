package com.tiny.ui.image_selector.bean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.tiny.framework.ui.recyclerview.interfaces.IViewTypeResolve;
import com.tiny.image.NativeImageLoader;
import com.tiny.ui.photoview.ImageCache;

import java.io.IOException;

/**
 * Created by Administrator on 2015/6/8.
 */
public class Image implements IViewTypeResolve,Parcelable{

    public static final String CAMERA="multi_camera";

    public String path;
    public String name;
    public long time;
    private Bitmap bitmap;
    public boolean isSelected;

    public Image(){

    }
    public Image(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

    @Override
    public int getViewType() {
        if(path.equals(CAMERA)){
            return 1;
        }
        return 0;
    }

    public static Image CameraItem(){
       Image image= new Image();
       image.path=CAMERA;
        return image;
    }

    /**
     * 是否为相机的item
     * @return
     */
    public static boolean isCameraItem(Image image){
        if(image==null||image.path==null){
            return false;
        }
        return image.path.equalsIgnoreCase(CAMERA);

    }

    public Bitmap getBitmap() {
        if(bitmap == null){
            try {
                bitmap = ImageCache.revitionImageSize(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getTrueBitmap() {
        return bitmap;
    }

    public void recycle(){
        if(bitmap!=null&&!bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.name);
        dest.writeLong(this.time);
        dest.writeParcelable(this.bitmap, 0);
        dest.writeByte(isSelected ? (byte) 1 : (byte) 0);
    }

    private Image(Parcel in) {
        this.path = in.readString();
        this.name = in.readString();
        this.time = in.readLong();
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
