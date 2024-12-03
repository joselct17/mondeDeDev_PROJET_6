package com.mdd.back.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuthenticationToken {

    private String token;
    private LocalDateTime expiration;

    public AuthenticationToken(String token, LocalDateTime expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
