package com.example.soldapple.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class EmailReqDto {
    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    public String email;
}
