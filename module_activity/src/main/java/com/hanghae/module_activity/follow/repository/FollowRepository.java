package com.hanghae.module_activity.follow.repository;

import com.hanghae.module_activity.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 특정 사용자가 팔로우하는 사람들의 목록 조회
    @Query("SELECT f FROM Follow f WHERE f.followerId = :userId AND f.status = true")
    List<Follow> findFollowing(Long userId);

    // 특정 사용자를 팔로우하는 사람들의 목록 조회
    @Query("SELECT f FROM Follow f WHERE f.followingId = :userId AND f.status = true")
    List<Follow> findFollower(Long userId);
}
