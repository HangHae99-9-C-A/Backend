package com.example.soldapple.issues.entity;

import com.example.soldapple.create_price.dto.GetIPhonePriceResDto;
import com.example.soldapple.create_price.dto.GetMacbookPriceResDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class IssuesOpt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    private String category;
    private String years;
    private String model;
    private String options;
    private String batteryState;
    private String displayState;
    private String scratchState;
    private String careOX;
    private String careDate;
    private Integer getPrice;
    private String ram;
    private String keyboard;
    private String storage;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "issuesId")
    private Issues issues;

    public IssuesOpt(GetMacbookPriceResDto macbookOption, Issues issues) {
        this.category = macbookOption.getCategory();
        this.years = macbookOption.getYear();
        this.model = macbookOption.getModel();
        this.options = macbookOption.getOption();
        this.ram = macbookOption.getRam();
        this.keyboard = macbookOption.getKeyboard();
        this.storage = macbookOption.getStorage();
        this.batteryState = macbookOption.getBatteryState();
        this.careOX = macbookOption.getCareOX();
        this.careDate = macbookOption.getCareDate();
        this.getPrice = macbookOption.getGetPrice();
        this.issues = issues;
    }

    public IssuesOpt(GetIPhonePriceResDto iphoneOption, Issues issues) {
        this.category=iphoneOption.getCategory();
        this.years=iphoneOption.getYear();
        this.model=iphoneOption.getModel();
        this.options=iphoneOption.getOption();
        this.batteryState = iphoneOption.getBatteryState();
        this.displayState = iphoneOption.getDisplayState();
        this.scratchState= iphoneOption.getScratchState();
        this.careOX = iphoneOption.getCareOX();
        this.careDate = iphoneOption.getCareDate();
        this.getPrice = iphoneOption.getGetPrice();
        this.issues = issues;
    }
}
