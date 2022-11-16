package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetIPhonePriceReqDto {
    private String category;
    private String year;
    private String model;
    private String option;
    private String batteryState;
    private String displayState;
    private String scratchState;
    private String careOX;
    private String careDate;
}
