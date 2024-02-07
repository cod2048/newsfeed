package com.hanghae.module_activity.follow.repository;

import com.hanghae.module_activity.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByFollowerId(Long userId);
}
