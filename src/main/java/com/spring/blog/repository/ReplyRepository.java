package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {

    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);

    // 댓글번호 입력시 특정 댓글 하나만 가져오는 메서드 findByReplyId()를 선언해주세요,
    ReplyFindByIdDTO findByReplyId(long replyId);

}
