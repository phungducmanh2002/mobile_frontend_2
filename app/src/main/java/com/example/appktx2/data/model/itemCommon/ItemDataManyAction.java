package com.example.appktx2.data.model.itemCommon;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDataManyAction {
    Integer id;
    String title;
    ItemResource itemResource;
    List<ItemAction> itemActionList;
    Map<String, String> fields;
    Integer textColor = Color.BLACK;
    Drawable backgroundDrawable;
}
