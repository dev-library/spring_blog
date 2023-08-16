package com.spring.blog.controller;

import com.spring.blog.dto.AccessTokenResponseDTO;
import com.spring.blog.dto.RefreshTokenRequestDTO;
import com.spring.blog.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")                                        // json으로 보내야 파라미터 매핑됨
    public ResponseEntity<AccessTokenResponseDTO> createNewAccessToken(@RequestBody RefreshTokenRequestDTO request){
        // 전달받은 리프레시 토큰을 이용해 새로운 억세스 토큰 발급
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        // 발급된 토큰을 401 응답(생성됨) 과 함께 리턴
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AccessTokenResponseDTO(newAccessToken));
    }

}
