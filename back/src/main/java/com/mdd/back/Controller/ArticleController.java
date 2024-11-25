package com.mdd.back.Controller;


import com.mdd.back.Model.DTO.ArticleDto;
import com.mdd.back.Service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody ArticleDto articleDto) {
        articleService.createArticle(articleDto);
        return ResponseEntity.ok("Article created successfully");
    }

    @GetMapping
    public ResponseEntity<List<ArticleDto>> getArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getArticleById(id));
    }
}
