package com.mdd.back.Model.DTO;

import com.mdd.back.Model.Article;
import com.mdd.back.Model.Comment;
import com.mdd.back.Model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private String content;

    private LocalDateTime datePosted;

    private Long user_id;

    private Long article_id;

    public CommentDto(Comment comment, Article article, User user) {
        this.content = comment.getContent();
        this.datePosted = LocalDateTime.now();
        this.article_id = article.getId();
        this.user_id = user.getId();
    }
}
