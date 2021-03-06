package com.llave.mini_project.models;


import com.llave.mini_project.dto.BlogRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

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

    @Formula("(select count(1) from heart h where h.blog_id = id)")
    private int heartCount;

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
