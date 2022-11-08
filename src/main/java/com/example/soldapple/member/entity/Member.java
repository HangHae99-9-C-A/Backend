package com.example.soldapple.member.entity;

import com.example.soldapple.member.dto.MemberReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;

    private String password;
    @NotBlank
    private String domain;

    private String avatarUrl;

    public Member(MemberReqDto memberReqDto) {
        this.email = memberReqDto.getEmail();
        this.nickname = memberReqDto.getNickname();
        this.password = memberReqDto.getPassword();
        this.domain = "Sold Apple";
        this.avatarUrl = null;
    }

    public Member(String email, String nickname, String domain, String avatarUrl){
        this.email = email;
        this.nickname = nickname;
        this.password = null;
        this.domain = domain;
        this.avatarUrl = avatarUrl;
    }
}
