package com.llave.mini_project.controller;

import com.llave.mini_project.dto.ReplyRequestDto;
import com.llave.mini_project.models.Reply;
import com.llave.mini_project.repository.ReplyRepository;
import com.llave.mini_project.security.UserDetailsImpl;
import com.llave.mini_project.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ReplyController {

    private final ReplyRepository replyRepository;
    private final ReplyService replyService;
    @Autowired
    public ReplyController(ReplyRepository replyRepository, ReplyService replyService){
        this.replyRepository=replyRepository;
        this.replyService=replyService;
    }

    //댓글 저장 컨트롤러
    @ResponseBody
    @PostMapping("/api/reply")
    public Reply createReply(@RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(replyRequestDto);
        Long userId=userDetails.getUser().getId();
        Reply reply=replyService.createReply(replyRequestDto, userId);
        return reply;
    }

    //게시물에 해당하는 댓글 불러오기
    @ResponseBody
    @GetMapping("/api/reply/{id}")
    public List<Reply> getReplies(@PathVariable Long id){
        List<Reply> replyList=replyService.getReplies(id);
        return replyList;
    }

    //댓글 수정 컨트롤러
    @ResponseBody
    @PutMapping("/api/reply/{id}")
    public Long editReplies(@PathVariable Long id, @RequestBody ReplyRequestDto replyRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        //로그인한 유저 아이디를 따로 넘겨준다.
        Long userId=userDetails.getUser().getId();
        replyService.editReplies(id, replyRequestDto, userId);
        return id;
    }
    //댓글 삭제 컨트롤러
    @ResponseBody
    @DeleteMapping("/api/reply/{id}")
    public void deleteReplies(@PathVariable Long id){
        replyService.deleteReplies(id);
    }


}
