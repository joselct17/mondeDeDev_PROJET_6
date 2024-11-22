package com.mdd.back.Repository;

import com.mdd.back.Model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
