package com.spring.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AccessTokenResponseDTO {
    private String accessToken;
}
