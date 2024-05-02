package com.example.appktx2.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.appktx2.R;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.databinding.ItemCommonBinding;
import com.example.appktx2.databinding.ViewMyListViewBinding;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.ui.item.ItemCommon;
import com.example.appktx2.ui.item.ItemField;

import java.util.List;
import java.util.Map;

public class MyListView extends LinearLayout {
    ViewMyListViewBinding binding;
    List<ItemData> itemDataList;
    ICallBack callBack;
    LayoutParams layoutParams;

    public MyListView(Context context) {
        super(context);
        init(context, null);
    }
    public MyListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public MyListView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        binding = ViewMyListViewBinding.inflate(LayoutInflater.from(context), this, true);

        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.itemCommon);
            String title = a.getString(R.styleable.itemCommon_itemCommonTitle);
            String action = a.getString(R.styleable.itemCommon_itemCommonAction);
            Integer icon = a.getResourceId(R.styleable.itemCommon_itemCommonIcon, R.drawable.ic_launcher_foreground);

            if(title != null){

            }

            a.recycle();
        }

        this.layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(30,30,30,30);

        setEvent();
    }
    private void setEvent() {
    }
    public void setTitle(String title){

    }
    public void reRender(){
        binding.getRoot().removeAllViews();
        if(this.itemDataList == null){
            return;
        }
        for (ItemData itemData: this.itemDataList) {
            if(this.layoutParams != null){
                binding.getRoot().addView(ItemCommon.GetItemCommon(getContext(), itemData), this.layoutParams);
            }
            else{
                binding.getRoot().addView(ItemCommon.GetItemCommon(getContext(), itemData));
            }
        }
    }
    public void setItemDataList(@Nullable List<ItemData> itemDataList){
        this.itemDataList = itemDataList;
        this.reRender();
    }
    public void setLayoutParams(LayoutParams layoutParams){
        this.layoutParams = layoutParams;
        reRender();
    }
}
