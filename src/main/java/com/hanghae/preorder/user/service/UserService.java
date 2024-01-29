package com.hanghae.preorder.user.service;

import com.hanghae.preorder.email.service.EmailService;
import com.hanghae.preorder.redis.service.RedisService;
import com.hanghae.preorder.user.dto.request.UserRequest;
import com.hanghae.preorder.user.dto.request.VerificationRequest;
import com.hanghae.preorder.user.dto.response.UserResponse;
import com.hanghae.preorder.user.entity.User;
import com.hanghae.preorder.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.juli.VerbatimFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final EmailService emailService;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private Long timeout;

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

        //3. 인증 메일 전송

        //4. entity를 userResponse 변환해서 반환
        return new UserResponse(
                createdUser.getId(),
                createdUser.getName()
        );
    }

    public void verifyEmail(String email){

        //1. 가입 요청 들어오면 이메일전송(이 안에 코드 생성까지 있음)
        String verificationCode = generateRandomCode();
        log.info(verificationCode);
        emailService.sendEmail(email, verificationCode);
        //2. 레디스에 위에 쓴 이메일이랑 코드 저장(setValue)
        redisService.setValue(email, verificationCode, timeout, TimeUnit.MILLISECONDS);

    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
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
