package com.hanghae.module_user.article.entity;

import com.hanghae.module_user.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    public Article() { }

    public Article(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }
}
