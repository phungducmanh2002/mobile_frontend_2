package com.example.appktx2.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.appktx2.R;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.databinding.ViewMyListViewBinding;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.ui.item.ItemCommon;

import java.util.List;

public class MyImageListView extends LinearLayout {
    ViewMyListViewBinding binding;
    LayoutParams layoutParams;
    public MyImageListView(Context context) {
        super(context);
        init(context, null);
    }
    public MyImageListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public MyImageListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public MyImageListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        binding = ViewMyListViewBinding.inflate(LayoutInflater.from(context), this, true);

        this.layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(30,30,30,30);

        setEvent();
    }
    private void setEvent() {
    }
    public void resetView(){
        binding.getRoot().removeAllViews();
    }
    public void addChildren(View view){
        if(view.getParent() != null){
            ((ViewGroup)view.getParent()).removeView(view);
        }
        binding.getRoot().addView(view, layoutParams);
    }
    public void  addChildren(View... views){
        for (View view:views ) {
            this.addChildren(view);
        }
    }
    public void setLayoutParams(LayoutParams layoutParams){
        this.layoutParams = layoutParams;
    }
}
