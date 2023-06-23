package com.spring.blog.service;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyServiceTest {

    // 서비스 객체 세팅해주세요
    @Autowired
    ReplyService replyService;

    // findAllByBlogIdTest()는 ReplyRepositoryTest코드를 참조해서 작성해주세요.

    @Test
    @Transactional
    @DisplayName("2번 글과 연동된 댓글 전체를 가져오고 개수가 4개일 것이라고 단언")
    public void findAllByBlogIdTest(){
        // given : fixture 저장
        long blogId = 2;

        // when : 2번글의 댓글 전부 가져오기
        List<ReplyResponseDTO> result = replyService.findAllByBlogId(blogId);

        // then : 개수는 4개일 것이다.
        assertEquals(4, result.size());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 5번으로 얻어온 객체의 replyWriter은 개발고수이고, replyId는 5일것이다.")
    public void findByReplyIdTest(){
        // given
        long replyId = 5;
        String replyWriter = "개발고수";

        // when
        ReplyResponseDTO result = replyService.findByReplyId(replyId);

        // then
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyId, result.getReplyId());
    }

    @Test
    @Transactional
    @DisplayName("댓글번호 3번 삭제시, 2번포스팅의 댓글은 3개이고, 3번으로 재요청시 null")
    public void deleteByReplyIdTest(){
        // given
        long replyId = 3;
        long blogId = 2;

        // when
        replyService.deleteByReplyId(replyId);

        // then
        assertEquals(3, replyService.findAllByBlogId(blogId).size());
        assertNull(replyService.findByReplyId(replyId));
    }

    @Test
    @Transactional
    @DisplayName("replyWriter는 토토비, replyContent는 토비보자, blogId는 3번으로 입력시 3번글 연동댓글 2개, 입력된 픽스처와 일치하는 멤버변수")
    //"replyWriter는 토토비, replyContent는 토비보자, blogId는 3번으로 입력시 3번글 연동댓글 2개,
    // 입력된 픽스처와 일치하는 멤버변수"
    public void saveTest(){
        // given
        String replyWriter = "토토비";
        String replyContent = "토비보자";
        long blogId = 3;
        ReplyCreateRequestDTO replyInsertDTO = ReplyCreateRequestDTO.builder()
                                                    .replyWriter(replyWriter)
                                                    .replyContent(replyContent)
                                                    .blogId(blogId)
                                                    .build();
        // when
        replyService.save(replyInsertDTO);

        // then
        List<ReplyResponseDTO> resultList = replyService.findAllByBlogId(blogId);
        assertEquals(2, resultList.size());

        ReplyResponseDTO result = resultList.get(resultList.size() - 1);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
    }

    @Test
    @Transactional
    @DisplayName("replyId 2번의 replyWriter를 바뀐짹짹이, replyContent를 부엉부엉부엉부엉으로, updatedAt이 publishedAt보다 이후로 바뀜")
    // "replyId 2번의 replyWriter를 바뀐짹짹이, replyContent를 부엉부엉부엉부엉으로,
    // updatedAt이 publishedAt보다 이후로 바뀜"
    public void updateTest(){
        // given
        long replyId = 2;
        String replyWriter = "바뀐짹짹이";
        String replyContent = "부엉부엉부엉부엉";

        ReplyUpdateRequestDTO replyUpdateDTO = new ReplyUpdateRequestDTO();
        replyUpdateDTO.setReplyId(replyId);
        replyUpdateDTO.setReplyWriter(replyWriter);
        replyUpdateDTO.setReplyContent(replyContent);

        // when
        replyService.update(replyUpdateDTO);

        // then
        ReplyResponseDTO result = replyService.findByReplyId(replyId);
        assertEquals(replyWriter, result.getReplyWriter());
        assertEquals(replyContent, result.getReplyContent());
        assertTrue(result.getUpdatedAt().isAfter(result.getPublishedAt()));

    }


}
