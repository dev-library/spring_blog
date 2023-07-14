package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter // 엔터티를 DTO처럼 쓰는 행위는 좋지 못합니다
@NoArgsConstructor
@Table(name = "users") // 만약 클래스명과 테이블명이 다르게 매칭되기를 원하면 사용하는 어노테이션, USER는 MySQL의 예약어.
public class User implements UserDetails {// UserDetails 의 구현체만 스프링 시큐리티에서 인증정보로 사용할 수 있음

    // 필드로 사용할 멤버변수 선언
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false) // 수정 불가 옵션
    private Long id; // null 체크 대비.

    @Column(nullable = false, unique = true) // 프라이머리키는 아니지만 유일 키로 사용
    private String email;

    @Column(nullable = false, unique = true) // 중복닉네임 불가, 닉네임 지정 필수
    private String loginId;

    // 비밀번호는 null 허용(OAuth2.0을 활용한 소셜로그인은 비밀번호가 없음)
    private String password;

    @Builder                                                     // 인증정보
    public User(String email, String password, String loginId, String auth){// 생성자에서 인증 정보를 요구함
        this.email = email;
        this.password = password;
        this.loginId = loginId;
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }// 비밀번호 리턴

    @Override
    public String getUsername() {
        return loginId;
    } // 로그인에 사용할 아이디 입력(unique 요소만 가능)

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // 로그인중이면 true 리턴

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // 밴당하지 않았다면 true 리턴

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
