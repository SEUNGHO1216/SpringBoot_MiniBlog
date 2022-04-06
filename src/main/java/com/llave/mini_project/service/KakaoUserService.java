package com.llave.mini_project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.llave.mini_project.dto.KakaoUserInfoDto;
import com.llave.mini_project.models.User;
import com.llave.mini_project.repository.UserRepository;
import com.llave.mini_project.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Service
public class KakaoUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public KakaoUserService(UserRepository userRepository, PasswordEncoder passwordEncoder){

        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    //카카오 로그인
    public void kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);
        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfoDto=getKakaoUserInfo(accessToken);
        // 3. 카카오 계정으로 회원가입 처리(반환값 : 신규 가입자이든 중복자이든 user정보 담아서 반환)
        User user=isDuplicatedKakaoId(kakaoUserInfoDto);

        // 4. 강제 로그인 처리(로컬은 로그인 정보가 시큐리티에 있지만, api는 카카오에 있다. 따라서 강제 로그인처리 해줘야함)
        kakaoSinginIfNeeded(user);
    }

    //카카오로그인 1번과정 리팩토링(토큰요청)
    private String getAccessToken(String code) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "cd8e0182ff5a86168a2f3b15d55d6d39");
        body.add("redirect_uri", "http://benjamin-tech.shop/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange( //이때 카카오 서버와 교신하고 결과를 response로 받는다
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();//response도 헤더와 바디로 나뉘어 있으니! 바디 부분의 token 빼냄
        ObjectMapper objectMapper = new ObjectMapper(); //json형태의 정보를 추출하기 위함
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText(); //토큰 받아옴!
    }

    // 카카오 로그인 2번과정 리팩토링 (토큰으로 계정정보요청)
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response;
        response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        String password= UUID.randomUUID().toString();
        String encPassword=passwordEncoder.encode(password);
        KakaoUserInfoDto kakaoUserInfoDto=new KakaoUserInfoDto(id, nickname, encPassword, email);
        return kakaoUserInfoDto;

    }

    //카카오로 회원가입
    private User isDuplicatedKakaoId(KakaoUserInfoDto kakaoUserInfoDto) {
        Optional<User> optionalUser=userRepository.findByKakaoId(kakaoUserInfoDto.getKakaoId());
        //중복된 회원 아이디 존재시 그냥 회원정보 저장이 아니라 로그인 처리만 되게하자(밑에서)
        User user =new User();
        //중복 안 되어 사용 가능 시
        if(!optionalUser.isPresent()){
            userRepository.save(user);
        }else{
        //증복되면 이미 정보는 있으니 save하지 말고 정보만 user에 담아서 넘겨주자(그게 여기선 optionalUser로 바로 전송
            user=new User(optionalUser);
        }
        return user;
    }

    //강제 로그인
    private void kakaoSinginIfNeeded(User user) {
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
