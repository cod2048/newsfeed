package com.hanghae.module_activity.follow.entity;

import com.hanghae.module_activity.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following")
    private User following;

    public Follow() { }

    public Follow(User follower, User following){
        this.follower = follower;
        this.following = following;
    }
}
