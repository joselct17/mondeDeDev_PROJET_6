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

    /**
     * Ajoute un commentaire à un article.
     *
     * @param commentDto objet CommentDto contenant les informations du commentaire à ajouter
     * @return ResponseEntity contenant un message indiquant que le commentaire a été ajouté avec succès
     */
    // Ajouter un commentaire à un article
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto) {
        commentService.addComment(commentDto);
        return ResponseEntity.ok("Comment added successfully");
    }

    /**
     * Retrieve comments for a given article.
     *
     * @param articleId the ID of the article for which to retrieve comments
     * @return ResponseEntity containing a list of CommentDto objects representing the comments for the article
     */
    // Récupérer les commentaires pour un article donné
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDto>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentDto> comments = commentService.getCommentsByArticle(articleId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Delete a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     * @return ResponseEntity indicating the success of the deletion operation
     */
    // Supprimer un commentaire (optionnel)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}
