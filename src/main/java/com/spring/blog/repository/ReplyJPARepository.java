package com.spring.blog.repository;

import com.spring.blog.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyJPARepository extends JpaRepository<Reply, Long> {

    // blogId를 기준으로 전체 댓글을 얻어오는 메서드를 쿼리메서드 방식으로 생성
    List<Reply> findAllByBlogId(long blogId);
    // blogId를 기준으로 연관된 댓글을 전부 삭제하는 메서드 생성
    void deleteAllByBlogId(long blogId);

}
