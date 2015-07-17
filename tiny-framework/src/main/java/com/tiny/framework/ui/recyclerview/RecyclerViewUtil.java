package com.tiny.framework.ui.recyclerview;

import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2015/5/5.
 */
public class RecyclerViewUtil {

    private  RecyclerViewUtil(){

    }

    /**
     * 反射取得ViewItemHelper的实例
     * <p>这里在反射构造的时候，如果BaseViewHolder是static class 则不需要mConstructObject实例作为构造参数</p>
     * 所要创建BaseViewHolder类必须存在public的构造
     * 如果是Activity或者fragment的内部类，则需要activity或者fragm的实例作为第一个参数，这里mConstructContext就是activity
     * @param cls BaseViewHolder的class
     * @param mItemView
     * @param mConstructObject BaseViewHolder 实例的构造参数，如果BaseViewHolder是activity或者fragment的内部类，
     *                         该参数就是activity或者fragment的对象
     * @return 一个BaseViewHolder实例
     */
    public static  BaseViewHolder createViewItemInstance(Class cls,View mItemView, Object mConstructObject) {
        try {
            Constructor<BaseViewHolder>[] constructors = cls.getConstructors();
            if (constructors == null && constructors.length == 0) {
                return null;
            }
            for (Constructor<BaseViewHolder> mCons : constructors) {
                Class[] mParameterTypes = mCons.getParameterTypes();
                Object[] params = null;
                switch (mParameterTypes.length) {
                    case 1:
                        params = new Object[]{mItemView};
                        return mCons.newInstance(params);
                    case 2:
                        params = new Object[]{mConstructObject, mItemView};
                        return mCons.newInstance(params);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
