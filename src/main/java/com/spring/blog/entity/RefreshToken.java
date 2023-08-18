package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false) // 수정불가, PK이므로
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;// 유저번호. 중복될 일이 없음.

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken; // 토큰값 자체를 저장함.

    public RefreshToken(Long userId, String refreshToken) { // 유저명과 토큰값을 넣어주면 저장
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) { // 토큰값 갱신시 사용할 메서드
        this.refreshToken = newRefreshToken;

        return this;
    }
}
