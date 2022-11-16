package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetIPhonePriceResDto {
    private String category;
    private String year;
    private String model;
    private String option;
    private String batteryState;
    private String displayState;
    private String scratchState;
    private String careOX;
    private String careDate;
    private Integer getPrice;

    public GetIPhonePriceResDto(GetIPhonePriceReqDto getIPhonePriceReqDto){
        this.category = getIPhonePriceReqDto.getCategory();
        this.year = getIPhonePriceReqDto.getYear();
        this.model = getIPhonePriceReqDto.getModel();
        this.option = getIPhonePriceReqDto.getOption();
        this.batteryState = getIPhonePriceReqDto.getBatteryState();
        this.displayState = getIPhonePriceReqDto.getDisplayState();
        this.scratchState = getIPhonePriceReqDto.getScratchState();
        this.careOX = getIPhonePriceReqDto.getCareOX();
        this.careDate = getIPhonePriceReqDto.getCareDate();
        this.getPrice = 10000;
    }
}
