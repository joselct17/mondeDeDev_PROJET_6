package com.mdd.back.Service;


import com.mdd.back.Model.Article;
import com.mdd.back.Model.DTO.ArticleDto;
import com.mdd.back.Model.DTO.CommentResponseDto;
import com.mdd.back.Model.Theme;
import com.mdd.back.Model.User;
import com.mdd.back.Repository.ArticleRepository;
import com.mdd.back.Repository.ThemeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ThemeRepository themeRepository;


    public void createArticle(ArticleDto articleDto, User user) {
        Article article = new Article();
        article.setUser(user);
        article.setDatePosted(LocalDateTime.now());
        article.setName(articleDto.getName());
        article.setContent(articleDto.getContent());
        Theme theme = themeRepository.findByName(articleDto.getTheme());
        article.setTheme(theme);
        articleRepository.save(article);
    }

    /**
     * Retrieves all articles from the database.
     *
     * @return List<ArticleDto> containing all articles as DTOs
     */
    public List<ArticleDto> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(article -> {
                    ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);

                    articleDto.setAuthor(article.getUser().getUsername());
                    articleDto.setTheme(article.getTheme().getName());
                    // Mapper les commentaires
                    articleDto.setComments(
                            article.getComments().stream()
                                    .map(comment -> {
                                        CommentResponseDto commentDto = new CommentResponseDto();
                                        commentDto.setContent(comment.getContent());
                                        commentDto.setDatePosted(comment.getDatePosted());
                                        commentDto.setUserName(comment.getUser().getUsername());
                                        return commentDto;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return articleDto;
                })
                .collect(Collectors.toList());
    }


    /**
     * Retrieves an article by its ID.
     *
     * @param id The ID of the article to retrieve
     * @return ArticleDto representing the article with the provided ID, or null if not found
     */
    /**
     * Retrieves an article by its ID.
     *
     * @param id The ID of the article to retrieve
     * @return ArticleDto representing the article with the provided ID, or null if not found
     */
    public ArticleDto getArticleById(Long id) {
        return articleRepository.findById(id)
                .map(article -> {
                    ArticleDto articleDto = modelMapper.map(article, ArticleDto.class);

                    // Mapper les informations de l'auteur
                    articleDto.setAuthor(article.getUser().getUsername());

                    // Mapper le thème
                    articleDto.setTheme(article.getTheme().getName());

                    // Mapper les commentaires
                    articleDto.setComments(
                            article.getComments().stream()
                                    .map(comment -> {
                                        CommentResponseDto commentDto = new CommentResponseDto();
                                        commentDto.setContent(comment.getContent());
                                        commentDto.setDatePosted(comment.getDatePosted());
                                        commentDto.setUserName(comment.getUser().getUsername());
                                        return commentDto;
                                    })
                                    .collect(Collectors.toList())
                    );

                    return articleDto;
                })
                .orElse(null); // Retourner null si l'article n'existe pas
    }

}
