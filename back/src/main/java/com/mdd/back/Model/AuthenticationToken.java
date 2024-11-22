package com.mdd.back.Model;

import java.time.LocalDateTime;

public class AuthenticationToken {

    private String token;
    private LocalDateTime expiration;

    public AuthenticationToken(String token, LocalDateTime expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
