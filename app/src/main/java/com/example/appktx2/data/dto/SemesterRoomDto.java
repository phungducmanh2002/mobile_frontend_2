package com.example.appktx2.data.dto;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterRoomDto {
    @SerializedName("id")
    Integer id;
    @SerializedName("idSemester")
    Integer idSemester;
    @SerializedName("idRoom")
    Integer idRoom;
    RoomDto room;
    SemesterDto semester;
}
