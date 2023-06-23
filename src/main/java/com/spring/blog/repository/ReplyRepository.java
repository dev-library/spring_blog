package com.spring.blog.repository;

import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {

    List<ReplyResponseDTO> findAllByBlogId(long blogId);

    // 댓글번호 입력시 특정 댓글 하나만 가져오는 메서드 findByReplyId()를 선언해주세요,
    ReplyResponseDTO findByReplyId(long replyId);

    // 삭제는 replyId를 이용해 합니다. deleteByReplyId 메서드를 정의해주세요.
    void deleteByReplyId(long replyId);

    // 삽입구문은 ReplyInsertDTO를 이용해서 합니다. save 메서드를 정의해주세요.
    // ReplyInsertDTO에 내장된 멤버변수인 blogId(몇번글에), replyWriter(누가), replyContent(무슨내용)
    // 들을 전달해서 INSERT구문을 완성시키기 위함
    void save(ReplyCreateRequestDTO replyInsertDTO);

    // 수정로직은 ReplyUpdateDTO를 이용해서 update 메서드를 호출해 처리합니다.
    // 수정로직은 replyId를 WHERE절에 집어넣고, replyWriter, replyContent의 내용을 업데이트해주고
    // updatedAt 역시 now()로 바꿔줍니다.
    void update(ReplyUpdateRequestDTO replyUpdateDTO);

    // blogId를 받아서 특정 글과 연계된 댓글 전체를 삭제하는 메서드를 정의만 해주세요.
    void deleteByBlogId(long blogId);



}
