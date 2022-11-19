package com.example.soldapple.create_price.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GetMacbookPriceResDto {
    private String category;
    private Integer year;
    private String model;
    private String cpu;
    private Integer inch;
    private String option;
    private String ram;
    private String keyboard;
    private String storage;
    private Integer batteryState;
    private String careOX;
    private String careDate;
    private Integer getPrice;

    public GetMacbookPriceResDto(GetMacbookPriceReqDto getMacbookPriceReqDto, Integer price){
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
        this.getPrice = price;
        this.cpu = getMacbookPriceReqDto.getCpu();
        this.inch = getMacbookPriceReqDto.getInch();
    }
}
