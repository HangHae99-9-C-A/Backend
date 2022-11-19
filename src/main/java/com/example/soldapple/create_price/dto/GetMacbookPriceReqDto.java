package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GetMacbookPriceReqDto {
    private String category;
    private Integer year;
    private String cpu;
    private Integer inch;
    private String model;
    private String option;
    private String ram;
    private String keyboard;
    private String storage;
    private Integer batteryState;
    private String careOX;
    private String careDate;
}
