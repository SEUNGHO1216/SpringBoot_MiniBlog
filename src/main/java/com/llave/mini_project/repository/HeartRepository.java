package com.llave.mini_project.repository;

import com.llave.mini_project.models.Blog;
import com.llave.mini_project.models.Heart;
import com.llave.mini_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    List<Heart> findByUserAndBlog(User user, Blog blog);
    void deleteByUserAndBlog(User user, Blog blog);

}