package com.llave.mini_project.models;

import com.llave.mini_project.dto.ReplyRequestDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
public class Reply extends Timestamped{

    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Long blogId;

    @Column(nullable = false)
    private Long userId;

    public Reply(ReplyRequestDto requestDto, Long userId){
        this.nickname=requestDto.getNickname();
        this.contents=requestDto.getContents();
        this.blogId=requestDto.getBlogId();
        this.userId=userId;
    }
    public void update(ReplyRequestDto requestDto, Long userId){
        this.nickname=requestDto.getNickname();
        this.contents=requestDto.getContents();
        this.blogId=requestDto.getBlogId();
        this.userId=userId;
    }
}
