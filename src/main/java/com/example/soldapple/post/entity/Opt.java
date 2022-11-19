package com.example.soldapple.post.entity;

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
public class Opt {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
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

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "postId")
    private Post post;


    public Opt(GetMacbookPriceResDto macbookOption, Post post) {
        this.category = macbookOption.getCategory();
        this.years = macbookOption.getYear();
        this.model = macbookOption.getModel();
        this.options = macbookOption.getOption();
        this.ram = macbookOption.getRam();
        this.keyboard = macbookOption.getKeyboard();
        this.storage = macbookOption.getStorage();
        this.batteryState = macbookOption.getBatteryState();
        this.state = macbookOption.getMacbookState();
        this.careOX = macbookOption.getCareOX();
        this.careDate = macbookOption.getCareDate();
        this.getPrice = macbookOption.getGetPrice();
        this.post = post;
    }

    public Opt(GetIPhonePriceResDto iphoneOption, Post post) {
        this.category=iphoneOption.getCategory();
        this.years=iphoneOption.getYear();
        this.model=iphoneOption.getModel();
        this.options=iphoneOption.getOption();
        this.batteryState = iphoneOption.getBatteryState();
        this.state = iphoneOption.getIphoneState();
        this.careOX = iphoneOption.getCareOX();
        this.careDate = iphoneOption.getCareDate();
        this.getPrice = iphoneOption.getGetPrice();
        this.post = post;
    }


}
