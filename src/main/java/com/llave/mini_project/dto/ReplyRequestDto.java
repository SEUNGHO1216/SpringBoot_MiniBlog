package com.llave.mini_project.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyRequestDto {

    private String nickname;
    private String contents;
    private Long blogId;

}
