package com.example.appktx2.ui.components;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.appktx2.databinding.ComponentPickDateBinding;

import java.util.Calendar;
import java.util.Date;
import  com.example.appktx2.R;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.utils.DateUtils;

public class PickDate extends LinearLayout {
    ICallBack callBack;
    ComponentPickDateBinding binding;
    Date date = new Date();
    boolean isPick = false;
    boolean isShowWarning = false;
    public PickDate(Context context) {
        super(context);
        init(context, null);
    }

    public PickDate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PickDate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public PickDate(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
//        inflate(getContext(), R.layout.custome_input_fill, this);
        binding = ComponentPickDateBinding.inflate(LayoutInflater.from(context), this, true);

        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.selector);
            String pickDateLabel = a.getString(R.styleable.pickerDate_pickDateLabel);
            Integer iconResId = a.getResourceId(R.styleable.pickerDate_pickDateIcon, R.drawable.ic_celender);

            if(pickDateLabel != null){
                binding.label.setText(pickDateLabel);
            }
            else{
                setDate(new Date());
            }
            if(iconResId != null){
                binding.icon.setImageResource(iconResId);
            }
            a.recycle();
        }
        setEvent();
    }
    private void setEvent() {
        binding.icon.setOnClickListener(v -> {
            showPickDate();
        });
    }
    private void showPickDate(){
        final Calendar calender = Calendar.getInstance();
        if(this.date != null){
            calender.setTime(this.date);
        }
        int mYear = calender.get(Calendar.YEAR);
        int mMonth = calender.get(Calendar.MONTH);
        int mDay = calender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, month, dayOfMonth) -> {
                    if(!isPick){
                        isPick = true;
                    }
                    Date newDate = DateUtils.CreateDate(year, month, dayOfMonth);
                    setDate(newDate);
                    if(callBack != null){
                        callBack.action(newDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void setDate(Date date){
        this.date = date;
        binding.label.setText(DateUtils.Date2String(date));
    }
    public Date getDate(){
        if(!isPick)
            return null;
        return  this.date;
    }
    public void setCallBack(ICallBack callback){
        this.callBack = callback;
    }
    public void setWarning(String warning){
        binding.warning.setText(warning);
        setShowWarning(true);
    }
    public void setShowWarning(boolean isShow){
        this.isShowWarning = isShow;
        if(isShow == true){
            binding.warning.setVisibility(VISIBLE);
        }
        else{
            binding.warning.setVisibility(GONE);
        }
    }
    public boolean isShowWarning(){
        return this.isShowWarning;
    }
    public void pickDate(){
        binding.icon.callOnClick();
    }
    public void setText(String text){
        binding.label.setText(text);
    }
}
