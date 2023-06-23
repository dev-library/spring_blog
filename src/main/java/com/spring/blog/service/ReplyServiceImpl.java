package com.spring.blog.service;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    ReplyRepository replyRepository;

    @Autowired
    public ReplyServiceImpl(ReplyRepository replyRepository){
        this.replyRepository = replyRepository;
    }

    @Override
    public List<ReplyResponseDTO> findAllByBlogId(long blogId) {
        return replyRepository.findAllByBlogId(blogId);
    }

    @Override
    public ReplyResponseDTO findByReplyId(long replyId) {
        return replyRepository.findByReplyId(replyId);
    }

    @Override
    public void deleteByReplyId(long replyId) {
        replyRepository.deleteByReplyId(replyId);
    }

    @Override
    public void save(ReplyCreateRequestDTO replyInsertDTO) {
        replyRepository.save(replyInsertDTO);
    }

    @Override
    public void update(ReplyUpdateRequestDTO replyUpdateDTO) {
        replyRepository.update(replyUpdateDTO);
    }
}
