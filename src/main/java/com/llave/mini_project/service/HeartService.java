package com.llave.mini_project.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.llave.mini_project.dto.HeartRequestDto;
import com.llave.mini_project.models.Blog;
import com.llave.mini_project.models.Heart;
import com.llave.mini_project.models.User;
import com.llave.mini_project.repository.BlogRepository;
import com.llave.mini_project.repository.HeartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HeartService {

    private HeartRepository heartRepository;
    private BlogRepository blogRepository;
    @Autowired
    public HeartService(HeartRepository heartRepository, BlogRepository blogRepository){
        this. heartRepository=heartRepository;
        this.blogRepository=blogRepository;
    }
    //좋아요 추가/삭제
    @Transactional
    public int addHeart(Long blogId, User user) {
        System.out.println("info:"+user);
        System.out.println("블로그아이디"+blogId);
        //게시물정보를 그대로 받아왔던 heartRequestDto를 통해 게시물 테이블 접근
        Blog blog=blogRepository.findById(blogId).orElseThrow(
                ()->new NullPointerException("해당 아이디가 없습니다")
        );
        System.out.println(blog);

        List<Heart> heartList=heartRepository.findByUserAndBlog(user, blog);
        System.out.println("좋아요리스트"+heartList);
        //그럼 좋아요 이미 누른 사람은 취소 어떻게 해?
//        Blog blogHeartCount=blogRepository.findById(blog.getId()).orElseThrow(
//                ()->new IllegalArgumentException("해당 아아디 없음")
//        );
        System.out.println("좋아요수 이전"+blog.getHeartCount());
        if(heartList.size()>0){
            heartRepository.deleteByUserAndBlog(user, blog);
            System.out.println("좋아요수 이후"+blog.getHeartCount());
            return blog.getHeartCount()-1;
        }
        System.out.println("좋아요수 이후"+blog.getHeartCount());
        Heart heart=new Heart(user, blog);
        //좋아요 저장
        heartRepository.save(heart);
        //

        //Formula로 좋아요 수 count한 것을 반환
        //1을 더한 이유는 좋아요 클릭해서 1이 늘어난 것을 반영하기 위함
        return blog.getHeartCount()+1;
    }

    //좋아요 개수 출력
    public int showHeart(Long id){
        Blog blog=blogRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("해당 아이디가 없습니다.")
        );
        return blog.getHeartCount();
    }
}