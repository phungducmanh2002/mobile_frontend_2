package com.example.appktx2.ui.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.appktx2.R;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.data.model.itemCommon.ItemDataManyAction;
import com.example.appktx2.data.model.itemCommon.ItemResource;

import java.util.Map;

import lombok.NonNull;

public class ItemManyAction extends ItemCommon {
    ItemDataManyAction itemDataManyAction;
    LayoutParams layoutParams;


    public ItemManyAction(Context context) {
        super(context);

    }

    public ItemManyAction(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemManyAction(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public ItemManyAction(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setItemDataManyAction(ItemDataManyAction itemDataManyAction){
        this.itemDataManyAction = itemDataManyAction;
        this.reRender();
    }
    @Override
    public void reRender() {

        if(layoutParams == null){
            this.layoutParams = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(30,30,30,30);
        }

        binding.wrapper.setOrientation(VERTICAL);
        if(this.itemDataManyAction == null){
            return;
        }

        if(this.itemDataManyAction.getBackgroundDrawable() != null){
            binding.getRoot().setBackground(this.itemDataManyAction.getBackgroundDrawable());
        }

        if(this.itemDataManyAction.getTextColor() != null){
            binding.title.setTextColor(this.itemDataManyAction.getTextColor());
        }

        if(this.itemDataManyAction.getTitle() != null){
            binding.title.setText(this.itemDataManyAction.getTitle());
        }

        if(this.itemDataManyAction.getItemActionList() != null){
            binding.actionList.removeAllViews();
            for (ItemAction itemAction: this.itemDataManyAction.getItemActionList()) {
                AppCompatButton btnAction = ItemAction.CreateItemAction(this, itemAction);
                if(btnAction.getParent() != null){
                    ((ViewGroup)btnAction.getParent()).removeView(btnAction);
                }
                binding.actionList.addView(btnAction, layoutParams);
            }
        }

        if(itemDataManyAction.getItemResource() != null){
            binding.image.setVisibility(VISIBLE);
            ItemResource.AssignResource(getContext(),this.itemDataManyAction.getItemResource(), binding.image);
        }
        else{
            binding.image.setVisibility(GONE);
        }
        binding.attrs.removeAllViews();
        if(this.itemDataManyAction.getFields() != null){
            for (Map.Entry<String, String> attr: this.itemDataManyAction.getFields().entrySet()) {
                ItemField itemField = ItemField.GetField(getContext(), attr.getKey(), attr.getValue(), this.itemDataManyAction.getTextColor() != null?this.itemDataManyAction.getTextColor() : null);
                binding.attrs.addView(itemField);
            }
        }
    }
    @Override
    public void setItemData(@NonNull ItemData itemData) {

    }
}
