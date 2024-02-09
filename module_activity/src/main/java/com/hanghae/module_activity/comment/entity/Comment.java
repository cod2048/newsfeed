package com.hanghae.module_activity.comment.entity;

import com.hanghae.module_activity.article.entity.Article;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_num")
    private Long id;

    @Column(name = "user_num")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Comment() { }

    @Builder
    public Comment(Long userId, Article article, String content) {
        this.userId = userId;
        this.article = article;
        this.content = content;
    }

}
