package com.hanghae.module_activity.like.entity;

import com.hanghae.module_activity.comment.entity.Comment;
import com.hanghae.module_activity.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentLike() { }

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
    }
}
