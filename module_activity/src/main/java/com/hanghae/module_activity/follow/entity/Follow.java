package com.hanghae.module_activity.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follower_id")
    private Long followerId;

    @Column(name = "following_id")
    private Long followingId;

    @Column(name = "status")
    private Boolean status = true;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Follow() { }

    public Follow(Long follower, Long following){
        this.followerId = follower;
        this.followingId = following;
    }

    // 팔로우 해제 메소드
    public void deactivate() {
        this.status = false;
    }

    // 팔로우 상태 변경 시 시간 업데이트
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }
}
