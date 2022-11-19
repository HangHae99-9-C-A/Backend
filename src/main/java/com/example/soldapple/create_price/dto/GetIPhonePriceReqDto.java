package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GetIPhonePriceReqDto {
    private String category;
    private Integer year;
    private String model;
    private String option; //storage
    private Integer batteryState;
    private String iphoneState;
    private String careOX;
    private String careDate;
}
