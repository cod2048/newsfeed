package com.hanghae.module_user.user.service;

import com.hanghae.module_user.email.service.EmailService;
import com.hanghae.module_user.redis.service.RedisService;
import com.hanghae.module_user.security.jwt.JwtTokenProvider;
import com.hanghae.module_user.security.jwt.TokenType;
import com.hanghae.module_user.user.dto.request.LoginRequest;
import com.hanghae.module_user.user.dto.request.CreateUserRequest;
import com.hanghae.module_user.user.dto.request.UpdatePasswordRequest;
import com.hanghae.module_user.user.dto.request.UpdateUserRequest;
import com.hanghae.module_user.user.dto.response.LoginResponse;
import com.hanghae.module_user.user.dto.response.CreateUserResponse;
import com.hanghae.module_user.user.dto.response.UpdateUserResponse;
import com.hanghae.module_user.user.entity.User;
import com.hanghae.module_user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.mail.auth-code-expiration-millis}")
    private Long timeout;

    // TODO : s3연결 후 이미지 url 저장

    /**
     * 회원가입
     */
    @Transactional
    public CreateUserResponse create(CreateUserRequest createUserRequest) {
        // 이메일 중복체크
        if (checkEmailDuplication(createUserRequest.getEmail())) {
            throw new IllegalArgumentException("already exist email");
        }

        //필수 요소확인 : 이름, 프로필 이미지, 인사말
        if (createUserRequest.getName() == null || createUserRequest.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }
        if (createUserRequest.getProfileImage() == null || createUserRequest.getProfileImage().trim().isEmpty()) {
            throw new IllegalArgumentException("profile image is empty");
        }
        if (createUserRequest.getGreeting() == null || createUserRequest.getGreeting().trim().isEmpty()) {
            throw new IllegalArgumentException("greeting is empty");
        }

        //인증코드 확인
        String verificationCode = createUserRequest.getVerificationCode();
        String userEmail = createUserRequest.getEmail();

        if (isVerify(userEmail, verificationCode)) {
            String encodedPassword = bCryptPasswordEncoder.encode(createUserRequest.getPassword());
            //1. userRequest로 entity 생성
            User newUser = User.create(createUserRequest, encodedPassword);

            //2. 생성된 entity db에 저장
            User createdUser = userRepository.save(newUser);

            //3. entity를 userResponse 변환해서 반환
            return new CreateUserResponse(
                    createdUser.getId(),
                    createdUser.getEmail()
            );
        } else {
            throw new IllegalStateException("verification code not match");
        }

    }

    public boolean checkEmailDuplication(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean isVerify(String userEmail, String requestCode) {
        return redisService.compareValue(userEmail, requestCode);
    }

    public void verifyEmail(String email) {

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

    public LoginResponse login(LoginRequest loginRequest) {

        //1. request의 email이랑 password랑 db의 email password 검사
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Optional<User> loginUser = userRepository.findByEmail(email);

        if (!loginUser.isPresent()) {
            throw new IllegalArgumentException("user not exist");
        }

        User user = loginUser.get();
        String userName = user.getName();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("password not match");
        }

//        log.info("인코딩 패스워드 검증 성공");
        String accessToken = jwtTokenProvider.generate(email, userName, TokenType.ACCESS);
        String refreshToken = jwtTokenProvider.generate(email, userName, TokenType.REFRESH);

        redisService.saveRefreshToken(email, refreshToken, jwtTokenProvider.getExpiredTime(refreshToken, TokenType.REFRESH), TimeUnit.MILLISECONDS);

        return new LoginResponse(accessToken, refreshToken);
    }

    public void logout(String accessToken) {
        String email = jwtTokenProvider.getEmail(accessToken, TokenType.ACCESS);
        redisService.deleteRefreshToken(email);
    }

    public UpdateUserResponse update(Long id, Long requestId, UpdateUserRequest updateUserRequest) {
        // 1. 받아온 id로 db에 user 존재 유무 확인
        User target = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        // 3. 나머지 userRequest 값으로 target 정보 수정
        target.updateProfile(updateUserRequest);

        // 4. 수정된 정보 db 저장
        User updated = userRepository.save(target);

        // 5. 저장 후 userResponse로 변환해서 반환
        return new UpdateUserResponse(
                updated.getName(),
                updated.getProfileImage(),
                updated.getGreeting()
        );
    }

    public void updatePassword(Long id, UpdatePasswordRequest updatePasswordRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String encodedNewPassword = bCryptPasswordEncoder.encode(updatePasswordRequest.getPassword());

        user.updatePassword(encodedNewPassword);

        userRepository.save(user);
    }
}
