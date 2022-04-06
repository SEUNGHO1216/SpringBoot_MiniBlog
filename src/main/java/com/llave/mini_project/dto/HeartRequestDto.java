package com.llave.mini_project.dto;

import com.llave.mini_project.models.Blog;
import com.llave.mini_project.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class HeartRequestDto {
    //블로그DTO에 id추가
    private Long id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}