package com.mdd.back.Repository;

import com.mdd.back.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Retrieves comments for a given article ID.
     *
     * @param articleId The ID of the article for which to retrieve comments
     * @return List<Comment> containing the comments for the article
     */
    List<Comment> findByArticleId(Long articleId);
}
