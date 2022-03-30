package com.llave.mini_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequestDto {

    @NotBlank(message = "닉네임을 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{3,15}$", message = "닉네임은 3자 이상 15자 이하의 영어 소문자,대문자, 숫자의 조합으로 입력해주세요")
    private String nickname;

    //닉네임값이 포함되면 안 됨
    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^.{4,15}$", message = "비밀번호는 4자리 이상 입력해주세요")
    private String password;


}
