package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter @Setter @ToString @Builder
@AllArgsConstructor @NoArgsConstructor
public class ReplyUpdateRequestDTO {

    private long replyId;
    private String replyWriter;
    private String replyContent;

    public ReplyUpdateRequestDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyWriter = reply.getReplyWriter();
        this.replyContent = reply.getReplyContent();
    }
}
