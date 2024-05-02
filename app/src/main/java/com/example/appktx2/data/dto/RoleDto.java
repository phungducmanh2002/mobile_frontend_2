package com.example.appktx2.data.dto;

public class RoleDto {
    public static String GetRoleName(Integer roleId){
        String role = "";
        switch (roleId){
            case 1:{
                role = "ADMIN";
                break;
            }
            case 2: {
                role = "STAFF";
                break;
            }
            case 3:{
                role = "STUDENT";
                break;
            }
            default:{
                role = null;
                break;
            }
        }
        return role;
    }
}
