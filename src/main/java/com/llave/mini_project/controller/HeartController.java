package com.llave.mini_project.controller;

import com.llave.mini_project.dto.HeartRequestDto;
import com.llave.mini_project.models.Blog;
import com.llave.mini_project.models.User;
import com.llave.mini_project.security.UserDetailsImpl;
import com.llave.mini_project.service.HeartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HeartController {

    private HeartService heartService;

    @Autowired
    public HeartController(HeartService heartService){
        this.heartService=heartService;
    }
    //컨트롤러 단에서 부터 blog 아이디에 user 아이디가 이미 있으면 좋아요 취소인거 구현(ip 내기)
    //좋아요 입력
    @ResponseBody
    @PostMapping("/api/heart")
    public int addHeart(@RequestBody HeartRequestDto heartRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            User user=userDetails.getUser();
            int heartCount=heartService.addHeart(heartRequestDto.getId(), user);
            return heartCount;
        }catch(NullPointerException e){
            //로그인한 user가 없어서 npe오느니 유저 아이디를 -1로 대체하겠다.
            int result= -1;
            return result;
        }
    }

    //좋아요 개수 출력 컨트롤러
    @ResponseBody
    @GetMapping("/api/heart/{id}")
    public int showHeart(@PathVariable Long id){
        int heartCount=heartService.showHeart(id);

        return heartCount;
    }

}