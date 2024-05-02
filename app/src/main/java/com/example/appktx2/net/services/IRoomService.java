package com.example.appktx2.net.services;

import com.example.appktx2.data.apiResponse.UpdateAvatarRes;
import com.example.appktx2.data.dto.ItemAddedDto;
import com.example.appktx2.data.dto.ItemDto;
import com.example.appktx2.data.dto.RoomDto;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IRoomService {

    @DELETE("/api/v1/room/item/{idRoom}/{idItem}")
    Call<Object> deleteRoomItem(@Path("idRoom") Integer idRoom,@Path("idItem") Integer idItem);
    @PUT("/api/v1/room/add-item/{idRoom}/{idItem}/{quantity}")
    Call<Object> updateRoomItemQuantity(@Path("idRoom") Integer idRoom,@Path("idItem") Integer idItem,@Path("quantity") Integer quantity);
    @GET("/api/v1/room/item-added/{idRoom}")
    Call<List<ItemAddedDto>> getAllItemAdded(@Path("idRoom") Integer idRoom);

    @GET("/api/v1/room/item-not-added/{idRoom}")
    Call<List<ItemDto>> getAllItemNotAdded(@Path("idRoom") Integer idRoom);
    @GET("/api/v1/room")
    Call<List<RoomDto>> getAllRoom();

    @DELETE("/api/v1/room/image/{idRoom}/{idResource}")
    Call<Object> deleteRoomImage(@Path("idRoom") Integer idRoom, @Path("idResource") Integer idResource);

    @GET("/api/v1/room/images/{idRoom}")
    Call<List<Integer>> getAllImagesId(@Path("idRoom") Integer idRoom);

    @GET("/api/v1/room/{idRoom}")
    Call<RoomDto> getRoomDetails(@Path("idRoom") Integer idRoom);

    @POST("/api/v1/room/create")
    Call<RoomDto> createRoom(@Body RoomDto romDto);

    @POST("/api/v1/room/add-image/{idRoom}")
    @Multipart
    Call<UpdateAvatarRes> addRoomImage(@Part MultipartBody.Part file, @Path("idRoom") Integer idRoom);

}
