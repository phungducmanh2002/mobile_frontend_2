package com.example.appktx2.ui.activities.room.detail.roomItemList;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.appktx2.R;
import com.example.appktx2.data.apiResponse.UpdateAvatarRes;
import com.example.appktx2.data.dto.ItemAddedDto;
import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.model.itemCommon.ItemAction;
import com.example.appktx2.data.model.itemCommon.ItemDataManyAction;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomCollectionService;
import com.example.appktx2.net.services.IRoomService;
import com.example.appktx2.ui.activities.roomItem.detail.ActivityRoomItemDetail;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.ui.item.ItemImage;
import com.example.appktx2.ui.item.ItemManyAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetailItemManagerHandler {
    ActivityRoomDetailItemManager view;
    IRoomService roomService = RetrofitClient.GI().getRetrofit().create(IRoomService.class);
    IRoomCollectionService romCollectionService = RetrofitClient.GI().getRetrofit().create(IRoomCollectionService.class);
    public RoomDetailItemManagerHandler(ActivityRoomDetailItemManager view){
        this.view = view;
    }
    public void getAllItemAdded(Integer idRoom){
        roomService.getAllItemAdded(idRoom).enqueue(new Callback<List<ItemAddedDto>>() {
            @Override
            public void onResponse(Call<List<ItemAddedDto>> call, Response<List<ItemAddedDto>> response) {
                if(response.isSuccessful()){
                    view.binding.myListView.resetView();
                    for (ItemAddedDto itemAddedDto: response.body()) {

                        ItemManyAction itemManyAction = new ItemManyAction(view);
                        itemManyAction.setItemDataManyAction(ItemAdded2ItemDataManyAction(itemAddedDto, idRoom));

                        view.binding.myListView.addChildren(itemManyAction);
                    }
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ItemAddedDto>> call, Throwable t) {

            }
        });
    }
    public void getAllItemNoAdded(Integer idRoom){
        roomService.getAllItemNotAdded(idRoom).enqueue(new Callback<List<ItemDto>>() {
            @Override
            public void onResponse(Call<List<ItemDto>> call, Response<List<ItemDto>> response) {
                if(response.isSuccessful()){
                    view.binding.myListView.resetView();
                    for (ItemDto itemAddedDto: response.body()) {

                        ItemManyAction itemManyAction = new ItemManyAction(view);
                        itemManyAction.setItemDataManyAction(ItemNotAdded2ItemDataManyAction(itemAddedDto, idRoom));

                        view.binding.myListView.addChildren(itemManyAction);
                    }
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ItemDto>> call, Throwable t) {

            }
        });
    }
    private ItemDataManyAction ItemAdded2ItemDataManyAction(ItemAddedDto itemAddedDto, Integer idRoom){
        List<ItemAction> itemActionList = new ArrayList<>();
        itemActionList.add(CreateItemActionAdd(itemAddedDto.getId(), idRoom));
        itemActionList.add(CreateItemActionSub(itemAddedDto.getId(), idRoom));
        itemActionList.add(CreateItemActionRemove(itemAddedDto.getId(), idRoom));

        Map<String, String> attrs = new HashMap<>();
        attrs.put("Total: ", Integer.toString(itemAddedDto.getQuantity()));
        attrs.put("Quantity: ", Integer.toString(itemAddedDto.getQuantityInRoom()));
        attrs.put("Quantity left: ", Integer.toString(itemAddedDto.getQuantityLeft()));

        ItemDataManyAction itemDataManyAction = new ItemDataManyAction();
        itemDataManyAction.setTitle(itemAddedDto.getItemName());

        if(itemAddedDto.getIdResource() != null){
            itemDataManyAction.setItemResource(
                    ItemResource.GetItemResourceResServer(itemAddedDto.getIdResource())
            );
        }
        else{
            itemDataManyAction.setItemResource(ItemResource.builder()
                    .isLocal(true)
                    .idResource(R.drawable.ic_fan)
                    .build());
        }
        itemDataManyAction.setItemActionList(itemActionList);
        itemDataManyAction.setFields(attrs);

        return  itemDataManyAction;
    }
    private ItemDataManyAction ItemNotAdded2ItemDataManyAction(ItemDto itemDto, Integer idRoom){
        List<ItemAction> itemActionList = new ArrayList<>();
        itemActionList.add(CreateItemActionAdd(itemDto.getId(), idRoom));
        itemActionList.add(CreateItemActionDetail(itemDto.getId(), idRoom));

        Map<String, String> attrs = new HashMap<>();
        attrs.put("Total: ", Integer.toString(itemDto.getQuantity()));
        attrs.put("Quantity left: ", Integer.toString(itemDto.getQuantityLeft()));

        ItemDataManyAction itemDataManyAction = new ItemDataManyAction();
        itemDataManyAction.setTitle(itemDto.getItemName());

        if(itemDto.getIdResource() != null){
            itemDataManyAction.setItemResource(
                    ItemResource.GetItemResourceResServer(itemDto.getIdResource())
            );
        }
        else{
            itemDataManyAction.setItemResource(ItemResource.builder()
                    .isLocal(true)
                    .idResource(R.drawable.ic_fan)
                    .build());
        }
        itemDataManyAction.setItemActionList(itemActionList);
        itemDataManyAction.setFields(attrs);

        return  itemDataManyAction;
    }
    public ItemAction CreateItemActionAdd(Integer idItem, Integer idRoom){
        return ItemAction.builder()
                .background(view.getDrawable(R.drawable.bg_rounded_blue))
                .text("ADD")
                .textColor(view.getColor(R.color.white))
                .storedData(idItem)
                .callBack(objects -> {
//                    Toast.makeText(view, "ADD: " + idRoom.toString() + "______" + idItem.toString(), Toast.LENGTH_SHORT).show();
                    MyDialog dialog = MyDialog.CreateUpdateItemQuantityDialog(view, objects1 -> {
                        Integer quantity = (Integer)objects1[0];
//                        Toast.makeText(view, "QUANITYT: " + quantity.toString(), Toast.LENGTH_SHORT).show();
                        roomService.updateRoomItemQuantity(idRoom, idItem, quantity).enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if(response.isSuccessful()){
                                        view.onResume();
                                }
                                else{
                                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });
                    });
                    dialog.show();
                })
                .build();
    }
    public ItemAction CreateItemActionSub(Integer idItem, Integer idRoom){
        return ItemAction.builder()
                .background(view.getDrawable(R.drawable.bg_rounded_orange))
                .text("SUB")
                .textColor(view.getColor(R.color.white))
                .storedData(idItem)
                .callBack(objects -> {
//                    Toast.makeText(view, "ADD: " + idRoom.toString() + "______" + idItem.toString(), Toast.LENGTH_SHORT).show();
                    MyDialog dialog = MyDialog.CreateUpdateItemQuantityDialog(view, objects1 -> {
                        Integer quantity = (Integer)objects1[0];
//                        Toast.makeText(view, "QUANITYT: " + quantity.toString(), Toast.LENGTH_SHORT).show();
                        roomService.updateRoomItemQuantity(idRoom, idItem, -quantity).enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if(response.isSuccessful()){
                                    view.onResume();
                                }
                                else{
                                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });
                    });
                    dialog.show();
                })
                .build();
    }
    public ItemAction CreateItemActionRemove(Integer idItem, Integer idRoom){
        return ItemAction.builder()
                .background(view.getDrawable(R.drawable.bg_rounded_red))
                .text("REMOVE")
                .textColor(view.getColor(R.color.white))
                .storedData(idItem)
                .callBack(objects -> {
//                    Toast.makeText(view, "REMOVE: " + idRoom.toString() + "______" + idItem.toString(), Toast.LENGTH_SHORT).show();
                        roomService.deleteRoomItem(idRoom, idItem).enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                if(response.isSuccessful()){
                                        view.onResume();
                                }
                                else{
                                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {

                            }
                        });
                })
                .build();
    }
    public ItemAction CreateItemActionDetail(Integer idItem, Integer idRoom){
        return ItemAction.builder()
                .background(view.getDrawable(R.drawable.bg_rounded_blue))
                .text("Detail")
                .textColor(view.getColor(R.color.white))
                .storedData(idItem)
                .callBack(objects -> {
                    Intent intentItemDetail = new Intent(view, ActivityRoomItemDetail.class);
                    intentItemDetail.putExtra("idItem", idItem);
                    view.startActivity(intentItemDetail);
//                    Toast.makeText(view, "REMOVE: " + idRoom.toString() + "______" + idItem.toString(), Toast.LENGTH_SHORT).show();
                })
                .build();
    }

}
