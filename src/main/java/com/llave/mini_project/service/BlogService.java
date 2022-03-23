package com.llave.mini_project.service;

import com.llave.mini_project.models.Blog;
import com.llave.mini_project.models.BlogRepository;
import com.llave.mini_project.models.BlogRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Long updateBlog(Long id, BlogRequestDto requestDto){
        Blog blog=blogRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 없습니다.")
        );
        blog.update(requestDto);
        return id;
    }
}
