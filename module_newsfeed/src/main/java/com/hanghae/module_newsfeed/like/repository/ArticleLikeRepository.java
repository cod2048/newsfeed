package com.hanghae.module_newsfeed.like.repository;

import com.hanghae.module_newsfeed.like.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
}
