package com.example.appktx2.utils;

import android.content.Context;

import com.example.appktx2.R;
import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.SemesterDto;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemData;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.NonNull;

public class MapperUtils {
    public static <T> T ConvertFromLinkedTreeMap(LinkedTreeMap<?, ?> linkedTreeMap, Class<T> cls){
        Gson gson = new Gson();
        String json = gson.toJson(linkedTreeMap);
        Type type = TypeToken.getParameterized(cls, cls).getType();
        return gson.fromJson(json,type);
    }

    public static <T> T ConvertFromObject(Object object, Class<T> cls){
        LinkedTreeMap<?, ?> linkedTreeMap = (LinkedTreeMap<?, ?>)object;
        Gson gson = new Gson();
        String json = gson.toJson(linkedTreeMap);
        Type type = TypeToken.getParameterized(cls, cls).getType();
        return gson.fromJson(json,type);
    }
    public static List<ItemData> ConvertListRoomDto2ItemData(Context context, List<RoomDto> roomDtoList){
        List<ItemData> itemDataList = new ArrayList<>();
        for (RoomDto roomDto: roomDtoList) {

            Map<String, String> attrs = new HashMap<>();
            if(roomDto.getIdRoomCollection() != null){
                attrs.put("Collection: ", roomDto.getRoomCollection().getRoomCollectionName());
            }
            attrs.put("Gender: ", roomDto.getRoomGender() == 0 ? "Male" : "Female");
            attrs.put("Status: ", roomDto.getRoomStatus() == 0 ? "Active" : "Disable");


            ItemData itemData = new ItemData();
            itemData.setItemResource(null);
            itemData.setItemAction(ItemAction.builder()
                            .storedData(roomDto.getId())
                            .background(context.getDrawable(R.drawable.bg_rounded_blue))
                            .textColor(context.getColor(R.color.white))
                            .callBack(CallBackManager.GetCallBackItemActionRoomManager(context))
                            .text("Detail")
                    .build());
            itemData.setTitle(roomDto.getRoomName());
            itemData.setFields(attrs);
            itemData.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_smoke));

            itemDataList.add(itemData);
        }
        return itemDataList;
    }
    public static List<ItemData> ConvertListItemDto2ItemData(@NonNull Context context, List<ItemDto> roomDtoList){
        List<ItemData> itemDataList = new ArrayList<>();
        for (ItemDto itemDto: roomDtoList) {

            Map<String, String> attrs = new HashMap<>();
            attrs.put("Quantity: ", Integer.toString(itemDto.getQuantity()));

            ItemData itemData = new ItemData();
            itemData.setItemResource(null);
            itemData.setItemAction(ItemAction.builder()
                    .storedData(itemDto.getId())
                    .background(context.getDrawable(R.drawable.bg_rounded_blue))
                    .textColor(context.getColor(R.color.white))
                    .callBack(CallBackManager.GetCallBackItemActionItemManager(context))
                    .text("Detail")
                    .build());
            itemData.setTitle(itemDto.getItemName());
            itemData.setFields(attrs);
            itemData.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_smoke));

            if(itemDto.getIdResource() != null){
                itemData.setItemResource(ItemResource.builder()
                        .isLocal(false)
                        .idResource(itemDto.getIdResource())
                        .build());
            }
            else{
                itemData.setItemResource(ItemResource.builder()
                        .isLocal(true)
                        .idResource(R.drawable.ic_fan)
                        .build());
            }

            itemDataList.add(itemData);
        }
        return itemDataList;
    }
    public static List<ItemData> ConvertListSemesterDto2ItemData(@NonNull Context context, List<SemesterDto> semesterDtoList){
        List<ItemData> itemDataList = new ArrayList<>();
        for (SemesterDto semesterDto: semesterDtoList) {

            Map<String, String> attrs = new HashMap<>();
            attrs.put("Room price: ", NumberUtils.GetMoney(semesterDto.getRoomPrice()));
            attrs.put("Electric price: ", NumberUtils.GetMoney(semesterDto.getElectricPrice()));
            attrs.put("Water price: ", NumberUtils.GetMoney(semesterDto.getWaterPrice()));
            attrs.put("Start at: ", DateUtils.Date2String(semesterDto.getStartAt()));
            attrs.put("End at: ", DateUtils.Date2String(semesterDto.getEndAt()));

            ItemData itemData = new ItemData();
            itemData.setItemResource(null);
            itemData.setItemAction(ItemAction.builder()
                    .storedData(semesterDto.getId())
                    .background(context.getDrawable(R.drawable.bg_rounded_blue))
                    .textColor(context.getColor(R.color.white))
                    .callBack(CallBackManager.GetCallBackItemActionSemesterManager(context))
                    .text("Detail")
                    .build());
            itemData.setTitle(semesterDto.getSemesterName());
            itemData.setFields(attrs);
            itemData.setBackgroundDrawable(context.getDrawable(R.drawable.bg_rounded_smoke));

            itemDataList.add(itemData);
        }
        return itemDataList;
    }

}
