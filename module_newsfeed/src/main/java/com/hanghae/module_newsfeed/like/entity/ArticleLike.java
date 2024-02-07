package com.hanghae.module_newsfeed.like.entity;

import com.hanghae.module_newsfeed.article.entity.Article;
import com.hanghae.module_newsfeed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public ArticleLike() { }

    public ArticleLike(User user, Article article) {
        this.user = user;
        this.article = article;
    }
}
