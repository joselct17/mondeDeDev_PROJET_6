package com.mdd.back.Model.DTO;

import lombok.Data;

@Data
public class RegisterUserDto {

    private String userName;
    private String email;
    private String password;

}
