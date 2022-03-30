package com.llave.mini_project.service;

import com.llave.mini_project.models.Blog;
import com.llave.mini_project.repository.BlogRepository;
import com.llave.mini_project.dto.BlogRequestDto;
import com.llave.mini_project.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    //블로그 메인 페이지 조회
    public List<Blog> showAll(){
        List<Blog> blogList=blogRepository.findAllByOrderByModifiedAtDesc();
        return blogList;
    }
    @Transactional
    public Long updateBlog(Long id, BlogRequestDto requestDto){
        Blog blog=blogRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 없습니다.")
        );
        blog.update(requestDto);
        return id;
    }
}
