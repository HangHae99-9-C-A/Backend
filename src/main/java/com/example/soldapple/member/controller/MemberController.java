package com.example.soldapple.member.controller;

import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.dto.LoginReqDto;
import com.example.soldapple.member.service.MemberService;
import com.example.soldapple.global.dto.GlobalResDto;
import com.example.soldapple.jwt.util.JwtUtil;
import com.example.soldapple.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(@RequestBody @Valid MemberReqDto memberReqDto) {
//        return memberService.signup(memberReqDto);
        return "hi";
    }

    @PostMapping("/login")
    public GlobalResDto login(@RequestBody @Valid LoginReqDto loginReqDto, HttpServletResponse response) {
        return memberService.login(loginReqDto, response);
    }

    @GetMapping("/issue/token")
    public GlobalResDto issuedToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(userDetails.getMember().getEmail(), "Access"));
        return new GlobalResDto("Success IssuedToken", HttpStatus.OK.value());
    }

}
