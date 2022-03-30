package com.llave.mini_project.models;

import com.llave.mini_project.dto.KakaoUserInfoDto;
import com.llave.mini_project.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
public class User extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, unique = true)
    private Long kakaoId;

    //일반 로그인 폼 유저
    public User(UserRequestDto requestDto){
        this.nickname=requestDto.getNickname();
        this.password=requestDto.getPassword();
        this.kakaoId=null;
    }
    //카카오 oAuth 유저
    public User(KakaoUserInfoDto kakaoUserInfoDto){
        this.nickname=kakaoUserInfoDto.getNickname();
        this.password= kakaoUserInfoDto.getPassword();
        this.kakaoId=kakaoUserInfoDto.getKakaoId();
    }
    //optional 유저(카카오 이미 연동돼 있는 유저)
    public User(Optional<User> optionalUser){
        this.id=optionalUser.get().getId();
        this.nickname=optionalUser.get().getNickname();
        this.password= optionalUser.get().getPassword();
        this.kakaoId=optionalUser.get().getKakaoId();
    }



}
