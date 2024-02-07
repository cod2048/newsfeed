package com.hanghae.module_activity.like.repository;

import com.hanghae.module_activity.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
