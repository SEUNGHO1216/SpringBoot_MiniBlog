package com.llave.mini_project.repository;


import com.llave.mini_project.models.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findAllByBlogIdOrderByModifiedAtDesc(Long blogId);
}
