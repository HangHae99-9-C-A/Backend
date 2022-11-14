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

    private Long asd;
    private String model;
    private String storage;

    public IPhone(CreateIPhoneReqDto createTableReqDto){
        this.asd = createTableReqDto.getYear();
        this.model = createTableReqDto.getModel();
        this.storage = createTableReqDto.getStorage();
    }
}
