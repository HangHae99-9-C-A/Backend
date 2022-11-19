package com.example.soldapple.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateMacbookReqDto {
    @NotBlank
    private Integer year;
    @NotBlank
    private String model;
    @NotBlank
    private String cpu;
    @NotBlank
    private Integer inch;
    @NotBlank
    private String keyboard;
    @NotBlank
    private String ram;
    @NotBlank
    private String storage;
    @NotBlank
    private String macbookOption;
    @NotBlank
    private Integer price;
}
