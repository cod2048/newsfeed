package com.hanghae.preorder.comment.repository;

import com.hanghae.preorder.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
