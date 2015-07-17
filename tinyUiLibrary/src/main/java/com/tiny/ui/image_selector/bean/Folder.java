package com.tiny.ui.image_selector.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/8.
 */
public class Folder  {
    public static final String FOLDER_ALL="所有图片";
    public String name;
    public String path;
    public Image cover;
    public ArrayList<Image> images;


    public Folder(){
        images=new ArrayList<>();

    }
    public int getImageCount(){
        if(name!=null&&name.equals(FOLDER_ALL)){
            return images.size()-1;
        }

            return images.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Image getCover() {
        return cover;
    }

    public void setCover(Image cover) {
        this.cover = cover;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Folder other = (Folder) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }

    public  static Folder CollectionFolder(){
        Folder folder=new Folder() ;
        folder.name=FOLDER_ALL;
        return folder;
    }

}
