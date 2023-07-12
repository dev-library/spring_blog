package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

// 역직렬화(디비 -> 자바객체) 가 가능하도록 blog 테이블 구조에 맞춰서 멤버변수를 선언해주세요.
// 실무에서는 대부분의 경우 엔터티 클래스에는 세터를 만들지 않습니다.
@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor @Builder// 빌더패턴 생성자를 쓸수있게 해줌
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long blogId; // 숫자는 어지간하면 long형을 사용합니다.

    @Column(nullable = false) // not null
    private String writer;

    @Column(nullable = false) // not null
    private String blogTitle;

    @Column(nullable = false) // not null
    private String blogContent;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    @ColumnDefault("0")// 조회수는 기본값을 0으로 설정
    private Long blogCount; // Wrapper Long형을 사용, 기본형 변수는 null을 가질 수 없음

    // @PrePersist 어노테이션은 insert 되기 전 실행할 로직을 작성합니다
    @PrePersist
    public void setDefaultValue(){
        this.blogCount = this.blogCount == null ? 0 : this.blogCount;
        this.publishedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // @PreUpdate 어노테이션은 update 되기 전 실행할 로직을 작성합니다.
    @PreUpdate
    public void setUpdateValue(){
        this.updatedAt = LocalDateTime.now();
    }



}
