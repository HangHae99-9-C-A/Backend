package com.example.soldapple.member.controller;

import com.example.soldapple.jwt.dto.TokenDto;
import com.example.soldapple.member.dto.EmailReqDto;
import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.dto.LoginReqDto;
import com.example.soldapple.member.service.EmailService;
import com.example.soldapple.member.service.MemberService;
import com.example.soldapple.global.dto.GlobalResDto;
import com.example.soldapple.jwt.util.JwtUtil;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/member")
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

    @PostMapping("login/mailConfirm")
    public String mailConfirm(@RequestBody EmailReqDto emailDto) throws MessagingException, UnsupportedEncodingException {

        String authCode = emailService.sendEmail(emailDto.getEmail());
        return authCode;
    }

    @GetMapping("/issue/token")
    public GlobalResDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getEmail(), "Access"));
        return new GlobalResDto("Success IssuedToken", HttpStatus.OK.value());
    }

}
