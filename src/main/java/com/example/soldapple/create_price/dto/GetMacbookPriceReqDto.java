package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMacbookPriceReqDto {
    private String category;
    private String year;
    private String model;
    private String option;
    private String ram;
    private String keyboard;
    private String storage;
    private String batteryState;
    private String careOX;
    private String careDate;
}
