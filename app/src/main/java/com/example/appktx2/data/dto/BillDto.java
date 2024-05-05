package com.example.appktx2.data.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    @SerializedName("id")
    Integer id;
    @SerializedName("idElectricWater")
    Integer idElectricWater;
    @SerializedName("idRegis")
    Integer idRegis;
    @SerializedName("title")
    String title;
    @SerializedName("status")
    Boolean status;
    @SerializedName("createdAt")
    Date createdAt;
    @SerializedName("updatedAt")
    Date updatedAt;
    @SerializedName("email")
    String email;
    @SerializedName("roomName")
    String roomName;
    @SerializedName("electricNumber")
    Integer electricNumber;
    @SerializedName("waterNumber")
    Integer waterNumber;
    @SerializedName("month")
    Integer month;
    @SerializedName("year")
    Integer year;
    @SerializedName("electricPrice")
    Integer electricPrice;
    @SerializedName("waterPrice")
    Integer waterPrice;
    @SerializedName("roomPrice")
    Integer roomPrice;
}
