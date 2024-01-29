package com.hanghae.preorder.follow.service;

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

    public FollowService(UserRepository userRepository, FollowRepository followRepository){
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public FollowResponse create(FollowRequest followRequest){
        User userId = userRepository.findById(followRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        User following = userRepository.findById(followRequest.getFollowing())
                .orElseThrow(() -> new IllegalArgumentException("can't find user2"));

        Follow newFollow = new Follow(
                userId,
                following
        );

        Follow createdFollow = followRepository.save(newFollow);

        return new FollowResponse(
                createdFollow.getFollower().getId(),
                createdFollow.getFollowing().getId()
        );
    }
}
