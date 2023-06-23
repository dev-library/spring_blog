package com.spring.blog.repository;

import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class ReplyRepositoryTest {

    @Autowired// 테스트 코드에서는 필드 주입을 써도 무방합니다.
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @DisplayName("2번 글에 연동된 댓글 개수가 4개인지 확인")
    public void findAllByBlogIdTest(){
        // given : 2번 글을 조회하기 위한 fixture 저장
        long blogId = 2;
        // when : findAllByBlogId() 호출 및 결과 자료 저장
        List<ReplyResponseDTO> result = replyRepository.findAllByBlogId(blogId);
        // then : 2번글에 연동된 댓글이 4개일것이라고 단언
        // assertj로 임포트
        assertThat(result.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번 자료의 댓글은 3번이고, 글쓴이는 '바둑이'")
    public void findByReplyIdTest(){
        // given : replyId fixture 3저장
        long replyId = 3;
        //when
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);
        //then
        assertEquals("바둑이", result.getReplyWriter());
        assertEquals(3, result.getReplyId());
    }

    @Test
    @Transactional
    @DisplayName("2번 글에 연동된 댓글번호 2번을 삭제한 다음, 2번글에 연동된 데이터 개수가 3개이고, 그리고 2번으로 재조회시 null일것이다.")
    public void deleteByReplyIdTest(){
        // given : fixture - 글번호 2번, 댓글번호 2번 생성
        long blogId = 2;
        long replyId = 2;

        // when : 댓글 삭제하기
        replyRepository.deleteByReplyId(replyId);

        // then : 2번글에 연동된 댓글 개수는 3개일것이고, 2번 댓글 재 조회시 null
        assertEquals(3, replyRepository.findAllByBlogId(blogId).size());
        assertNull(replyRepository.findByReplyId(replyId));
    }

    @Test
    @Transactional
    @DisplayName("fixture를 이용해 INSERT후, 전체 데이터를 가져와서 마지막인덱스 번호 요소를 얻어와서 입력했던 fixture와 비교하면 같다")
    public void saveTest(){
        // given : 픽스처 세팅한 다음 ReplyCreateRequestDTO 생성 후 멤버변수 초기화
        long blogId = 1;
        String replyWriter = "도비의스프링";
        String replyContent = "도비는 자유입니다!!!!";
        ReplyCreateRequestDTO replyInsertDTO = ReplyCreateRequestDTO.builder()
                                                    .blogId(blogId)
                                                    .replyWriter(replyWriter)
                                                    .replyContent(replyContent)
                                                    .build();

        // when : insert 실행
        replyRepository.save(replyInsertDTO);

        // then : blogId번 글의 전체 댓글을 가지고 온 다음 마지막 인덱스 요소만 변수에 저장한 다음
        //        getter를 이용해 위에서 넣은 fixture와 일치하는지 체크.
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId);
        // resultList의 개수 - 1 이 마지막 인덱스 번호이므로, resultList에서 마지막 인덱스 요소만 가져오기
        ReplyResponseDTO result = resultList.get(resultList.size() - 1);
        // 단언문 작성
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    @DisplayName("fixture로 수정할 댓글쓴이, 댓글내용, 3번 replyId를 지정합니다. 수정 후 3번자료를 DB에서 꺼내 fixture비교 및 published_at과 updated_at이 다른지 확인")
    // "fixture로 수정할 댓글쓴이, 댓글내용, 3번 replyId를 지정합니다.
    // 수정 후 3번자료를 DB에서 꺼내 fixture비교 및 published_at과 updated_at이 다른지 확인"
    // DTO를 만들어서 거기다가 수정자료 자료 집어넣기
    public void updateTest(){
        // given
        long replyId = 3;
        String replyWriter = "수정글쓴잉";
        String replyContent = "수정한내용물!";
        ReplyUpdateRequestDTO replyUpdateDTO = ReplyUpdateRequestDTO.builder()
                                                    .replyId(replyId)
                                                    .replyWriter(replyWriter)
                                                    .replyContent(replyContent)
                                                    .build();

        // when
        replyRepository.update(replyUpdateDTO);

        //then
        ReplyResponseDTO result = replyRepository.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));

    }

    @Test
    @Transactional
    @DisplayName("blogId가 2인 글을 삭제하면, 삭제한 글의 전체 댓글 조회시 자료 길이는 0일것이다.")
    public void deleteByBlogIdTest(){
        // given : fixture 작성
        long blogId = 2;

        // when : 삭제 수행
        replyRepository.deleteByBlogId(blogId);

        // then : blogId번 글 전체 댓글을 얻어와서 길이가 0인지 확인
        List<ReplyResponseDTO> resultList = replyRepository.findAllByBlogId(blogId);
        assertEquals(0, resultList.size());
    }


}
