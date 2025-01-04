package com.mdd.back.Model.DTO;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponseDto {

    private String content;
    private LocalDateTime datePosted;
    private String userName;


}
