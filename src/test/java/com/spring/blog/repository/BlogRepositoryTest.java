package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)// 테스트 코드 주기가 메서드 단위임(기본값)
public class BlogRepositoryTest {

    @Autowired
    BlogRepository blogRepository;

    @BeforeEach // 각 테스트 전에 공통적으로 실행할 코드를 저장해두는곳
    public void setBlogTable(){
        blogRepository.createBlogTable();// blog 테이블 생성
        blogRepository.insertTestData();// 생성된 blog 테이블에 더미데이터 3개 입력
    }

    @Test
    @DisplayName("전체 행을 얻어오고, 그 중 자바 1번 인덱스 행만 추출해 번호 확인")
    public void findAllTest(){
        //given (사람기준)2번째요소 조회를 위한 fixture 선언
        int blogId = 1; // 자바 자료구조 인덱스는 0번부터

        // when DB에 있는 모든 데이터를 자바 엔터티로 역직렬화
        List<Blog> blogList = blogRepository.findAll();

        // then 더미데이터가 3개이므로 3개일것이라 단언
        assertEquals(3, blogList.size());
        // (사람기준)2번째 객체의 ID번호는 2번일것이다.
        assertEquals(2, blogList.get(blogId).getBlogId());

    }

    @Test
    @DisplayName("4번째 행 데이터 저장 후, 행 저장여부 및 전달데이터 저장 여부 확인")
    public void saveTest(){
        // given : 저장을 위한 Blog entity 생성 및 writer, blogTitle, blogContent
        // 에 해당하는 fixture setter로 저장하기, findAll()로 얻어올 데이터의 인덱스번호 저장
        String writer = "추가할작가명";
        String blogTitle = "추가할제목";
        String blogContent = "추가할본문";
        //Blog blog = new Blog();
        //blog.setWriter(writer);
        //blog.setBlogTitle(blogTitle);
        //blog.setBlogContent(blogContent);

        // blog 객체 생성 코드를 빌더패턴으로 리팩토링
        // 빌더 패턴 쓰는법
        // 장점 : 파라미터 순서를 뒤바꿔서 집어넣어도 상관없음
        Blog blog = Blog.builder()// 빌더패턴 시작
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();// 빌더패턴 끝

        int blogId = 3;// 4번째 요소 조회(자바 인덱스는 0번부터 시작)
        // when : save() 메서드 호출 하고, findAll()로 전체 데이터 가져오기
        blogRepository.save(blog);
        List<Blog> blogList = blogRepository.findAll();
        // then : 전체 데이터 개수가 4개인지,
        // 그리고 방금 INSERT한 데이터의 writer, blogtitle, blogContent가
        // 입력한대로 들어갔는지 단언문으로 확인
        assertEquals(4, blogList.size());
        assertEquals(writer, blogList.get(blogId).getWriter());
        assertEquals(blogTitle, blogList.get(blogId).getBlogTitle());
        assertEquals(blogContent, blogList.get(blogId).getBlogContent());
    }

    @Test
    @DisplayName("2번 글을 조회했을때, 제목과 글쓴이와 번호가 단언대로 받아와지는지 확인")
    public void findByIdTest(){
        // given : 조회할 DB기준 2번 id를 변수로 저장합니다.
        long id = 2;
        // when : 레포지토리에서 단일행 Blog를 얻어와 저장합니다.
        Blog blog = blogRepository.findById(id);
        // then : 해당 객체의 writer 멤버변수는 "2번유저" 이고 blogTitle은 "2번제목"이고
        // blogId 는 2이다.
        assertEquals("2번유저", blog.getWriter());
        assertEquals("2번제목", blog.getBlogTitle());
        assertEquals(2, blog.getBlogId());
    }

    @Test
    @DisplayName("2번 삭제 후 전체 목록 가져왔을때 남은행수 2개, 삭제한번호 재조회시 null")
    public void deleteByIdTest(){
        // given : 삭제할 자료의 번호를 저장
        long blogId = 2;
        // when : 삭제로직 실행 후, findAll(), findById()로 전체 행, 개별행 가져오기
        blogRepository.deleteById(blogId);

        // then : 단언문을 이용해 전체 행 2개, 개별 행은 null임을 확인
        assertEquals(2, blogRepository.findAll().size());
        assertNull(blogRepository.findById(blogId));
    }

    @Test
    @DisplayName("2번글의 제목을 '수정한제목' 으로, 본문도 '수정한 본문' 으로 수정 후 확인")
    public void updateTest(){
        // given : 2번글 원본 데이터를 얻어온 다음, blogTitle, blogContent내용만 수정해서 다시 update()
        // Blog객체를 생성해서 blogId와 blogTitle, blogContent내용만 setter로 주입해서 다시 update()

        // 픽스처 생성
        long blogId = 2;
        String blogTitle = "수정한제목";
        String blogContent = "수정한본문";

        // 1번 given 실행
        //Blog blog = blogRepository.findById(blogId);

        //blog.setBlogTitle(blogTitle);
        //blog.setBlogContent(blogContent);

        // 2번 given 실행
        Blog blog = Blog.builder()  // 빌더패턴 시작
                .blogId(blogId)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build(); // 빌더패턴 끝



        // when : 수정 내역을 디비에 반영해주기
        blogRepository.update(blog);

        // then : 바뀐 2번 글의 타이틀은 "수정한제목", 본문은 "수정한본문" 으로 변환되었을것이다.
        assertEquals(blogTitle, blogRepository.findById(blogId).getBlogTitle());
        assertEquals(blogContent, blogRepository.findById(blogId).getBlogContent());
    }

    @AfterEach // 각 단위테스트 끝난 후에 실행할 구문을 작성
    public void dropBlogTable(){
        blogRepository.dropBlogTable();// blog 테이블 지우기
    }

}
