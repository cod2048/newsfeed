package com.hanghae.module_activity.user.service;

import com.hanghae.module_activity.email.service.EmailService;
import com.hanghae.module_activity.redis.service.RedisService;
import com.hanghae.module_activity.security.jwt.JwtTokenProvider;
import com.hanghae.module_activity.security.jwt.TokenType;
import com.hanghae.module_activity.user.dto.request.LoginRequest;
import com.hanghae.module_activity.user.dto.request.UserRequest;
import com.hanghae.module_activity.user.dto.response.LoginResponse;
import com.hanghae.module_activity.user.dto.response.UserResponse;
import com.hanghae.module_activity.user.entity.User;
import com.hanghae.module_activity.user.repository.UserRepository;
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

    @Transactional
    public UserResponse create(UserRequest userRequest) {

        String emailCode = userRequest.getVerificationCode();
        String userEmail = userRequest.getEmail();

        boolean checkCode = redisService.compareValue(userEmail, emailCode);

        if(checkCode){
            String encodingPassword = bCryptPasswordEncoder.encode(userRequest.getPassword());
            //1. userRequest로 entity 생성
            User newUser = new User(
                userRequest.getEmail(),
                encodingPassword,
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
        else{
            throw new IllegalStateException("can't create user");
        }

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

    public LoginResponse login(LoginRequest loginRequest) {

        //1. request의 email이랑 password랑 db의 email password 검사
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Optional<User> loginUser = userRepository.findByEmail(email);
        log.info("로그인 서비스 진입(email,password가져오고, 유저찾기)");
        //2중 if문 쓰지말고 findByEmail뒤에 throw추가하기

        if(loginUser.isPresent()) {
            log.info("유저 찾기 성공");
            User user = loginUser.get();
            String userName = user.getName();
            if (bCryptPasswordEncoder.matches(password, user.getPassword())){
                log.info("인코딩 패스워드 검증 성공");
                String accessToken = jwtTokenProvider.generate(email, userName, TokenType.ACCESS);
                String refreshToken = jwtTokenProvider.generate(email, userName, TokenType.REFRESH);

                return new LoginResponse(accessToken, refreshToken, email, userName);
            }
            else{
                throw new IllegalStateException("비밀번호 불일치");
            }
        }
        else{
            throw new IllegalStateException("유저 정보 없음");
        }
    }

    public UserResponse update(Long id, Long requestId, UserRequest userRequest) {
        // 1. 받아온 id로 db에 user 존재 유무 확인
        User target = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("can't find user"));

        // 2. 받아온 userRequest 값으로 비밀번호 변경
        String newPassword = userRequest.getPassword();
        userRequest.updatePassword(newPassword, bCryptPasswordEncoder);

        // 3. 나머지 userRequest 값으로 target 정보 수정
        target.update(userRequest);

        // 4. 수정된 정보 db 저장
        User updated = userRepository.save(target);

        // 5. 저장 후 userResponse로 변환해서 반환
        return new UserResponse(
                updated.getId(),
                updated.getName()
        );
    }

}
