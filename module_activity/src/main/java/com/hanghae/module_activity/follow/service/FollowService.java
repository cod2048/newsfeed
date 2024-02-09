package com.hanghae.module_activity.follow.service;

import com.hanghae.module_activity.client.UserClient;
import com.hanghae.module_activity.follow.dto.request.FollowRequest;
import com.hanghae.module_activity.follow.entity.Follow;
import com.hanghae.module_activity.follow.repository.FollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FollowService {
    private final FollowRepository followRepository;
    private final UserClient userClient;
//    private final ActivityRepository activityRepository;

    public FollowService(FollowRepository followRepository, UserClient userClient){
        this.followRepository = followRepository;
//        this.activityRepository = activityRepository;
        this.userClient = userClient;
    }

    public void create(FollowRequest followRequest){
        Long followerId = followRequest.getUserId();
        Long followingId = followRequest.getFollowing();

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
//        Activity followActivity = new Activity(follower, Activity.ActivityType.FOLLOW, following.getId());
//        activityRepository.save(followActivity);

    }
}
