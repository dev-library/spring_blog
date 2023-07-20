package com.spring.blog.config.jwt;

import com.spring.blog.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service // 빈 컨테이너에 해당 클래스 등록
@RequiredArgsConstructor // 멤버변수를 활용한 생성자 자동 생성
public class TokenProvider {

    private final JwtProperties jwtProperties; // getter를 이용해 properties 파일에 작성한 데이터 가져오기

    public String generateToken(User user, Duration expiredAt){
        // 아래 메서드 작성 완료되면 추가로 작성
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    private String makeToken(Date expiry, User user){
        Date now = new Date(); //현재 시간으로 토큰 발급 날짜 저장할 준비

        return Jwts.builder() // 빌더패턴으로 토큰 생성
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더타입은 JWT
                .setIssuer(jwtProperties.getIssuer()) // application.properties에 기입된 내용 자동전달
                .setIssuedAt(now) //발급시각
                .setExpiration(expiry) //만기시각
                .setSubject(user.getLoginId()) // 기본적으로는 아이디를 줍니다
                .claim("id", user.getId()) // 클레임에는 PK제공
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 알고리즘과 비밀키 같이입력
                .compact(); // 생성메서드는 빌더패턴이지만 .build()가 아닌 .compact();
    }

    // 검증 로직은 유효한지, 혹은 유효하지 않은지만 리턴하면 되므로 boolean을 리턴합니다.
    // 발급된 토큰을 입력받았을때, 유효하면 true, 아니면 false를 리턴합니다.
    public boolean validToken(String token){
        try{
            Jwts.parser() // 토큰을 먼저 파싱(읽어들임)하고
                    .setSigningKey(jwtProperties.getSecretKey()) // 복호화시에는 암호화때 사용한 비밀키를 입력
                    .parseClaimsJws(token); // 검증 타겟이 되는 토큰 입력
            return true; // 아무일도 없으면 유효 토큰이므로 true 리턴
        } catch (Exception e){
            return false; // 파싱 및 복호화 비교중 문제가 발생하면 익셉션이 발생하므로, false 리턴
        }
    }

    public Authentication getAuthentication(String token){ // 토큰 입력시 인증정보 읽어오기
        Claims claims = getClaims(token); // getClaims()는 아래에 추가로 작성할것임
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(),
                        "", authorities), token, authorities);
    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 클레임 정보(실제 개인정보)는 외부에서 직접적으로 호출할 수 없고, 오로지 토큰제공클래스 내부에서만 호출되도록 처리
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) // 비밀키를 제공하고
                .parseClaimsJws(token) // 토큰을 제공하면
                .getBody(); // 유저정보를 역으로 리턴.
    }

}











