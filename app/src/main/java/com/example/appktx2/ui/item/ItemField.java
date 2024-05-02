package com.example.appktx2.ui.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.appktx2.R;
import com.example.appktx2.databinding.ItemFieldBinding;
import com.example.appktx2.interfaces.ICallBack;

public class ItemField extends LinearLayout {
    public static ItemField GetField(Context context, String label, String value, Integer textColor){
        ItemField itemField = new ItemField(context);
        itemField.setLabel(label);
        itemField.setValue(value);
        if(textColor != null){
            itemField.setTextColor(textColor);
        }
        return  itemField;
    }
    ItemFieldBinding binding;
    ICallBack callBack;
    String label, value;
    Integer textColor = Color.BLACK;

    public ItemField(Context context) {
        super(context);
        init(context, null);
    }
    public ItemField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public ItemField(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public ItemField(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        binding = ItemFieldBinding.inflate(LayoutInflater.from(context), this, true);

        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.field);
            String fieldLabel = a.getString(R.styleable.field_fieldLabel);
            String fieldValue = a.getString(R.styleable.field_fieldValue);

            if(fieldLabel != null){
                setLabel(fieldLabel);
            }

            if(fieldValue != null){
                setValue(fieldValue);
            }

            a.recycle();
        }
        setEvent();
    }
    private void setEvent() {
        binding.getRoot().setOnClickListener(v -> {
            if(callBack != null){
                callBack.action(v, label, value);
            }
        });
    }
    public void setCallBack(ICallBack callBack){
        this.callBack = callBack;
    }
    public void setLabel(String label) {
        this.label = label;
        reRender();
    }
    public void setValue(String value){
        this.value = value;
        reRender();
    }
    public void setTextColor(Integer textColor){
        this.textColor = textColor;
        reRender();
    }
    public String getLabel(){return  this.label;}
    public String getValue(){return  this.value;}
    public void reRender(){
        binding.label.setTextColor(this.textColor);
        binding.value.setTextColor(this.textColor);

        binding.label.setText(this.label);
        binding.value.setText(this.value);
    }
}
