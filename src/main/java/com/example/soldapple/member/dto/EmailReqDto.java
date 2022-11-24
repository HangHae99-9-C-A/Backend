package com.example.soldapple.member.dto;

import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
public class EmailReqDto {
    @Email
    public String email;
}
