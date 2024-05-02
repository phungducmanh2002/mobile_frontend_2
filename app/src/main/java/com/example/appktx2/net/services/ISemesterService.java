package com.example.appktx2.net.services;

import com.example.appktx2.data.dto.RoomDto;
import com.example.appktx2.data.dto.SemesterDto;
import com.example.appktx2.data.dto.SemesterRoomDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ISemesterService {
    @POST("/api/v1/semester/create")
    Call<SemesterDto> createSemester(@Body SemesterDto semesterDto);

    @POST("/api/v1/semester/add-room")
    Call<SemesterRoomDto> semesterAddRoom(@Body SemesterRoomDto semesterRoomDto);

    @DELETE("/api/v1/semester/remove-room/{idSemester}/{idRoom}")
    Call<Object> semesterRemoveRoom(@Path("idSemester") Integer idSemester, @Path("idRoom") Integer idRoom);

    @GET("/api/v1/semester")
    Call<List<SemesterDto>> getAllSemester();

    @GET("/api/v1/semester/{semesterId}")
    Call<SemesterDto> getDetailsById(@Path("semesterId") Integer semesterId);

    @GET("/api/v1/semester/all-room-added/{semesterId}")
    Call<List<RoomDto>> getAllRoomAdded(@Path("semesterId") Integer semesterId);

    @GET("/api/v1/semester/all-room-not-added/{semesterId}")
    Call<List<RoomDto>> getAllRoomNotAdded(@Path("semesterId") Integer semesterId);
}
