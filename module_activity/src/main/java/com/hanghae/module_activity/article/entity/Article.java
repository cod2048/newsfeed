package com.hanghae.module_activity.article.entity;

import com.hanghae.module_activity.article.dto.request.CreateArticleRequest;
import com.hanghae.module_activity.article.dto.request.UpdateArticleRequest;
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
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_num")
    private Long id;

    @Column(name = "user_num")
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

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

    public Article() { }

    @Builder
    public Article(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public static Article create(CreateArticleRequest createArticleRequest){
        return Article.builder()
                .userId(createArticleRequest.getUserId())
                .title(createArticleRequest.getTitle())
                .content(createArticleRequest.getContent())
                .build();
    }

    public void updateArticle(UpdateArticleRequest updateArticleRequest) {
        this.title = updateArticleRequest.getTitle();
        this.content = updateArticleRequest.getContent();
    }

    public void deleteArticle(Long articleId) {
        this.deletedAt = LocalDateTime.now();
    }

}
