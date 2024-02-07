package com.hanghae.module_newsfeed.like.service;

import com.hanghae.module_newsfeed.activity.entity.Activity;
import com.hanghae.module_newsfeed.activity.repository.ActivityRepository;
import com.hanghae.module_newsfeed.comment.entity.Comment;
import com.hanghae.module_newsfeed.comment.repository.CommentRepository;
import com.hanghae.module_newsfeed.like.dto.likeRequest.LikeRequest;
import com.hanghae.module_newsfeed.like.entity.CommentLike;
import com.hanghae.module_newsfeed.like.repository.CommentLikeRepository;
import com.hanghae.module_newsfeed.user.entity.User;
import com.hanghae.module_newsfeed.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ActivityRepository activityRepository;

    public CommentLikeService(UserRepository userRepository, CommentRepository commentRepository, CommentLikeRepository commentLikeRepository, ActivityRepository activityRepository){
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
        this.activityRepository = activityRepository;
    }

    public void create(Long id, LikeRequest likeRequest){
        User user = userRepository.findById(likeRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("can't find comment"));

        CommentLike newLike = new CommentLike(
                user,
                comment
        );

        commentLikeRepository.save(newLike);

        // 코멘트 좋아요에 대한 Activity 생성 및 저장
        Activity likeActivity = new Activity(user, Activity.ActivityType.COMMENT_LIKE, comment.getId());
        activityRepository.save(likeActivity);
    }
}
