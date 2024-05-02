package com.example.appktx2.ui.activities.roomItem.manager;

import android.widget.Toast;

import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomItemService;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.utils.MapperUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomItemManagerHandler {
    ActivityRoomItemManager view;
    IRoomItemService itemService = RetrofitClient.GI().getRetrofit().create(IRoomItemService.class);
    public RoomItemManagerHandler(ActivityRoomItemManager view){
        this.view = view;

        view.binding.viewManager.setOnSearch((Object... objs) -> {});
        view.binding.viewManager.setOnAction((Object... objs) -> {
            MyDialog myDialog = new MyDialog(this.view);
            myDialog.show();
        });
    }
    public void getAllRoomItem(){
        itemService.getAllItem().enqueue(new Callback<List<ItemDto>>() {
            @Override
            public void onResponse(Call<List<ItemDto>> call, Response<List<ItemDto>> response) {
                if(response.isSuccessful()){
                    view.binding.viewManager.setItemDataList(MapperUtils.ConvertListItemDto2ItemData(view,response.body()));
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
    public void createItem(ItemDto itemDto){
        itemService.createItem(itemDto).enqueue(new Callback<RoomDto>() {
            @Override
            public void onResponse(Call<RoomDto> call, Response<RoomDto> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoomDto> call, Throwable t) {

            }
        });
    }
}
