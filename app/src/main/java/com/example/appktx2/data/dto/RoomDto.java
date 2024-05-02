package com.example.appktx2.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDto {
    @SerializedName("id")
    Integer id;
    @SerializedName("idRoomCollection")
    Integer idRoomCollection;
    @SerializedName("roomName")
    String roomName;
    @SerializedName("roomGender")
    Integer roomGender;
    @SerializedName("roomStatus")
    Integer roomStatus;
    @SerializedName("roomAcreage")
    Float roomAcreage;
    RoomCollectionDto roomCollection;
    List<ResourceDto> resources;
}
