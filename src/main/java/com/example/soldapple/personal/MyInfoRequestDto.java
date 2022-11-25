package com.example.soldapple.personal;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MyInfoRequestDto {
    private String nickname;


    public MyInfoRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
