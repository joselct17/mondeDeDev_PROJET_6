package com.mdd.back.Service;


import com.mdd.back.Model.Article;
import com.mdd.back.Model.DTO.ArticleDto;
import com.mdd.back.Repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return articleRepository.findAll().stream()
                .map(article -> modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());
    }

    public ArticleDto getArticleById(Long id) {
        return modelMapper.map(articleRepository.findById(id).orElse(null), ArticleDto.class);
    }
}
