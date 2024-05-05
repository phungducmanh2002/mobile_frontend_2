package com.example.appktx2.ui.activities.roomCollection.manager;

import android.widget.Toast;

import com.example.appktx2.data.dto.RoomCollectionDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomCollectionService;
import com.example.appktx2.net.services.IRoomService;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.utils.MapperUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomCollectionManagerHandler {
    ActivityRoomCollectionManager view;
    IRoomCollectionService romCollectionService = RetrofitClient.GI().getRetrofit().create(IRoomCollectionService.class);
    public RoomCollectionManagerHandler(ActivityRoomCollectionManager view){
        this.view = view;

        view.binding.viewManager.setOnSearch((Object... objs) -> {});
        view.binding.viewManager.setOnAction((Object... objs) -> {
            MyDialog myDialog = new MyDialog(this.view);
            myDialog.show();
        });
    }
    public void getAllRoomCollection(){
        romCollectionService.getAllRoomCollection().enqueue(new Callback<List<RoomCollectionDto>>() {
            @Override
            public void onResponse(Call<List<RoomCollectionDto>> call, Response<List<RoomCollectionDto>> response) {
                if(response.isSuccessful()){
                    view.binding.viewManager.setItemDataList(MapperUtils.ConvertListRoomCollectionDto2ItemData(view,response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<RoomCollectionDto>> call, Throwable t) {

            }
        });
    }
    public void createRoomCollection(RoomCollectionDto roomCollectionDto){
        romCollectionService.createRoomCollection(roomCollectionDto).enqueue(new Callback<RoomCollectionDto>() {
            @Override
            public void onResponse(Call<RoomCollectionDto> call, Response<RoomCollectionDto> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else {
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoomCollectionDto> call, Throwable t) {

            }
        });
    }
}
