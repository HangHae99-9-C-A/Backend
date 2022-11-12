package com.example.soldapple.personal;

import com.example.soldapple.member.entity.Member;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class MemberResponseDto {
    private String email;
    private String nickname;
    private String profileImg;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.profileImg = member.getAvatarUrl();

    }

}
