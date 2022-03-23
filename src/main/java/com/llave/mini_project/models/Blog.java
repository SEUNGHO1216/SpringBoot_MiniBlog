package com.llave.mini_project.models;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Blog extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length =10000)
    private String contents;

    public Blog(BlogRequestDto requestDto){
        this.writer=requestDto.getWriter();
        this.title=requestDto.getTitle();
        this.contents=requestDto.getContents();
    }

    public void update(BlogRequestDto requestDto){
        this.writer=requestDto.getWriter();
        this.title=requestDto.getTitle();
        this.contents=requestDto.getContents();
    }
}
