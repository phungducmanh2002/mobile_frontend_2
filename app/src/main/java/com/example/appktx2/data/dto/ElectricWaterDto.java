package com.example.appktx2.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricWaterDto {
    @SerializedName("id")
    Integer id;
    @SerializedName("idRoomSemester")
    Integer idRoomSemester;
    @SerializedName("electricNumber")
    Integer electricNumber;
    @SerializedName("waterNumber")
    Integer waterNumber;
    @SerializedName("month")
    Integer month;
    @SerializedName("year")
    Integer year;

}
