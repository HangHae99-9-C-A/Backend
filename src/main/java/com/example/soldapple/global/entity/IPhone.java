package com.example.soldapple.global.entity;

import com.example.soldapple.global.dto.CreateIPhoneReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class IPhone {
    @Id
    @Column(name = "iPhoneId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long iPhoneId;

    private Integer productYear;
    private String model;
    private String storage;
    private String opt;
    private Integer price;

    public IPhone(CreateIPhoneReqDto createIPhoneReqDto){
        this.productYear = createIPhoneReqDto.getYear();
        this.model = createIPhoneReqDto.getModel();
        this.storage = createIPhoneReqDto.getStorage();
        this.opt = createIPhoneReqDto.getOpt();
        this.price = createIPhoneReqDto.getPrice();
    }
}
