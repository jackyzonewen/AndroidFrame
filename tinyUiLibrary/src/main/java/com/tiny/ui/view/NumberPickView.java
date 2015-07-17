package com.tiny.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiny.framework.util.StringUtil;
import com.tiny.ui.R;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2015/1/17.
 */
public class NumberPickView extends LinearLayout implements View.OnClickListener,TextWatcher {

    public static final double MIN_STEP=0.01;

    ImageButton btn_left, btn_right;
    int mLeftSrcId, mRightSrcId;
    int mLeftBackId, mRightBackId;
    int mNumberViewRes;


    TextView mNumberText;
    private double step; //每次加减的步长
    private double mMinValue=Double.MIN_VALUE, mMaxValue=Double.MAX_VALUE;
    private boolean isValueEnable=true;

    private final int ID_LEFT = 0x101;
    private final int ID_RIGHT = 0x102;


    DecimalFormat df = new DecimalFormat(".00");


    ValueObserver mObserver;

    NumberViewClickListener mListener;



    public NumberPickView(Context context) {
        this(context, null);
    }

    public NumberPickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public NumberPickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumberPickView);
        mLeftSrcId = a.getResourceId(R.styleable.NumberPickView_left_src, R.drawable.selector_number_down);
        mRightSrcId = a.getResourceId(R.styleable.NumberPickView_right_src, R.drawable.selector_number_rise);
        mLeftBackId = a.getResourceId(R.styleable.NumberPickView_left_bg, 0);
        mRightBackId = a.getResourceId(R.styleable.NumberPickView_right_bg, 0);
        mNumberViewRes=a.getResourceId(R.styleable.NumberPickView_numberView,0);
        a.recycle();
        init();
    }

    private void init() {
        btn_left = createImageButton(mLeftSrcId, mLeftBackId, ID_LEFT);
        btn_left.setOnClickListener(this);
        addView(btn_left);
        mNumberText =(TextView) View.inflate(getContext(),mNumberViewRes,null);
        mNumberText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mNumberText.setSingleLine(true);
        mNumberText.addTextChangedListener(this);
        LayoutParams mParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mNumberText.setLayoutParams(mParams);
        mNumberText.setGravity(Gravity.CENTER);
        mNumberText.setText(String.valueOf(0.0));
        addView(mNumberText);
        btn_right = createImageButton(mRightSrcId, mRightBackId, ID_RIGHT);
        btn_right.setOnClickListener(this);
        addView(btn_right);

    }

    private ImageButton createImageButton(int mSrcId, int mBackId, int id) {
        ImageButton btn = new ImageButton(getContext());
        btn.setId(id);
        btn.setImageResource(mSrcId);
        btn.setBackgroundResource(mBackId);
        LayoutParams mParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(mParams);
        return btn;
    }


    @Override
    public void onClick(View v) {
        double value = getNumberData();
        switch (v.getId()) {
            case ID_LEFT:
                if ((value - step) < mMinValue) {
                    if(mObserver!=null){
                        mObserver.onValueCrushToMin();
                    }
                } else {
                    value = value - step;
                }
                break;
            case ID_RIGHT:
                if ((value + step) > mMaxValue) {
                    if(mObserver!=null){
                        mObserver.onValueCrushToMax();
                    }
                } else {
                    value = value + step;
                }
                break;
        }
        setNumberData(value);
    }

    public void setNumberData(double v) {
        if (v <= 0) {
            mNumberText.setText(String.valueOf(0.0));
        } else {
            mNumberText.setText(df.format(v));
        }
    }

    public double getNumberData() {
        if (StringUtil.isEmpty(mNumberText)) {
            mNumberText.setText(String.valueOf(0.0));
            return 0;
        }
        return Double.parseDouble(mNumberText.getText().toString());
    }

    public void setChildEnable(boolean enable) {
        btn_left.setEnabled(enable);
        btn_right.setEnabled(enable);
        mNumberText.setEnabled(enable);
        isValueEnable=enable;
    }
    public boolean isVauleEnable(){
        return isValueEnable;
    }
    public void setEditTextFocusable(boolean focusable){
        mNumberText.setFocusable(focusable);
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        if(step<MIN_STEP){
            step=MIN_STEP;
        }
        this.step = step;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(mObserver!=null){
            mObserver.onValueChanged(getNumberData());
        }

    }

    public double getMinValue() {
        return mMinValue;
    }

    public void setMinValue(double mMinValue) {
        this.mMinValue = mMinValue;
    }

    public double gemMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(double mMaxValue) {
        this.mMaxValue = mMaxValue;
    }



    public void setValueObserver(ValueObserver mObserver) {
        this.mObserver = mObserver;
    }
    public void setNumberViewClickListener(NumberViewClickListener l){
        this.mListener = l;
        mNumberText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null){
                    mListener.onNumberViewClick(mNumberText,getTag(),getNumberData());
                }
            }
        });
    }

    public void setInputType(int type){
        mNumberText.setInputType(type);
    }



    public interface  ValueObserver  {
        void onValueChanged(double value);
        //当改变后的值大于最大值
        void onValueCrushToMax();
        void onValueCrushToMin();
    }
    public interface NumberViewClickListener{
        void onNumberViewClick(TextView mNumberView,Object tag,double value);
    }
}
