package com.llave.mini_project.service;

import com.llave.mini_project.dto.UserRequestDto;
import com.llave.mini_project.models.User;
import com.llave.mini_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){

        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    //유효성 체크 (에러메시지 다발을 받음->erros.getFieldErrors())
    public Map<String, String> validateHandling(Errors erros){
        Map<String , String > validatorResult=new HashMap<>();

        //유효성 검사에 오류가 난 필드 목록을 받아서 for문 돌림
        for(FieldError error : erros.getFieldErrors()){
            System.out.println("에러필드:"+error.getField());
            String validKey=String.format("valid_%s", error.getField());
            validatorResult.put(validKey, error.getDefaultMessage());
        }
        return  validatorResult;
    }

    //회원가입 요청
    public void register(UserRequestDto requestDto) {
        String nickname=requestDto.getNickname();
        String password=passwordEncoder.encode(requestDto.getPassword());
        User user= new User(new UserRequestDto(nickname, password));
        userRepository.save(user);
    }

    //중복검사
    public boolean isDuplicated(String nickname) {
        System.out.println("서비스에서의 닉네임:"+nickname);
        //repositroy 커스텀(닉네임이 존재하는가?)
        return userRepository.existsByNickname(nickname);
    }

}
