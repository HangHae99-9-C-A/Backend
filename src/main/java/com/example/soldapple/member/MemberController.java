package com.example.soldapple.member;

import com.example.soldapple.jwt.TokenDto;
import com.example.soldapple.member.dto.EmailReqDto;
import com.example.soldapple.member.dto.LoginKakaoResDto;
import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.dto.LoginReqDto;
import com.example.soldapple.global.dto.GlobalResDto;
import com.example.soldapple.jwt.JwtUtil;
import com.example.soldapple.security.user.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/signup") //회원가입
    public String signup(@RequestBody @Valid MemberReqDto memberReqDto) {
        return memberService.signup(memberReqDto);
    }

    @PostMapping("/login")  //로그인
    public TokenDto login(@RequestBody @Valid LoginReqDto loginReqDto) {
        return memberService.login(loginReqDto);
    }

    @PostMapping("/signup/mail-confirm")    //이메일 인증 보내기
    public String mailConfirm(@RequestBody @Valid EmailReqDto email) throws MessagingException, UnsupportedEncodingException {
        return memberService.sendEmail(email.getEmail());
    }

    @GetMapping("/kakao")   //카카오 로그인
    public LoginKakaoResDto kakaoLogin(@RequestParam(name = "code") String code) throws JsonProcessingException {
        LoginKakaoResDto loginKakaoResDto = memberService.kakaoLogin(code);
        return loginKakaoResDto;
    }

    @GetMapping("/issue/token") //토큰 재발급
    public GlobalResDto issuedToken(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getEmail(), "Access"));
        return new GlobalResDto("Success IssuedToken", HttpStatus.OK.value());
    }
}
