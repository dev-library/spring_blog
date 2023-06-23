package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@AllArgsConstructor @Getter @Setter
@Builder @ToString @NoArgsConstructor
public class ReplyCreateRequestDTO {
    // 글번호, 댓글쓴이, 댓글내용
    private long blogId;
    private String replyWriter;
    private String replyContent;

    // 엔터티 클래스를 DTO로 변환해주는 메서드
    public ReplyCreateRequestDTO(Reply reply){
        this.blogId = reply.getBlogId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }

}
