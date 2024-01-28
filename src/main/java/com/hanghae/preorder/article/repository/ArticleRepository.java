package com.hanghae.preorder.article.repository;

import com.hanghae.preorder.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
