package com.example.appktx2.net;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance;
    public static RetrofitClient GI(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return  instance;
    }
    public String baseUrl = "http://192.168.1.12:8080";
    private Retrofit retrofit = null;
    private RetrofitClient(){
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
    }
    public void setToken(String token){
        // Tạo header
        token = "Bearer " + token;
        Headers headers = new Headers.Builder().set("Authorization", token).build();
        // Setup http client
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            request = request.newBuilder().headers(headers).build();
            return chain.proceed(request);
        }).build();
        // rebuild retrofit
        retrofit = retrofit.newBuilder().client(okHttpClient).build();
    }
    public Retrofit getRetrofit(){
        return this.retrofit;
    }
    public static void Reset(){
        instance = null;
    }
}
