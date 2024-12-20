package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.CommentDto;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.UserRepository;
import com.mdd.back.Service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserRepository userRepository;

    /**
     * Ajoute un commentaire à un article.
     *
     * @param commentDto objet CommentDto contenant les informations du commentaire à ajouter
     * @return ResponseEntity contenant un message indiquant que le commentaire a été ajouté avec succès
     */
    // Ajouter un commentaire à un article
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody CommentDto commentDto, Principal principal) {
        try {
            User user = userRepository.findByEmail(principal.getName());
            commentDto.setUser_id(user.getId());

            commentService.addComment(commentDto);
            return ResponseEntity.ok("Comment added successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error adding comment: " + e.getMessage());
        }
    }


    /**
     * Retrieve comments for a given article.
     *
     * @param articleId the ID of the article for which to retrieve comments
     * @return ResponseEntity containing a list of CommentDto objects representing the comments for the article
     */
    // Récupérer les commentaires pour un article donné
    @GetMapping("/article/{articleId}")
    public ResponseEntity<Map<String,List<CommentDto>>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentDto> comments = commentService.getCommentsByArticle(articleId);
        Map<String, List<CommentDto>> commentDtoMap = new HashMap();
        commentDtoMap.put("comments", comments);
        return ResponseEntity.ok(commentDtoMap);
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
