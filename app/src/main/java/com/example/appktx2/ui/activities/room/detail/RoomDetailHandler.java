package com.example.appktx2.ui.activities.room.detail;

import android.util.Log;
import android.widget.Toast;

import com.example.appktx2.data.apiResponse.UpdateAvatarRes;
import com.example.appktx2.data.dto.RoomCollectionDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.interfaces.ICallBack;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomCollectionService;
import com.example.appktx2.net.services.IRoomService;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.ui.item.ItemImage;
import com.example.appktx2.utils.MapperUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetailHandler {
    ActivityRoomDetail view;
    IRoomService romService = RetrofitClient.GI().getRetrofit().create(IRoomService.class);
    IRoomCollectionService romCollectionService = RetrofitClient.GI().getRetrofit().create(IRoomCollectionService.class);
    public RoomDetailHandler(ActivityRoomDetail view){
        this.view = view;

    }
    public void getRoomDetail(Integer idRoom){
        romService.getRoomDetails(idRoom).enqueue(new Callback<RoomDto>() {
            @Override
            public void onResponse(Call<RoomDto> call, Response<RoomDto> response) {
                if(response.isSuccessful()){

                    RoomDto roomDto = response.body();

                    Log.d("TFGVJHBKNLMPI", response.body().toString());

                    view.binding.roomName.setText(roomDto.getRoomName());
                    view.binding.collectionName.setValue(roomDto.getRoomCollection().getRoomCollectionName());
                    view.binding.acreage.setValue(roomDto.getRoomAcreage().toString());
                    view.binding.gender.setValue(roomDto.getRoomGender() == 0 ? "Male" : "Female");
                    view.binding.status.setValue(roomDto.getRoomStatus() == 0 ? "Active" : "Disable");

                    view.binding.myImageListView.resetView();

                    for (int i = 0; i < roomDto.getResources().size(); i++) {
                        Integer resId = roomDto.getResources().get(i).getId();

                        ItemResource itemResource = ItemResource.builder()
                                .idResource(resId)
                                .isLocal(false)
                                .callBack(objects -> {
                                    try{
                                        ItemImage itemImage = (ItemImage) objects[0];
                                        deleteRoomImage(idRoom ,itemImage.getItemResource().getIdResource());
                                    }
                                    catch (Exception ex){
                                        Toast.makeText(view, ex.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .build();

                        ItemImage itemImage = new ItemImage(view);
                        itemImage.setItemResource(itemResource);

                        view.binding.myImageListView.addChildren(
                                itemImage
                        );
                    }
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
    public void deleteRoomImage(Integer idRoom, Integer idResource){
        romService.deleteRoomImage(idRoom, idResource).enqueue(new Callback<Object>() {
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
    }
    public void onSelectAvatarResponse(byte[] imgData, Integer idRoom){

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgData);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "newavatar.image", requestBody);

        romService.addRoomImage(body, idRoom).enqueue(new Callback<UpdateAvatarRes>() {
            @Override
            public void onResponse(Call<UpdateAvatarRes> call, Response<UpdateAvatarRes> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else{
                    Toast.makeText(view, "Server error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateAvatarRes> call, Throwable t) {
            }
        });
    }
}
