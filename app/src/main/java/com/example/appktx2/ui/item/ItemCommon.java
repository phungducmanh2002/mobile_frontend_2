package com.example.appktx2.ui.item;

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

import java.util.Map;

import lombok.NonNull;

public class ItemCommon extends LinearLayout {
    public static ItemCommon GetItemCommon(@NonNull Context context,@NonNull ItemData itemData){
        ItemCommon itemCommon = new ItemCommon(context);
        itemCommon.setItemData(itemData);
        return  itemCommon;
    }
    ItemCommonBinding binding;
    ItemData itemData;
    public ItemCommon(Context context) {
        super(context);
        init(context, null);
    }
    public ItemCommon(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public ItemCommon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public ItemCommon(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        binding = ItemCommonBinding.inflate(LayoutInflater.from(context), this, true);

        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.itemCommon);
            String title = a.getString(R.styleable.itemCommon_itemCommonTitle);
            String action = a.getString(R.styleable.itemCommon_itemCommonAction);
            Integer icon = a.getResourceId(R.styleable.itemCommon_itemCommonIcon, R.drawable.ic_launcher_foreground);

            if(title != null){

            }

            a.recycle();
        }
        setEvent();
    }
    private void setEvent() {

    }
    public void reRender(){
        if(this.itemData == null){
            return;
        }

        if(this.itemData.getBackgroundDrawable() != null){
            binding.getRoot().setBackground(this.itemData.getBackgroundDrawable());
        }

        if(this.itemData.getTextColor() != null){
            binding.title.setTextColor(this.itemData.getTextColor());
        }

        if(this.itemData.getTitle() != null){
            binding.title.setText(this.itemData.getTitle());
        }

        if(this.itemData.getItemAction() != null){
            ItemAction.AssignItemAction(this    ,this.itemData.getItemAction(), binding.action);
        }

        if(itemData.getItemResource() != null){
            binding.image.setVisibility(VISIBLE);
            ItemResource.AssignResource(getContext(),this.itemData.getItemResource(), binding.image);
        }
        else{
            binding.image.setVisibility(GONE);
        }
        binding.attrs.removeAllViews();
        if(this.itemData.getFields() != null){
            for (Map.Entry<String, String> attr: this.itemData.getFields().entrySet()) {
                ItemField itemField = ItemField.GetField(getContext(), attr.getKey(), attr.getValue(), this.itemData.getTextColor() != null?this.itemData.getTextColor() : null);
                binding.attrs.addView(itemField);
            }
        }
    }
    public void setItemData(@NonNull ItemData itemData){
        this.itemData = itemData;
        this.reRender();
    }
    public ItemData getItemData(){
        return  this.itemData;
    }
}
