package com.example.soldapple.member;

import com.example.soldapple.error.CustomException;
import com.example.soldapple.error.ErrorCode;
import com.example.soldapple.jwt.TokenDto;
import com.example.soldapple.jwt.JwtUtil;
import com.example.soldapple.member.dto.KakaoUserInfoDto;
import com.example.soldapple.member.dto.LoginReqDto;
import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.entity.RefreshToken;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.member.repository.RefreshTokenRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

import static com.example.soldapple.error.ErrorCode.NOT_FOUND_USER;
import static com.example.soldapple.error.ErrorCode.NOT_MATCHED_PASSWORD;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JavaMailSender emailSender;   //의존성 주입을 통해서 필요한 객체를 가져온다.
    private String authNum; //랜덤 인증 코드

    @Transactional
    public String signup(MemberReqDto memberReqDto) {
        // email 중복 검사
        if(memberRepository.findByEmail(memberReqDto.getEmail()).isPresent()){
            throw new CustomException(ErrorCode.USER_IS_EXIST);
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPassword()));
        Member member = new Member(memberReqDto);

        memberRepository.save(member);
        return "Success";
    }

    @Transactional
    public TokenDto login(LoginReqDto loginReqDto) {

        Member member = memberRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );

        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            throw new CustomException(NOT_MATCHED_PASSWORD);
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

        return getKakaoUserInfo(accessToken);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "d2c795c61c767b9c2bc94eb5cb045230");
        body.add("redirect_uri", "https://www.findapple.co.kr/KAKAO");
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
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String avatarUrl = jsonNode.get("kakao_account")
                        .get("profile").get("profile_image_url").asText();
        if(memberRepository.findByEmail(email).isEmpty()){
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

    public void createCode() {  //랜덤 인증 코드 생성
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {  //메일 양식 작성

        createCode(); //인증 코드 생성
        String setFrom = "dnajwm1995@gmail.com"; //email-config에 설정한 자신의 이메일 주소(보내는 사람)
        String title = "Find Apple 회원가입 인증 번호"; //이메일 제목

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); //보낼 이메일 설정
        message.setSubject(title); //제목 설정
        message.setFrom(new InternetAddress(setFrom,"FindApple")); //보내는 이메일 FindApple로 변경
        message.setText("<h1>Find Apple 인증번호 입니다.</h1>" + "<h2>인증번호 : " + authNum + "</h2>", "utf-8", "html");  //이메일 내욜 설정

        return message;
    }

    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {       //실제 메일 전송
        MimeMessage emailForm = createEmailForm(toEmail);   //메일전송에 필요한 정보 설정
        emailSender.send(emailForm);    //메일 전송

        return authNum; //인증 코드 반환
    }
}
