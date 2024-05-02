package com.example.appktx2.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable, Cloneable {
    @SerializedName("id")
    Integer id;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("gender")
    Integer gender = 0;
    @SerializedName("idRole")
    Integer idRole = 0;
    @SerializedName("accountStatus")
    Integer accountStatus = 0;
    @SerializedName("idResource")
    Integer idResource = 0;

    public UserDto clone(){
        try{
            UserDto userDtoNew = (UserDto)super.clone();
            return userDtoNew;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
