package com.hanghae.preorder.follow.repository;

import com.hanghae.preorder.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
