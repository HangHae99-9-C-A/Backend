package com.example.soldapple.member.dto;

import com.example.soldapple.jwt.dto.TokenDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class KakaoUserInfoDto {
    private String email;
    private String nickname;
    private String avatarUrl;
    private String accessToken;
    private String refreshToken;

    public KakaoUserInfoDto(String email, String nickname, String avatarUrl, TokenDto tokenDto){
        this.email = email;
        this.nickname = nickname;
        this.avatarUrl = avatarUrl;
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
    }
}
