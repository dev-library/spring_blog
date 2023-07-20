package com.spring.blog.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt") // properties 파일 중, jwt 프로퍼티에 속한 항목을 받아서 바인딩해줌
public class JwtProperties {
    private String issuer; // jwt.issuer=값  을 저장
    private String secretKey; // 원래 저장은 secret_key였으나, 자동으로 카멜케이스로 바꿔서 매핑됨.
                            // jwt.secret_key=값   을 저장
}
