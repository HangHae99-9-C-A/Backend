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
    @NotBlank
    private String password;

    public Member(MemberReqDto memberReqDto) {
        this.email = memberReqDto.getEmail();
        this.nickname = memberReqDto.getNickname();
        this.password = memberReqDto.getPassword();
    }
    
}
