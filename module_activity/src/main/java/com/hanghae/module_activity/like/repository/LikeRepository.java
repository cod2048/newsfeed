package com.hanghae.module_activity.like.repository;

import com.hanghae.module_activity.like.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Long> {
}
