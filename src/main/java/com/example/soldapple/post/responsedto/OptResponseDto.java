package com.example.soldapple.post.responsedto;

import com.example.soldapple.post.entity.Opt;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptResponseDto {
    private Long optionId;
    private String category;
    private Integer years;
    private String model;
    private String options;
    private Integer batteryState;
    private String state;
    private String careOX;
    private String careDate;
    private Integer getPrice;
    private String ram;
    private String keyboard;
    private String storage;

    public OptResponseDto(Opt opt){
        this.optionId=opt.getOptionId();
        this.category=opt.getCategory();
        this.years=opt.getYears();
        this.model=opt.getModel();
        this.options=opt.getOptions();
        this.batteryState=opt.getBatteryState();
        this.state=opt.getState();
        this.careOX=opt.getCareOX();
        this.careDate=opt.getCareDate();
        this.getPrice=opt.getGetPrice();
        this.ram=opt.getRam();
        this.keyboard=opt.getKeyboard();
        this.storage=opt.getStorage();
    }
}
