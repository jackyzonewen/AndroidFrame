package com.tiny.ui.camera_preview;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiny.framework.mvp.impl.vu.BaseVuImpl;
import com.tiny.ui.R;

/**
 * Created by Administrator on 2015/6/9.
 */
public class CameraPreViewVu extends BaseVuImpl {


    TextView tv_send,tv_size;
    ImageView mDisplay;
    CheckBox mCheckBox;
    EventCallBack callBack;

    @Override
    protected void onViewCreate() {
        super.onViewCreate();
        tv_send=findTextView(R.id.tv_send_camera_preview);
        tv_size=findTextView(R.id.tv_size_camera_preview);
        mDisplay=findImageView(R.id.iv_display_camera_preview);
        mCheckBox= (CheckBox) getContentView().findViewById(R.id.cb_camera_preview);
        tv_send.setOnClickListener(this);
    }

    public void setCallBack(EventCallBack callBack){
        this.callBack=callBack;
    }

    public boolean userOriginalBitmap(){
        return mCheckBox.isChecked();
    }

    public void displayImage(Bitmap bitmap){
        mDisplay.setImageBitmap(bitmap);
    }
    public void setBitmapCacheSize(String size){
        tv_size.setText("(" + size + ")");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId()==R.id.tv_send_camera_preview){
            callBack.sendBitmap();
        }
    }

    public interface EventCallBack{
        void sendBitmap();
    }
}
