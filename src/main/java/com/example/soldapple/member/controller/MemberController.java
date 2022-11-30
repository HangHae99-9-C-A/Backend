package com.example.soldapple.member.controller;

import com.example.soldapple.jwt.dto.TokenDto;
import com.example.soldapple.member.dto.EmailReqDto;
import com.example.soldapple.member.dto.KakaoUserInfoDto;
import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.dto.LoginReqDto;
import com.example.soldapple.member.service.EmailService;
import com.example.soldapple.member.service.MemberService;
import com.example.soldapple.global.dto.GlobalResDto;
import com.example.soldapple.jwt.util.JwtUtil;
import com.example.soldapple.security.user.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid MemberReqDto memberReqDto) {
        return memberService.signup(memberReqDto);
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid LoginReqDto loginReqDto) {
        return memberService.login(loginReqDto);
    }

    @PostMapping("/signup/mail-confirm")
    public String mailConfirm(@RequestBody @Valid EmailReqDto email) throws MessagingException, UnsupportedEncodingException {
        return emailService.sendEmail(email.getEmail());
    }

    @GetMapping("/kakao")
    public KakaoUserInfoDto kakaoLogin(@RequestParam(name = "code") String code) throws JsonProcessingException {
        KakaoUserInfoDto kakaoUserInfoDto = memberService.kakaoLogin(code);
        return kakaoUserInfoDto;
    }

    @GetMapping("/issue/token")
    public GlobalResDto issuedToken(@ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getEmail(), "Access"));
        return new GlobalResDto("Success IssuedToken", HttpStatus.OK.value());
    }


}
