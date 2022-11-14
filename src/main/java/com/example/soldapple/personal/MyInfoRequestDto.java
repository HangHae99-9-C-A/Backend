package com.example.soldapple.personal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
public class MyInfoRequestDto {
    @NotBlank
    private String nickname;
//    @NotBlank
//    private String password;

    public MyInfoRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
