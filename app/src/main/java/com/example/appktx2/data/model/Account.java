package com.example.appktx2.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
}
