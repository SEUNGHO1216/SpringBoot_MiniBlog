package com.llave.mini_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web){
        //h2-console 사용에 대한 허용(csrf, frameOptions 무시)
        web.ignoring()
                .antMatchers();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 회원관리처리 api(POST /user/**)에 대해 csrf 무시해줌(post일 때만 해당!)
        // 그러나 테스트 결과 get빼고 다 인듯
        http.csrf()
                .ignoringAntMatchers("/user/**")
                .ignoringAntMatchers("/api/blogs/**")
                .ignoringAntMatchers("/api/reply/**")
                .ignoringAntMatchers("/api/heart/**");

        http.authorizeRequests()
        // image 폴더를 login 없이 허용
                .antMatchers("/images/**",
                        "/css/**",
                        "/user/**",
                        "/",
                        //지금 개발단계이니 일단 다 허용(수정필요)
                        "/api/blogs",
                        "/api/blogs/one/**",
                        "/api/blogs/articles*",
                        "/api/reply/**",
                        "/api/heart/**").permitAll()
        // 어떤 요청이든 '인증'
                .anyRequest().authenticated()
                .and()
        // 로그인 기능 허용
                .formLogin()
                //GET 로그인 페이지 띄어줌
                .loginPage("/user/login")
                //POST security에게 로그인 정보 전달(이때 userDetailsServiceImpl에서 username을 주고 있으면
                //userDetailsImpl에서 비밀번호 암호화 된거 받아와서 다시 돌아오는 길에
                //webSercurityConfig에서 확인함
                .loginProcessingUrl("/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?error")
                .permitAll()
                .and()
// 로그아웃 기능 허용
                .logout()
                //로그아웃 url
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/")//로그아웃 성공시 리다이렉트 경로(기본값 : 로그인 화면)
                .permitAll();
    }
}