package com.mdd.back.Service;

import com.mdd.back.Model.Article;
import com.mdd.back.Model.Comment;
import com.mdd.back.Model.DTO.CommentDto;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.ArticleRepository;
import com.mdd.back.Repository.CommentRepository;
import com.mdd.back.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // Ajouter un commentaire
    public void addComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        comment.setDatePosted(LocalDateTime.now());

        Article article = articleRepository.findById(commentDto.getArticle_id())
                .orElseThrow(() -> new RuntimeException("Article not found"));
        comment.setArticle(article);

        User user = userRepository.findById(commentDto.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);

        commentRepository.save(comment);
    }

    // Récupérer les commentaires par article
    public List<CommentDto> getCommentsByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    // Supprimer un commentaire
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found");
        }
        commentRepository.deleteById(commentId);
    }

}
