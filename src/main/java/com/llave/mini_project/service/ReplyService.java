package com.llave.mini_project.service;

import com.llave.mini_project.dto.ReplyRequestDto;
import com.llave.mini_project.models.Reply;
import com.llave.mini_project.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReplyService {

    private final ReplyRepository replyRepository;
    @Autowired
    public ReplyService(ReplyRepository replyRepository){
        this.replyRepository=replyRepository;
    }

    //댓글 저장 서비스
    public Reply createReply(ReplyRequestDto replyRequestDto, Long userId) {
        System.out.println(replyRequestDto);
        System.out.println(userId);
        Reply reply=new Reply(replyRequestDto, userId);
        replyRepository.save(reply);
        return reply;
    }
    //댓글 불러오기 서비스
    public List<Reply> getReplies(Long blogId) {
        //댓글 테이블에 저장해놓은 해당 블로그 아이디로 찾고 최신순으로 정렬
        List<Reply> replyList=replyRepository.findAllByBlogIdOrderByModifiedAtDesc(blogId);
        return replyList;
    }

    //댓글 수정 서비스
    @Transactional
    public void editReplies(Long id, ReplyRequestDto replyRequestDto, Long userId){
        Reply reply=replyRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 아이디가 없습니다.")
        );
        reply.update(replyRequestDto, userId);
    }

    //댓글 삭제 서비스
    public void deleteReplies(Long id) {
        replyRepository.deleteById(id);
    }
}
