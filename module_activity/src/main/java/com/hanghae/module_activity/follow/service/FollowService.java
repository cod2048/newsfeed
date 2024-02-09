package com.hanghae.module_activity.follow.service;

import com.hanghae.module_activity.client.NewsfeedClient;
import com.hanghae.module_activity.client.NewsfeedClientRequest;
import com.hanghae.module_activity.client.UserClient;
import com.hanghae.module_activity.follow.dto.request.FollowRequest;
import com.hanghae.module_activity.follow.entity.Follow;
import com.hanghae.module_activity.follow.repository.FollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;
    private final UserClient userClient;
    private final NewsfeedClient newsfeedClient;

    public FollowService(FollowRepository followRepository, UserClient userClient, NewsfeedClient newsfeedClient){
        this.followRepository = followRepository;
        this.userClient = userClient;
        this.newsfeedClient = newsfeedClient;
    }

    public void create(FollowRequest followRequest){
        Long followerId = followRequest.getUserId();
        Long followingId = followRequest.getFollowing();
        String type = "FOLLOW";

        if (!userClient.checkUserExists(followerId)) {
            throw new IllegalArgumentException("follower user not exists");
        }

        if (!userClient.checkUserExists(followingId)) {
            throw new IllegalArgumentException("following user not exists");
        }

        log.info("FollowService 진입");
        Follow newFollow = Follow.builder()
                        .follower(followerId)
                        .following(followingId)
                        .build();
        log.info("newFollow 생성");
        followRepository.save(newFollow);
        log.info("newFollow 저장");


//        팔로우에 대한 Activity 생성 및 저장
        NewsfeedClientRequest newsfeedClientRequest = new NewsfeedClientRequest(
                followerId,
                type,
                followingId
        );

        newsfeedClient.create(newsfeedClientRequest);

    }

    public List<Long> findByFollowingId(Long principalId) {
        return followRepository.findFollowing(principalId).stream()
                .map(Follow::getFollowingId)
                .toList();
    }

    public List<Long> findByFollowerId(Long principalId) {
        return followRepository.findFollower(principalId).stream()
                .map(Follow::getFollowerId)
                .toList();
    }
}
