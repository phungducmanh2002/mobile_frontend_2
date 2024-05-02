package com.example.appktx2.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemesterDto {
    @SerializedName("id")
    Integer id;
    @SerializedName("semesterName")
    String semesterName;
    @SerializedName("roomPrice")
    Integer roomPrice;
    @SerializedName("electricPrice")
    Integer electricPrice;
    @SerializedName("waterPrice")
    Integer waterPrice;
    @SerializedName("startAt")
    Date startAt;
    @SerializedName("endAt")
    Date endAt;
    List<RoomDto> rooms;
}
