package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GetIPhonePriceResDto {
    private String category;
    private Integer year;
    private String model;
    private String option; //storage
    private Integer batteryState;
    private String iphoneState;
    private String careOX;
    private String careDate;
    private Integer getPrice;

    public GetIPhonePriceResDto(GetIPhonePriceReqDto getIPhonePriceReqDto, Integer price){
        this.category = getIPhonePriceReqDto.getCategory();
        this.year = getIPhonePriceReqDto.getYear();
        this.model = getIPhonePriceReqDto.getModel();
        this.option = getIPhonePriceReqDto.getOption();
        this.batteryState = getIPhonePriceReqDto.getBatteryState();
        this.iphoneState = getIPhonePriceReqDto.getIphoneState();
        this.careOX = getIPhonePriceReqDto.getCareOX();
        this.careDate = getIPhonePriceReqDto.getCareDate();
        this.getPrice = price;
    }
}
