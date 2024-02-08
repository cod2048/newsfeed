package com.hanghae.module_activity.like.entity;

import com.hanghae.module_activity.article.entity.Article;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public ArticleLike() { }

    public ArticleLike(User user, Article article) {
        this.user = user;
        this.article = article;
    }
}
