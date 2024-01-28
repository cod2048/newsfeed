package com.hanghae.preorder.service;

import com.hanghae.preorder.dto.request.UserRequest;
import com.hanghae.preorder.dto.response.UserResponse;
import com.hanghae.preorder.entity.User;
import com.hanghae.preorder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
