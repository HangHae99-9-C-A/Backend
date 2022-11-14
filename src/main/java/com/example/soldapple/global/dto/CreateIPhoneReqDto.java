package com.example.soldapple.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateIPhoneReqDto {

    private Long year;
    private String model;
    private String storage;
}
