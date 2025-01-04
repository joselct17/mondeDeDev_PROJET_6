package com.mdd.back.Model.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long id;
    private String name;
    private String content;
    private String theme;
    private String author;
    public LocalDateTime datePosted;
    private List<CommentResponseDto> comments;
}
