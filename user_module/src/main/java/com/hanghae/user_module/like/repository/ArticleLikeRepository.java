package com.hanghae.preorder.like.repository;

import com.hanghae.preorder.like.entity.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {
}
