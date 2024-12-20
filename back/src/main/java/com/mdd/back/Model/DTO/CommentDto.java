package com.mdd.back.Model.DTO;

import com.mdd.back.Model.Article;
import com.mdd.back.Model.Comment;
import com.mdd.back.Model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private Long id;
    private String content;

    private LocalDateTime datePosted;

    private Long user_id;

    private Long article_id;

}
