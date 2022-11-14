package com.example.soldapple.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateMacbookReqDto {
    private Long year;
    private String model;
    private String cpu;
    private Long inch;
    private String keyboard;
}
