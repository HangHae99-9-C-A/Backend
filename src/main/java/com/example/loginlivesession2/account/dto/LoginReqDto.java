package com.example.loginlivesession2.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginReqDto {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

