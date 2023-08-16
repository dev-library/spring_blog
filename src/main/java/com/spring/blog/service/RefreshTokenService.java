package com.spring.blog.service;


import com.spring.blog.entity.RefreshToken;
import com.spring.blog.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // RequiredArgsConstructor에 의해 생성자가 요구하는 필드는 자동으로 생성자 주입됩니다.
public class RefreshTokenService {

    // 서비스 레이어는 레포지토리 레이어를 호출합니다.
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }


}
