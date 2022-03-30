package com.llave.mini_project.repository;

import com.llave.mini_project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    /*userDetailsServiceImpl 에서 조건으로 불러오기 위함*/
    Optional<User> findByNickname(String nickname);

    /*카카오 로그인 회원가입 시 중복체크*/
    Optional<User> findByKakaoId(Long kakaoId);

}
