package com.hanghae.module_newsfeed.comment.repository;

import com.hanghae.module_newsfeed.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
