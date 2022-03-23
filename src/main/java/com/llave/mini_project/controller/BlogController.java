package com.llave.mini_project.controller;

import com.llave.mini_project.models.Blog;
import com.llave.mini_project.models.BlogRepository;
import com.llave.mini_project.models.BlogRequestDto;
import com.llave.mini_project.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogController {

    private final BlogRepository blogRepository;
    private final BlogService blogService;

    @PostMapping("/api/blogs")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto){
        Blog blog=new Blog(requestDto);
        return blogRepository.save(blog);
    }

    @GetMapping("/api/blogs")
    public List<Blog> showBlog(){
        List<Blog> blogList=blogRepository.findAllByOrderByModifiedAtDesc();
        return blogList;
    }

    /*디테일 페이지에서 그 게시물 정보 조회할 때*/
    @GetMapping("/api/blogs/{id}")
    public Blog showOneArticle(@PathVariable Long id){
        Blog blog=blogRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 없습니다")
        );
        return blog;
    }

    @PutMapping("/api/blogs/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto){
        return blogService.updateBlog(id,requestDto);
    }

    @DeleteMapping("/api/blogs/{id}")
    public Long deleteBlog(@PathVariable Long id){
        blogRepository.deleteById(id);
        return id;
    }
}
