package com.hanghae.preorder.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public void setValue(String to, String verificationCode, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(to, verificationCode, timeout, timeUnit);
    }

    public boolean compareValue(String email, String verificationCode) {
        if (checkEmail(email)) {
            String findCode = redisTemplate.opsForValue().get(email);
            return findCode != null && findCode.equals(verificationCode);
        }
        else {
            throw new IllegalStateException("no key");
        }

    }
    public boolean checkEmail (String email){
        return Boolean.TRUE.equals(redisTemplate.hasKey(email));
    }
}
