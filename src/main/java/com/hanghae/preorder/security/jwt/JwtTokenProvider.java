package com.hanghae.preorder.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {
//    @Value("${jwt.secret-key.access}")
    private String accessSecretKey = "qwerasdfzxcvqwerasdfzxcvqwerasdfzxcv";

//    @Value("${jwt.secret-key.refresh}")
    private String refreshSecretKey = "qwerasdfzxcvqwerasdfzxcvqwerasdfzxcv";

//    @Value("${jwt.expired-time.token.access}")
    private Long accessTokenExpiredTimeMs = 20000000000L;

//    @Value("${jwt.expired-time.token.refresh}")
    private Long refreshTokenExpiredTimeMs = 20000000000L;

    public String generate(String email, String userName, TokenType type) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);
        claims.put("email", email);

        String secretKey;
        Long expiredTime;
        if (type.equals(TokenType.ACCESS)) {
            secretKey = accessSecretKey;
            expiredTime = accessTokenExpiredTimeMs;
        } else {
            secretKey = refreshSecretKey;
            expiredTime = refreshTokenExpiredTimeMs;
        }

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isExpired(String token, TokenType type) {
        Date expiredDate = extractClaims(token, type).getExpiration();
        return expiredDate.before(new Date());
    }

    public long getExpiredTime(String token, TokenType type) {
        Date expiredDate = extractClaims(token, type).getExpiration();
        Date currentDate = new Date();
        return expiredDate.getTime() - currentDate.getTime();
    }

    /**
     * 토큰 속 정보 name 추출
     */
    public String getName(String token, TokenType type) {
        return extractClaims(token, type).get("name", String.class);
    }

    /**
     * 토큰 속 정보 email 추출
     */
    public String getEmail(String token, TokenType type) {
        return extractClaims(token, type).get("email", String.class);
    }

    private Claims extractClaims(String token, TokenType type) {
        if (type.equals(TokenType.ACCESS)) {
            return Jwts.parserBuilder().setSigningKey(getKey(accessSecretKey))
                    .build().parseClaimsJws(token).getBody();
        }
        return Jwts.parserBuilder().setSigningKey(getKey(refreshSecretKey))
                .build().parseClaimsJws(token).getBody();
    }

    private Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
