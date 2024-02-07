package com.hanghae.module_user.like.repository;

import com.hanghae.module_user.like.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
}
