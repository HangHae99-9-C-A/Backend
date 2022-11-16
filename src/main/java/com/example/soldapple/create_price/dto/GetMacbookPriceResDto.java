package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class GetMacbookPriceResDto {
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
    private Integer getPrice;

    public GetMacbookPriceResDto(GetMacbookPriceReqDto getMacbookPriceReqDto){
        this.category = getMacbookPriceReqDto.getCategory();
        this.year = getMacbookPriceReqDto.getYear();
        this.model = getMacbookPriceReqDto.getModel();
        this.option = getMacbookPriceReqDto.getOption();
        this.ram = getMacbookPriceReqDto.getRam();
        this.keyboard = getMacbookPriceReqDto.getKeyboard();
        this.storage = getMacbookPriceReqDto.getStorage();
        this.batteryState = getMacbookPriceReqDto.getBatteryState();
        this.careOX = getMacbookPriceReqDto.getCareOX();
        this.careDate = getMacbookPriceReqDto.getCareDate();
        this.getPrice = 10000;
    }
}
