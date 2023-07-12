package com.spring.blog.service;

import com.spring.blog.entity.Reply;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    ReplyRepository replyRepository;

    ReplyJPARepository replyJPARepository;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository,
                            ReplyJPARepository replyJPARepository){
        this.replyRepository = replyRepository;
        this.replyJPARepository = replyJPARepository;
    }

    // JPA 실습중에 사용하는 기간에 전체적으로 DTO를 쓰던 로직을 전부 Reply를 쓰도록 해 주시면 됩니다.
    @Override
    public List<Reply> findAllByBlogId(long blogId) {
        //return replyRepository.findAllByBlogId(blogId);
        return replyJPARepository.findAllByBlogId(blogId);
    }

    @Override
    public Reply findByReplyId(long replyId) {
        //return replyRepository.findByReplyId(replyId);
        return replyJPARepository.findById(replyId).get();
    }

    @Override
    public void deleteByReplyId(long replyId) {
        //replyRepository.deleteByReplyId(replyId);
        replyJPARepository.deleteById(replyId);
    }

    @Override
    public void save(Reply reply) {
        //replyRepository.save(reply);
        replyJPARepository.save(reply);
    }

    @Override
    public void update(Reply reply) {
        //replyRepository.update(reply);
        Reply updatedReply = replyJPARepository.findById(reply.getReplyId()).get();
        updatedReply.setReplyContent(reply.getReplyContent());
        updatedReply.setReplyWriter(reply.getReplyWriter());
        replyJPARepository.save(updatedReply);
    }
}
