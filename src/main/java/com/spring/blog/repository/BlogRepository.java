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

    // 단일행 조회 기능 findById()
    // 호출시 blogId를 요구합니다.
    Blog findById(long blogId);

    // 새 데이터 저장 기능 save()
    // 저장시 writer, blog_title, blog_content 3개 파라미터를 요구함
    // 근데 위 3개 파라미터는 Blog 엔터티의 멤버변수임
    void save(Blog blog);

    // 데이터 삭제 기능 deleteById()
    // 삭제시 삭제에 필요한 primary key에 해당하는 아이디값을 요구
    void deleteById(long blogId);

    // 데이터 수정 기능 update()
    // JPA에서는 .save()를 동일하게 쓰지만, 현재 코드에서 메서드 오버로딩도 불가능하고
    // 분리할 방법이 없으므로 메서드명을 다르게 사용합니다.
    void update(Blog blog);

}
