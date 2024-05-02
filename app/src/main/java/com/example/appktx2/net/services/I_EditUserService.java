package com.example.appktx2.net.services;

import com.example.appktx2.data.dto.UserDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface I_EditUserService {
    @PUT("/api/v1/user/edit")
    Call<UserDto> editUser(@Body UserDto userDto);
}
