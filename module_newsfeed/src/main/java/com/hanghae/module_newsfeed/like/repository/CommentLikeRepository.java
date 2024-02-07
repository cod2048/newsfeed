package com.hanghae.module_newsfeed.like.repository;

import com.hanghae.module_newsfeed.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
