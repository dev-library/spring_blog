package com.spring.blog.entity;

import lombok.*;

import java.time.LocalDateTime;

// 역직렬화(디비 -> 자바객체) 가 가능하도록 blog 테이블 구조에 맞춰서 멤버변수를 선언해주세요.
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder// 빌더패턴 생성자를 쓸수있게 해줌
public class Blog {
    private long blogId; // 숫자는 어지간하면 long형을 사용합니다.
    private String writer;
    private String blogTitle;
    private String blogContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
    private long blogCount;

}
