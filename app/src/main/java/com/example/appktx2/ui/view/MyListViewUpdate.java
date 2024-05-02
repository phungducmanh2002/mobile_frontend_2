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
import com.example.appktx2.ui.item.ItemManyAction;

import java.util.List;

public class MyListViewUpdate extends MyListView {
    public MyListViewUpdate(Context context) {
        super(context);
    }
    public MyListViewUpdate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    public MyListViewUpdate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public MyListViewUpdate(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void reRender() {

    }
    public void resetView(){
        binding.getRoot().removeAllViews();
    }
    public void addChildren(View view){
        if(view.getParent() != null){
            ((ViewGroup)view.getParent()).removeView(view);
        }
        binding.getRoot().addView(view);
    }
    public void addChildren(View... view){
        addChildren(view);
    }
}
