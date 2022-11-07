package com.example.soldapple.member.service;

import com.example.soldapple.member.dto.MemberReqDto;
import com.example.soldapple.member.dto.LoginReqDto;
import com.example.soldapple.member.entity.Member;
import com.example.soldapple.member.entity.RefreshToken;
import com.example.soldapple.member.repository.MemberRepository;
import com.example.soldapple.member.repository.RefreshTokenRepository;
import com.example.soldapple.global.dto.GlobalResDto;
import com.example.soldapple.jwt.dto.TokenDto;
import com.example.soldapple.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public GlobalResDto signup(MemberReqDto memberReqDto) {
        // email 중복 검사
        if(memberRepository.findByEmail(memberReqDto.getEmail()).isPresent()){
            throw new RuntimeException("Overlap Check");
        }

        memberReqDto.setEncodePwd(passwordEncoder.encode(memberReqDto.getPassword()));
        Member member = new Member(memberReqDto);

        memberRepository.save(member);
        return new GlobalResDto("Success signup", HttpStatus.OK.value());
    }

    @Transactional
    public GlobalResDto login(LoginReqDto loginReqDto, HttpServletResponse response) {

        Member member = memberRepository.findByEmail(loginReqDto.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        TokenDto tokenDto = jwtUtil.createAllToken(loginReqDto.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountEmail(loginReqDto.getEmail());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), loginReqDto.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return new GlobalResDto("Success Login", HttpStatus.OK.value());

    }

    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }
}
