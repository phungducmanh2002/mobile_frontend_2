package com.example.appktx2.net.services;

import com.example.appktx2.data.dto.UserDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface I_HomeApiService {
    @GET("/api/v1/user/my-info")
    Call<UserDto> getMyInfo();
}
