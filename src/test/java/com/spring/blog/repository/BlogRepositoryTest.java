package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)// DROP TABLE시 필요한 어노테이션
public class BlogRepositoryTest {

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach // 각 테스트 전에 공통적으로 실행할 코드를 저장해두는곳
    public void setBlogTable(){
        blogRepository.createBlogTable();// blog 테이블 생성
        blogRepository.insertTestData();// 생성된 blog 테이블에 더미데이터 3개 입력
    }

    @Test
    public void findAllTest(){
        //given 없음

        // when DB에 있는 모든 데이터를 자바 엔터티로 역직렬화
        List<Blog> blogList = blogRepository.findAll();
        System.out.println(blogList);

        // then 더미데이터가 3개이므로 3개일것이라 단언
        assertEquals(3, blogList.size());
    }


    @AfterEach // 각 단위테스트 끝난 후에 실행할 구문을 작성
    public void dropBlogTable(){
        blogRepository.dropBlogTable();// blog 테이블 지우기
    }



}
