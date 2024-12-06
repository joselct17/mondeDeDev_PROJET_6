package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.ArticleDto;
import com.mdd.back.Service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Create a new article based on the provided ArticleDto.
     *
     * @param articleDto The ArticleDto object containing the title and content of the article to be created
     * @return ResponseEntity<String> indicating successful creation of the article
     */

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody ArticleDto articleDto) {
        log.info("Creating article: {}", articleDto);
        articleService.createArticle(articleDto);
        return ResponseEntity.ok("Article created successfully");
    }

    /**
     * Retrieves all articles from the database.
     *
     * @return ResponseEntity<List < ArticleDto>> containing a list of ArticleDto objects representing all articles
     */

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    /**
     * Retrieve an article by its ID.
     *
     * @param id The ID of the article to retrieve
     * @return ResponseEntity<ArticleDto> containing the article corresponding to the provided ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }
}
