package com.hanghae.module_newsfeed.activity.entity;

import com.hanghae.module_newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ActivityType type;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    public Activity() { }

    public Activity(User user, ActivityType type, Long targetId) {
        this.user = user;
        this.type = type;
        this.targetId = targetId;
    }

    public enum ActivityType {
        ARTICLE,
        COMMENT,
        LIKE,
        FOLLOW,
        ARTICLE_LIKE,
        COMMENT_LIKE
    }
}
