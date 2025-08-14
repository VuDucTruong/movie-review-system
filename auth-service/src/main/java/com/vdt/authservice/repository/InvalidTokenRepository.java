package com.vdt.authservice.repository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidTokenRepository {
    RedisTemplate<String , String> redisTemplate;

    public void saveInvalidToken(String token, Long remainingTime) {
        redisTemplate.opsForValue().set(token, "blacklisted", remainingTime, TimeUnit.MILLISECONDS);
    }

    public boolean isInvalidToken(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}
