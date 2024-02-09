package com.hanghae.module_activity.like.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_num")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private LikeType type;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    public Likes() {

    }

    @Builder
    public Likes(Long userId, LikeType type, Long targetId) {
        this.userId = userId;
        this.type = type;
        this.targetId = targetId;
    }

    public enum LikeType {
        ARTICLE,
        COMMENT
    }
}
