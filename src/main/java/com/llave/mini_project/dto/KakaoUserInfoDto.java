package com.llave.mini_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoUserInfoDto {

    private Long kakaoId;
    private String nickname;
    private String password;
    private String email;
}
