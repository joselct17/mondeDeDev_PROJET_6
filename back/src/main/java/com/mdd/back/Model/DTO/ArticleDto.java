package com.mdd.back.Model.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String name;
    private String content;
    private String theme;
    private String author;
    private List<CommentResponseDto> comments;

}
