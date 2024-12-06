package com.mdd.back.Model.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscriberDto {

    private String email;

    public SubscriberDto(String email) {
        this.email = email;
    }
}
