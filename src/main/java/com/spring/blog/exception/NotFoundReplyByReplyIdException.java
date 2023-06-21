package com.spring.blog.exception;

public class NotFoundReplyByReplyIdException extends RuntimeException{
    // 생성자에 에러 사유를 전달할수있도록 메세지를 적습니다.
    public NotFoundReplyByReplyIdException(String message){
        super(message);
    }
}
