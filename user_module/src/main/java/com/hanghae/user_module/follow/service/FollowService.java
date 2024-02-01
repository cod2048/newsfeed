package com.hanghae.preorder.follow.service;

import com.hanghae.preorder.activity.entity.Activity;
import com.hanghae.preorder.activity.repository.ActivityRepository;
import com.hanghae.preorder.follow.dto.request.FollowRequest;
import com.hanghae.preorder.follow.dto.response.FollowResponse;
import com.hanghae.preorder.follow.entity.Follow;
import com.hanghae.preorder.follow.repository.FollowRepository;
import com.hanghae.preorder.user.entity.User;
import com.hanghae.preorder.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final ActivityRepository activityRepository;

    public FollowService(UserRepository userRepository, FollowRepository followRepository, ActivityRepository activityRepository){
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.activityRepository = activityRepository;
    }

    public FollowResponse create(FollowRequest followRequest){
        User follower = userRepository.findById(followRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        User following = userRepository.findById(followRequest.getFollowing())
                .orElseThrow(() -> new IllegalArgumentException("can't find following user"));

        Follow newFollow = new Follow(
                follower,
                following
        );

        Follow createdFollow = followRepository.save(newFollow);

        // 팔로우에 대한 Activity 생성 및 저장
        Activity followActivity = new Activity(follower, Activity.ActivityType.FOLLOW, following.getId());
        activityRepository.save(followActivity);

        return new FollowResponse(
                createdFollow.getFollower().getId(),
                createdFollow.getFollowing().getId()
        );
    }
}
