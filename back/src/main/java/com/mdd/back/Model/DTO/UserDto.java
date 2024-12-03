package com.mdd.back.Model.DTO;


import com.mdd.back.Model.User;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDto {

    private String email;
    private String password;
    private String username;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public UserDto(User user) {
        email = user.getEmail();
        password = user.getPassword();
        username = user.getUsername();
        created_at = user.getCreatedAt();
        updated_at = user.getUpdatedAt();
    }
}
