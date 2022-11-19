package com.example.soldapple.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateIPhoneReqDto {
    @NotBlank
    private Integer year;
    @NotBlank
    private String model;
    @NotBlank
    private String storage;
    @NotBlank
    private String opt;
    @NotBlank
    private Integer price;
}
