package com.hanghae.module_activity.article.repository;

import com.hanghae.module_activity.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
