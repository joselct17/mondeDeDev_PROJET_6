package com.mdd.back.Model.DTO;


import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
    private String userName;
}
