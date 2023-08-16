package com.spring.blog.repository;

import com.spring.blog.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId); // 유저번호로 토큰정보 얻기
    RefreshToken findByRefreshToken(String refreshToken); // 토큰으로 토큰정보 얻기
}
