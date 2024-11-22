package com.mdd.back.Model.DTO;


import lombok.Data;

@Data
public class UserDto {

    private String email;

    private String password;

    private String confirmPassword;

    private String username;
}
