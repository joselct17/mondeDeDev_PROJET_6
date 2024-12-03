package com.mdd.back.Model.DTO;

import com.mdd.back.Model.User;
import lombok.Data;

@Data
public class RegisterUserDto {

    private String userName;
    private String email;
    private String password;

    public RegisterUserDto(User users) {
        this.userName = users.getUsername();
        this.email = users.getEmail();
        this.password = users.getPassword();
    }

}
