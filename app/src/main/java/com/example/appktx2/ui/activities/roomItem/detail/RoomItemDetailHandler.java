package com.example.appktx2.ui.activities.roomItem.detail;

import android.util.Log;
import android.widget.Toast;

import com.example.appktx2.data.apiResponse.UpdateAvatarRes;
import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.model.itemCommon.ItemResource;
import com.example.appktx2.net.RetrofitClient;
import com.example.appktx2.net.services.IRoomItemService;
import com.example.appktx2.ui.dialog.MyDialog;
import com.example.appktx2.ui.item.ItemImage;
import com.example.appktx2.utils.MapperUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomItemDetailHandler {
    ActivityRoomItemDetail view;
    IRoomItemService itemService = RetrofitClient.GI().getRetrofit().create(IRoomItemService.class);
    public RoomItemDetailHandler(ActivityRoomItemDetail view){
        this.view = view;

    }
    public void getItem(Integer idItem){
        itemService.getItemById(idItem).enqueue(new Callback<ItemDto>() {
            @Override
            public void onResponse(Call<ItemDto> call, Response<ItemDto> response) {
                if(response.isSuccessful()){
                    ItemDto itemDto = response.body();

                    ItemResource itemResource = new ItemResource();
                    itemResource.setIdResource(itemDto.getIdResource());
                    itemResource.setLocal(false);
                    itemResource.setCallBack(object -> {
                        updateImage();
                    });

                    view.binding.itemName.setText(itemDto.getItemName());
                    view.binding.itemQuantity.setValue(Integer.toString(itemDto.getQuantity()));
                    view.binding.itemImage.setItemResource(itemResource);
                }
                else{

                }
            }

            @Override
            public void onFailure(Call<ItemDto> call, Throwable t) {

            }
        });
    }
    public void updateImage(){
        ImagePicker.with(this.view).crop().compress(1024).maxResultSize(1080,1080).start();
    }
    public void onSelectAvatarResponse(byte[] imgData, Integer idItem){

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), imgData);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "newavatar.image", requestBody);

        itemService.updateImage(body, idItem).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else{
                    Toast.makeText(view, "Server error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }
    public void addQuantity(Integer idItem, Integer quatity){
        itemService.addQuantity(idItem, quatity).enqueue(new Callback<ItemDto>() {
            @Override
            public void onResponse(Call<ItemDto> call, Response<ItemDto> response) {
                if(response.isSuccessful()){
                    view.onResume();
                }
                else{
                    Toast.makeText(view, "SERVER ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ItemDto> call, Throwable t) {

            }
        });
    }
}
