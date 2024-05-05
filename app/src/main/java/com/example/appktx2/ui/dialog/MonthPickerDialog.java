package com.example.appktx2.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appktx2.databinding.DialogMonthPickerDialogBinding;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.utils.DateUtils;
import com.example.appktx2.R;

import java.util.ArrayList;
import java.util.List;

public class MonthPickerDialog extends Dialog {
    DialogMonthPickerDialogBinding binding;
    LinearLayout.LayoutParams layoutParams;
    Integer month, year;
    List<TextView> monthList;
    ICallBack callBackOnSelect, callBackOnCancle;
    public MonthPickerDialog(@NonNull Context context) {
        super(context);
        binding = DialogMonthPickerDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        if(window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttrs = window.getAttributes();
        windowAttrs.gravity = Gravity.CENTER;
        setCancelable(true);

        this.layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(30,30,30,30);

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
    public MonthPickerDialog(@NonNull Context context, Integer month, Integer year) {
        this(context);
        this.setMonth(month);
        this.setYear(year);
    }
    private void setEvent() {

        binding.select.setOnClickListener(v -> {
            if(callBackOnSelect != null){
                callBackOnSelect.action(this.month, this.year);
            }
            this.cancel();
        });

        binding.cancle.setOnClickListener(v -> {
            if(callBackOnCancle != null){
                callBackOnCancle.action(this.month, this.year);
            }
            this.cancel();
        });

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