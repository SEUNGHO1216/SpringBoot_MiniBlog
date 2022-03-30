package com.llave.mini_project.controller;

import com.llave.mini_project.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//단순 페이지 이동을 위한 컨트롤러
//데이터 교환은 RestController
@Controller
public class BlogPageController {

    /*게시글 조회 페이지 이동*/
    @GetMapping("/api/blogs/articles")
    public String moveToDetail(@RequestParam("id") Long id , Model model,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){

        try{
            Long userId=userDetails.getUser().getId();
            model.addAttribute("userId",userId);
        }catch (NullPointerException e){
            model.addAttribute("error","로그인 미실시!");
        }

        return "detail";
    }

    /*게시글 작성 페이지 이동*/
    @GetMapping("/api/blogs/posts")
    public String moveToWrite(){
        return "write";
    }
}
