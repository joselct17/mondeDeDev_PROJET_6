package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.CommentDto;
import com.mdd.back.Model.DTO.CommentResponseDto;
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
    @PostMapping
    public ResponseEntity<Map<String, String>> addComment(@RequestBody CommentDto commentDto, Principal principal) {
        try {
            // Récupérer l'utilisateur connecté
            User user = userRepository.findByEmail(principal.getName());
            if (user == null) {
                throw new RuntimeException("Utilisateur non trouvé.");
            }

            // Associer l'utilisateur au commentaire
            commentDto.setUser_id(user.getId());

            // Ajouter le commentaire via le service
            commentService.addComment(commentDto);

            // Réponse en cas de succès
            Map<String, String> response = new HashMap<>();
            response.put("message", "Comment added successfully");
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            // Réponse en cas d'erreur
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error adding comment");
            errorResponse.put("details", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }



    /**
     * Retrieve comments for a given article.
     *
     * @param articleId the ID of the article for which to retrieve comments
     * @return ResponseEntity containing a list of CommentDto objects representing the comments for the article
     */
    @GetMapping("/article/{articleId}")
    public ResponseEntity<Map<String, List<CommentResponseDto>>> getCommentsByArticle(@PathVariable Long articleId) {
        List<CommentResponseDto> comments = commentService.getCommentsByArticle(articleId);
        Map<String, List<CommentResponseDto>> response = new HashMap<>();
        response.put("comments", comments);
        return ResponseEntity.ok(response);
    }


    /**
     * Delete a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     * @return ResponseEntity indicating the success of the deletion operation
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}
