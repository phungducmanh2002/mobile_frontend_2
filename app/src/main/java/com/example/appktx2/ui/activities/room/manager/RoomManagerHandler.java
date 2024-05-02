package com.example.appktx2.ui.activities.room.manager;

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

public class RoomManagerHandler {
    ActivityRoomManager view;
    IRoomService romService = RetrofitClient.GI().getRetrofit().create(IRoomService.class);
    IRoomCollectionService romCollectionService = RetrofitClient.GI().getRetrofit().create(IRoomCollectionService.class);
    public RoomManagerHandler(ActivityRoomManager view){
        this.view = view;

        view.binding.viewManager.setOnSearch((Object... objs) -> {});
        view.binding.viewManager.setOnAction((Object... objs) -> {
            MyDialog myDialog = new MyDialog(this.view);
            myDialog.show();
        });
    }
    public void getAllRoom(){
        romService.getAllRoom().enqueue(new Callback<List<RoomDto>>() {
            @Override
            public void onResponse(Call<List<RoomDto>> call, Response<List<RoomDto>> response) {
                if(response.isSuccessful()){
                    view.binding.viewManager.setItemDataList(MapperUtils.ConvertListRoomDto2ItemData(view,response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<RoomDto>> call, Throwable t) {

            }
        });
    }
    public void createRoom(RoomDto roomDto){
        romService.createRoom(roomDto).enqueue(new Callback<RoomDto>() {
            @Override
            public void onResponse(Call<RoomDto> call, Response<RoomDto> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else {
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoomDto> call, Throwable t) {

            }
        });
    }
    public void getAllRoomCollection(ICallBack callBack){
        romCollectionService.getAllRoomCollection().enqueue(new Callback<List<RoomCollectionDto>>() {
            @Override
            public void onResponse(Call<List<RoomCollectionDto>> call, Response<List<RoomCollectionDto>> response) {
                if(response.isSuccessful()){
                    callBack.action(response.body());
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RoomCollectionDto>> call, Throwable t) {

            }
        });
    }
}
