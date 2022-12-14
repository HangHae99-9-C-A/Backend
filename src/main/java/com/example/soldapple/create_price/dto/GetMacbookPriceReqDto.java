package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GetMacbookPriceReqDto {
    private String category;
    private Integer years;
    private String model; //model-cpu
    private String options; //inch
    private String ram;
    private String keyboard;
    private String storage;
    private String macbookState;
    private Integer batteryState;
    private String careOX;
    private String careDate;
}
