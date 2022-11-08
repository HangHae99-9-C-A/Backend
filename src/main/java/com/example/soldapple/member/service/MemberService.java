package com.example.soldapple.member.service;

import com.example.soldapple.member.dto.KakaoUserInfoDto;
import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.dto.LoginReqDto;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.entity.RefreshToken;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.member.repository.RefreshTokenRepository;
import com.example.soldapple.jwt.dto.TokenDto;
import com.example.soldapple.jwt.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public String signup(MemberReqDto memberReqDto) {
        // email 중복 검사
        if(memberRepository.findByEmail(memberReqDto.getEmail()).isPresent()){
            throw new RuntimeException("Overlap Check");
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPassword()));
        Member member = new Member(memberReqDto);

        memberRepository.save(member);
        return "Success";
    }

    @Transactional
    public TokenDto login(LoginReqDto loginReqDto) {

        Member member = memberRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(loginReqDto.getEmail());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        return tokenDto;
    }

    public KakaoUserInfoDto kakaoLogin(String code) throws JsonProcessingException {
        String accessToken = getAccessToken(code);

        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
        return kakaoUserInfo;
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "7f656ffee29f64023f766253fb70514e");
        body.add("redirect_uri", "http://localhost:3000");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String email = jsonNode.get("id").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String avatarUrl = jsonNode.get("kakao_account")
                        .get("profile").get("profile_image_url").asText();

        System.out.println("카카오 사용자 정보: " + email + ", " + nickname + ", " + avatarUrl);

        if(!memberRepository.findByEmail(email).isPresent()){
            Member member = new Member(email, nickname, "Kakao", avatarUrl);
            memberRepository.save(member);
        }

        TokenDto tokenDto = jwtUtil.createAllToken(email);

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByMemberEmail(email);

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), email);
            refreshTokenRepository.save(newToken);
        }

        return new KakaoUserInfoDto(email, nickname, avatarUrl, tokenDto);
    }
}
