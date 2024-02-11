package com.hanghae.module_newsfeed.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Newsfeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_num")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActivityType type;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public Newsfeed() { }

    @Builder
    public Newsfeed(Long userId, ActivityType type, Long targetId) {
        this.userId = userId;
        this.type = type;
        this.targetId = targetId;
    }

    public enum ActivityType {
        ARTICLE,
        COMMENT,
        FOLLOW,
        ARTICLE_LIKE,
        COMMENT_LIKE
    }
}
