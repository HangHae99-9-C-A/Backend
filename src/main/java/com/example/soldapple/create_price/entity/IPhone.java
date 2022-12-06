package com.example.soldapple.create_price.entity;

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
}
