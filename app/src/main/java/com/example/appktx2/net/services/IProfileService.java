package com.example.appktx2.net.services;

import com.example.appktx2.data.apiResponse.UpdateAvatarRes;
import com.example.appktx2.data.dto.ResourceDto;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IProfileService {
    @POST("/api/v1/res/create")
    @Multipart
    Call<Object> uploadImage(@Part MultipartBody.Part file);

    @POST("/api/v1/user/avatar")
    @Multipart
    Call<UpdateAvatarRes> updateAvatar(@Part MultipartBody.Part file);

    @GET("/api/v1/res/bytes/{resourceId}")
    Call<ResourceDto> getAvatar(@Path("resourceId") Integer resourceId);
}
