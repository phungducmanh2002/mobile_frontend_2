package com.example.appktx2.net.services;

import com.example.appktx2.data.dto.LoginResDto;
import com.example.appktx2.data.model.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILoginService {
    @POST("/api/v1/user/login")
    Call<LoginResDto> login(@Body Account account);
}
