package com.hanghae.preorder.service;

import com.hanghae.preorder.dto.request.UserRequest;
import com.hanghae.preorder.dto.response.UserResponse;
import com.hanghae.preorder.entity.User;
import com.hanghae.preorder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse create(UserRequest userRequest) {
        //1. userRequest로 entity 생성
        User newUser = new User(
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getName(),
                userRequest.getProfileImage(),
                userRequest.getGreeting()
        );

        //2. 생성된 entity db에 저장
        User createdUser = userRepository.save(newUser);

        //3. entity를 userResponse 변환해서 반환
        return new UserResponse(
                createdUser.getId(),
                createdUser.getName()
        );
    }

    public UserResponse update(Long id, UserRequest userRequest) {
        //1. 받아온 id로 db에 user 존재 유무 확인
        User target = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        //2. 받아온 userRequest값으로 target 정보 수정
        target.update(userRequest);

        //3. 수정된 정보 db 저장
        User updated = userRepository.save(target);

        //4. 저장 후 userResponse로 변환해서 반환
        return new UserResponse(
                updated.getId(),
                updated.getName()
        );
    }
}
