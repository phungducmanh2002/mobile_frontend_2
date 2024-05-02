package com.example.appktx2.net.services;

import com.example.appktx2.data.dto.RoomCollectionDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IRoomCollectionService {
    @GET("/api/v1/room-collection")
    Call<List<RoomCollectionDto>> getAllRoomCollection();
}
