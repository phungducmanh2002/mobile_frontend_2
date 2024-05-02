package com.example.appktx2.data.model.itemCommon;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.example.appktx2.R;
import com.example.appktx2.interfaces.ICallBack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemAction {
    public static ItemAction GetActionAdd(@NonNull Context context){
        return ItemAction.builder()
                .id(1)
                .text("Thêm")
                .textColor(context.getColor(R.color.white))
                .background(context.getDrawable(R.drawable.bg_rounded_blue))
                .callBack((Object... object) -> {
                    Toast.makeText(context, "ADD", Toast.LENGTH_SHORT).show();
                })
                .build();
    }
    public static void AssignItemAction(View view, @NonNull ItemAction itemAction, @NonNull AppCompatButton appCompatButton){
        if(itemAction.getText() != null){
            appCompatButton.setText(itemAction.getText());
        }
        if(itemAction.getBackground() != null){
            appCompatButton.setBackgroundDrawable(itemAction.getBackground());
        }
        if(itemAction.getTextColor() != null){
            appCompatButton.setTextColor(itemAction.getTextColor());
        }
        if(itemAction.callBack != null){
            appCompatButton.setOnClickListener(v -> {
                itemAction.callBack.action(view);
            });
        }
    }
    public static AppCompatButton CreateItemAction(View view, @NonNull ItemAction itemAction){
        AppCompatButton appCompatButton = new AppCompatButton(view.getContext());
        if(itemAction.getText() != null){
            appCompatButton.setText(itemAction.getText());
        }
        if(itemAction.getBackground() != null){
            appCompatButton.setBackgroundDrawable(itemAction.getBackground());
        }
        if(itemAction.getTextColor() != null){
            appCompatButton.setTextColor(itemAction.getTextColor());
        }
        if(itemAction.callBack != null){
            appCompatButton.setOnClickListener(v -> {
                itemAction.callBack.action(view);
            });
        }
        return appCompatButton;
    }

    Integer id;
    Integer textColor;
    Drawable background;
    String text;
    ICallBack callBack;
    Object storedData;
}
