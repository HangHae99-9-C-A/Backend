package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetIPhonePriceResDto {
    private String category;
    private Integer years;
    private String model;
    private String options; //storage
    private Integer batteryState;
    private String iphoneState;
    private String careOX;
    private String careDate;
    private Integer getPrice;

    public GetIPhonePriceResDto(GetIPhonePriceReqDto getIPhonePriceReqDto, Integer price){
        this.category = getIPhonePriceReqDto.getCategory();
        this.years = getIPhonePriceReqDto.getYears();
        this.model = getIPhonePriceReqDto.getModel();
        this.options = getIPhonePriceReqDto.getOptions();
        this.batteryState = getIPhonePriceReqDto.getBatteryState();
        this.iphoneState = getIPhonePriceReqDto.getIphoneState();
        this.careOX = getIPhonePriceReqDto.getCareOX();
        this.careDate = getIPhonePriceReqDto.getCareDate();
        this.getPrice = price;
    }
}
