package com.example.soldapple.global.entity;

import com.example.soldapple.global.dto.CreateIPhoneReqDto;
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

    private Long asd;
    private String model;
    private String cpu;
    private Long inch;
    private String keyboard;

    public Macbook(CreateMacbookReqDto createMacbookReqDto){
        this.asd = createMacbookReqDto.getYear();
        this.model = createMacbookReqDto.getModel();
        this.cpu = createMacbookReqDto.getCpu();
        this.inch = createMacbookReqDto.getInch();
        this.keyboard = createMacbookReqDto.getKeyboard();
    }
}
