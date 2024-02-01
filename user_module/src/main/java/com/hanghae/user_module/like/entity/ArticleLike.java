package com.hanghae.preorder.like.entity;

import com.hanghae.preorder.article.entity.Article;
import com.hanghae.preorder.user.entity.User;
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
