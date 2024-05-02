package com.example.appktx2.ui.item;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.appktx2.R;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.databinding.ItemCommonBinding;
import com.example.appktx2.databinding.ItemImageBinding;
import com.example.appktx2.interfaces.ICallBack;

import java.util.Map;

import lombok.Builder;

public class ItemImage extends LinearLayout {
    ItemImageBinding binding;
    ItemResource itemResource;
    public ItemImage(Context context) {
        super(context);
        init(context, null);
    }
    public ItemImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    public ItemImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public ItemImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }
    private void init(Context context, AttributeSet attrs){
        binding = ItemImageBinding.inflate(LayoutInflater.from(context), this, true);

        if(attrs != null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.itemImage);
            Integer image = a.getResourceId(R.styleable.itemImage_itemImageImage, R.drawable.ic_launcher_foreground);
            Integer iconAction = a.getResourceId(R.styleable.itemImage_itemImageActionIcon, R.drawable.ic_close);

            if(image != null){
                setImage(image);
            }
            if(iconAction !=  null){
                setActionIcon(iconAction);
            }

            a.recycle();
        }
        setEvent();
    }
    private void setEvent() {
//        binding.option.setOnClickListener(v -> {
//            if(i_optionHandler != null){
//                try {
//                    i_optionHandler.Callback(idRoom, idResouece);
//                }
//                catch (Exception ex){
//                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//            else{
//                Toast.makeText(getContext(), "handler not defind", Toast.LENGTH_SHORT).show();
//            }
////            String tmp  = idResouece.toString() + "-" + idRoom;
////            Toast.makeText(getContext(), tmp, Toast.LENGTH_SHORT).show();
////            if(i_optionHandler != null && idResouece != null && idRoom != null){
////                i_optionHandler.Callback(idRoom, idResouece);
////            }
//        });
    }
    public ItemResource getItemResource(){return this.itemResource;}
    public void setImage(Integer resId){
        binding.image.setImageResource(resId);
    }
    public void setActionIcon(Integer resId){
        binding.action.setImageResource(resId);
    }
    public void reRender(){
        if(this.itemResource == null){
            return;
        }
        if(this.itemResource.getIdResource() != null){
            binding.image.setVisibility(VISIBLE);
            ItemResource.AssignResource(getContext(), this.itemResource, binding.image);
        }
        if(this.itemResource.getCallBack() == null){
            binding.action.setVisibility(GONE);
        }
        else{
            binding.action.setVisibility(VISIBLE);
            binding.action.setOnClickListener(v -> {
                this.itemResource.getCallBack().action(ItemImage.this);
            });
        }
    }
    public void setIdResource(@Nullable Integer idResource){
        this.itemResource = ItemResource.GetItemResourceResServer(idResource);
        this.reRender();
    }
    public void setActionIcon(Drawable icon){
        binding.action.setImageDrawable(icon);
    }
    public void setItemResource(@Nullable ItemResource itemResource){
        this.itemResource = itemResource;
        reRender();
    }
    public void setOnAction(ICallBack callBack){
        binding.action.setOnClickListener(v -> {
            callBack.action(this);
            binding.action.setVisibility(VISIBLE);
        });
    }
}
