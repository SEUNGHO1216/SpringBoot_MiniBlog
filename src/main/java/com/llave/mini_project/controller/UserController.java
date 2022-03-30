package com.llave.mini_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.llave.mini_project.dto.DupDto;
import com.llave.mini_project.dto.UserRequestDto;
import com.llave.mini_project.security.UserDetailsImpl;
import com.llave.mini_project.service.KakaoUserService;
import com.llave.mini_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {


    private final UserService userService;
    private final KakaoUserService kakaoUserService;
    @Autowired
    public UserController(UserService userService,KakaoUserService kakaoUserService){

        this.userService=userService;
        this.kakaoUserService=kakaoUserService;
    }

    //로그인 페이지
    @GetMapping("/user/login")
    public String login(){
        return "login";
    }
    //회원가입 페이지
    @GetMapping("/user/signup")
    public String signup(){
        return "signup";
    }
    //회원가입 요청
    @PostMapping("/user/signup")
    public String signupSubmit(@Valid UserRequestDto requestDto, Errors errors, Model model){
        if(errors.hasErrors()){
            //회원가입 양식에서 에러가 있을 경우

            //value값으로 넣어서 값 유지
            model.addAttribute("requestDto",requestDto);

            //유효성 통과 못한  필드와 메시지 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for(String key:validatorResult.keySet()){
                System.out.println(key);
                System.out.println(validatorResult.get(key));
                model.addAttribute(key, validatorResult.get(key));
            }
            //오류 떴으니 재입력하러 이동
            return "signup";
        }
        userService.register(requestDto);
        return "redirect:/user/login";
    }

    //중복검사 실시
    @ResponseBody
    @PostMapping("/user/check")
    public boolean isDuplicated(@RequestBody()DupDto dupDto){ // json형식으로 들어오니 관련된 GETTER, SETTER가 있는 dto가 필요함
        boolean result=userService.isDuplicated(dupDto.getNickname());
        return result;
    }

    //카카오톡 api
    @GetMapping("/user/kakao/callback")
    public String kakaoCallBack(@RequestParam("code")String code) throws JsonProcessingException {

        kakaoUserService.kakaoLogin(code);

        return "redirect:/";
    }

    //로그아웃
    @PostMapping("/user/logout")
    public String logout(){
        return "redirect:/";
    }
}
