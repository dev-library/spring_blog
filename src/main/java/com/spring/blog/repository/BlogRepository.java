package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogRepository {

    // 사전에 정의해야 하는 메서드들
    // 테이블 생성
    void createBlogTable();// 이 메서드 호출시 CREATE TABLE 구문 실행

    // 더미데이터 입력
    void insertTestData();// 더미데이터로 3개 row 데이터 입력하기

    // 테이블 삭제
    void dropBlogTable();// 단위테스트 종료 후 DB초기화를 위해 테이블 삭제


    // 전체 데이터 조회 기능 findAll()
    // Blog 엔터티 하나가 포스팅 row 하나를 받을 수 있고
    // n개의 복수의 Blog 엔터티를 받아와야 하므로 List로 감쌈
    List<Blog> findAll();






}
