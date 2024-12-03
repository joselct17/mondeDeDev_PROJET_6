package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.CommentDto;
import com.mdd.back.Service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Ajouter un commentaire à un article
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto) {
        commentService.addComment(commentDto);
        return ResponseEntity.ok("Comment added successfully");
    }

    // Récupérer les commentaires pour un article donné
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDto>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentDto> comments = commentService.getCommentsByArticle(articleId);
        return ResponseEntity.ok(comments);
    }

    // Supprimer un commentaire (optionnel)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}
