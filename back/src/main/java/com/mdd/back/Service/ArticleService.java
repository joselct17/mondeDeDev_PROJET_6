package com.mdd.back.Service;


import com.mdd.back.Model.Article;
import com.mdd.back.Model.DTO.ArticleDto;
import com.mdd.back.Repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;


    public void createArticle(ArticleDto articleDto) {
        Article article = new Article();
        article.setName(articleDto.getTitle());
        article.setContent(articleDto.getContent());
    }

    public List<ArticleDto> getAllArticles() {
        // Récupérer tous les articles
        Iterable<Article> articlesIterable = articleRepository.findAll();

        // Convertir l'Iterable en List
        List<Article> articles = StreamSupport.stream(articlesIterable.spliterator(), false)
                .collect(Collectors.toList());

        // Vérifier si la liste est vide
        if (articles.isEmpty()) {
            return Collections.emptyList();
        }

        // Mapper les entités en DTOs
        return articles.stream()
                .map(article -> modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());
    }



    public ArticleDto getArticleById(Long id) {
        return modelMapper.map(articleRepository.findById(id).orElse(null), ArticleDto.class);
    }
}
