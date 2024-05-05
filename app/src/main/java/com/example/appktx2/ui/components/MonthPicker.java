package com.example.appktx2.ui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.appktx2.R;
import com.example.appktx2.databinding.ComponentInputBinding;
import com.example.appktx2.databinding.ComponentMonthPickerBinding;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class MonthPicker extends LinearLayout {
    public ComponentMonthPickerBinding binding;
    boolean isShowWarning = false;
    Integer month, year;
    ICallBack callBackOnSelect, callBackOnCancle;
    List<TextView> monthList;
    public MonthPicker(Context context) {
        super(context);
        init(context, null);
    }

    public MonthPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MonthPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public MonthPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs) {
        binding = ComponentMonthPickerBinding.inflate(LayoutInflater.from(context), this, true);

        monthList = new ArrayList<>();
        monthList.add(binding.thang1);
        monthList.add(binding.thang2);
        monthList.add(binding.thang3);
        monthList.add(binding.thang4);
        monthList.add(binding.thang5);
        monthList.add(binding.thang6);
        monthList.add(binding.thang7);
        monthList.add(binding.thang8);
        monthList.add(binding.thang9);
        monthList.add(binding.thang10);
        monthList.add(binding.thang11);
        monthList.add(binding.thang12);

        this.month = DateUtils.GetCurrentMonth();
        this.year  = DateUtils.GetCurrentYear();

        setMonth();
        setYear();

        setEvent();
    }
    private void setEvent() {
        binding.preYear.setOnClickListener(v -> {
            this.year = this.year - 1;
            setYear();
        });

        binding.nextYear.setOnClickListener(v -> {
            this.year = this.year + 1;
            setYear();
        });

        for (int i = 0; i < this.monthList.size(); i++) {
            TextView tv = this.monthList.get(i);
            tv.setOnClickListener(v -> {
                String text = ((TextView)v).getText().toString();
                Integer selectMonth = Integer.parseInt(text.replace("Tháng ", ""));
                setMonth(selectMonth);
            });
        }
    }
    public void setShowWarning(boolean isShow){
        this.isShowWarning = isShow;
        if(this.isShowWarning){
            binding.warning.setVisibility(VISIBLE);
        }
        else{
            binding.warning.setVisibility(GONE);
        }
    }
    public boolean getShowWarning(){
        return  this.isShowWarning;
    }
    public void setWarning(String warning){
        binding.warning.setText(warning);
        setShowWarning(true);
    }
    public void onSelect(ICallBack callBack){
        this.callBackOnSelect = callBack;
    }
    public void onCancle(ICallBack callBack){
        this.callBackOnCancle = callBack;
    }
    public void setMonth(Integer month){
        this.month = month;
        resetMonthUI();
        switch (month){
            case 1:{
                binding.thang1.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang1.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 2:{
                binding.thang2.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang2.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 3:{
                binding.thang3.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang3.setTextColor(getContext().getColor(R.color.white));
                break;
            }

            case 4:{
                binding.thang4.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang4.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 5:{
                binding.thang5.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang5.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 6:{
                binding.thang6.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang6.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 7:{
                binding.thang7.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang7.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 8:{
                binding.thang8.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang8.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 9:{
                binding.thang9.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang9.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 10:{
                binding.thang10.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang10.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 11:{
                binding.thang11.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang11.setTextColor(getContext().getColor(R.color.white));
                break;
            }
            case 12:{
                binding.thang12.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
                binding.thang12.setTextColor(getContext().getColor(R.color.white));
                break;
            }
        }
    }
    public void setYear(Integer year){
        this.year = year;
        binding.year.setText(year.toString());
    }
    public void setMonth(){
        resetMonthUI();
        TextView tv =  this.monthList.get((this.month - 1));
        tv.setBackground(getContext().getDrawable(R.drawable.bg_rounded_orange));
        tv.setTextColor(getContext().getColor(R.color.white));
    }
    public void setYear(){
        binding.year.setText(this.year.toString());
    }
    private void resetMonthUI(){
        for (int i = 0; i < this.monthList.size(); i++) {
            TextView tv = this.monthList.get(i);
            tv.setBackground(getContext().getDrawable(R.drawable.bg_rounded_smoke));
            tv.setTextColor(getContext().getColor(R.color.black));
        }
    }
    public Integer getMonth(){
        return this.month;
    }
    public Integer getYear(){
        return this.year;
    }
}
