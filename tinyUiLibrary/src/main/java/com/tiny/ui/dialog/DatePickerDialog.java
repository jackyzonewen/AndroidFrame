package com.tiny.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;

import com.tiny.ui.R;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/6/10.
 */
public class DatePickerDialog extends BaseCompatDialog implements DialogInterface.OnClickListener,DatePicker.OnDateChangedListener{


    DatePicker mDatePicker;

    OnDateSetListener mListener;

    Calendar mCalendar;


    public DatePickerDialog(Context context,OnDateSetListener callBack){
        this(context,callBack,-1,-1,-1);
    }
    public DatePickerDialog(Context context,OnDateSetListener callBack,int year,int month,int day) {
        super(context, 0);
        mCalendar =Calendar.getInstance();
        mListener=callBack;
        if(year==-1){
            year= mCalendar.get(Calendar.YEAR);
            month= mCalendar.get(Calendar.MONTH);
            day= mCalendar.get(Calendar.DAY_OF_MONTH);
        }
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        View view=View.inflate(context, R.layout.date_picker_dialog,null);
        setView(view);
        mDatePicker= (DatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(year,month,day,this);
        setButton(DialogInterface.BUTTON_POSITIVE, getContext().getString(R.string._sure), this);
        setButton(DialogInterface.BUTTON_NEGATIVE, getContext().getString(R.string._cancle), this);
        updateTitle(year,month,day);
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                if(mListener!=null){
                    mListener.onDateSet(mDatePicker, mDatePicker.getYear(),mDatePicker.getMonth(),mDatePicker.getDayOfMonth());
                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        updateTitle(year, monthOfYear, dayOfMonth);
    }
    private void updateTitle(int year,int month,int day){
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.DAY_OF_MONTH, day);
        String title = DateUtils.formatDateTime(getContext(),
                mCalendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_WEEKDAY
                        | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH
                        | DateUtils.FORMAT_ABBREV_WEEKDAY);
        setTitle(title);
    }

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListener {

        /**
         * @param view The view associated with this listener.
         * @param year The year that was set.
         * @param monthOfYear The month that was set (0-11) for compatibility
         *  with {@link java.util.Calendar}.
         * @param dayOfMonth The day of the month that was set.
         */
        void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth);
    }
}
