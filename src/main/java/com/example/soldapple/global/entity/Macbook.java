package com.example.soldapple.global.entity;

import com.example.soldapple.global.dto.CreateMacbookReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Macbook {
    @Id
    @Column(name = "macbookId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long macbookId;

    private Integer productYear;
    private String model;
    private String cpu;
    private Integer inch;
    private String keyboard;
    private String ram;
    private String storage;
    private String opt;
    private Integer price;

    public Macbook(CreateMacbookReqDto createMacbookReqDto){
        this.productYear = createMacbookReqDto.getYear();
        this.model = createMacbookReqDto.getModel();
        this.cpu = createMacbookReqDto.getCpu();
        this.inch = createMacbookReqDto.getInch();
        this.keyboard = createMacbookReqDto.getKeyboard();
        this.ram = createMacbookReqDto.getRam();
        this.storage = createMacbookReqDto.getStorage();
        this.opt = createMacbookReqDto.getMacbookOption();
        this.price = createMacbookReqDto.getPrice();
    }
}
