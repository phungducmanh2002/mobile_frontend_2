package com.example.appktx2;

import com.example.appktx2.data.dto.UserDto;
import com.example.appktx2.net.RetrofitClient;

public class AppKtx {
    public static UserDto userDto = null;
    public static String jwtToken = null;
    public static void OnLoginSuccess(String token){
        AppKtx.jwtToken = token;
        RetrofitClient.GI().setToken(token);
    }
    public static void Reset(){
        jwtToken = null;
        userDto = null;
        RetrofitClient.Reset();
    }
}
