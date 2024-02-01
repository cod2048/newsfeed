package com.hanghae.preorder.like.service;

import com.hanghae.preorder.activity.entity.Activity;
import com.hanghae.preorder.activity.repository.ActivityRepository;
import com.hanghae.preorder.comment.entity.Comment;
import com.hanghae.preorder.comment.repository.CommentRepository;
import com.hanghae.preorder.like.dto.likeRequest.LikeRequest;
import com.hanghae.preorder.like.entity.CommentLike;
import com.hanghae.preorder.like.repository.CommentLikeRepository;
import com.hanghae.preorder.user.entity.User;
import com.hanghae.preorder.user.repository.UserRepository;
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
